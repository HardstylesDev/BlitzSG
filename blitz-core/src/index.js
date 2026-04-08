require('dotenv').config();

const express = require('express');
const fs = require('fs');
const path = require('path');
const { spawn } = require('child_process');
const { createClient } = require('redis');
const { registerRoutes } = require('./core/routes');
const { initializeCli } = require('./core/cli');
const { appendLogLine } = require('./lib/logging');
const { sanitizeMapName, copyDirectoryRecursive } = require('./lib/mapUtils');
const { isBackendReadyLogLine, isProxyReadyLogLine } = require('./lib/readiness');
const { isInsidePath } = require('./lib/pathUtils');

const app = express();
app.use('/dashboard', express.static(path.join(__dirname, '../public')));
app.use(express.json());

const CONTROL_PORT = parseInt(process.env.CORE_PORT || '8080', 10);
const JAVA_BIN = process.env.JAVA_BIN || 'java';
const INSTANCES_ROOT = path.resolve(process.env.INSTANCES_ROOT || path.resolve(process.cwd(), 'instances'));
const DEFAULT_STOP_TIMEOUT_MS = parseInt(process.env.STOP_TIMEOUT_MS || '15000', 10);
const REDIS_URL = process.env.REDIS_URL || 'redis://127.0.0.1:6379';
const REDIS_PASSWORD = process.env.REDIS_PASSWORD || '';
const GAME_SERVER_JAR = process.env.GAME_SERVER_JAR || '';
const HUB_SERVER_JAR = process.env.HUB_SERVER_JAR || GAME_SERVER_JAR;
const PROXY_SERVER_JAR = process.env.PROXY_SERVER_JAR || '';
const PROXY_WORKDIR = path.resolve(process.env.PROXY_WORKDIR || path.join(INSTANCES_ROOT, 'proxy'));
const PROXY_VELOCITY_TEMPLATE = path.resolve(process.env.PROXY_VELOCITY_TEMPLATE || '../runtime/velocity.toml');
const BACKEND_PLUGINS_SOURCE_DIR = path.resolve(
  process.env.BACKEND_PLUGINS_SOURCE_DIR || '../runtime/backend-plugins'
);

const BACKEND_FILES_SOURCE_DIR = path.resolve(
  process.env.BACKEND_FILES_SOURCE_DIR || '../runtime/backend-files'
);


const PROXY_PLUGINS_SOURCE_DIR = path.resolve(
  process.env.PROXY_PLUGINS_SOURCE_DIR || '../runtime/proxy-plugins'
);

const AUTO_START_PROXY = String(process.env.AUTO_START_PROXY || 'false').toLowerCase() === 'true';
const AUTO_START_HUBS = parseInt(process.env.AUTO_START_HUBS || '0', 10);
const AUTO_HUB_PREFIX = process.env.AUTO_HUB_PREFIX || 'hub-';
const PORT_RANGE = process.env.PORT_RANGE || '';
const PUBLIC_HOST = process.env.PUBLIC_HOST || '127.0.0.1';
const MAP_WORLD_SOURCE_DIR = path.resolve(process.env.MAP_WORLD_SOURCE_DIR || '../runtime/maps');
const MAP_CONFIG_SOURCE_DIR = path.resolve(process.env.MAP_CONFIG_SOURCE_DIR || '../runtime/arenas');
const PLUGIN_DATA_FOLDER = process.env.PLUGIN_DATA_FOLDER || 'BlitzSG';
const RUNTIME_CONFIG_TTL_SECONDS = parseInt(process.env.RUNTIME_CONFIG_TTL_SECONDS || '900', 10);
const DB_AUTO_GENERATE = String(process.env.DB_AUTO_GENERATE || 'true').toLowerCase() === 'true';
const DB_TYPE = process.env.DB_TYPE || 'mysql';
const DB_HOST = process.env.DB_HOST || 'localhost';
const DB_PORT = process.env.DB_PORT || '27017';
const DB_NAME = process.env.DB_NAME || 'BlitzSG';
const DB_USERNAME = process.env.DB_USERNAME || 'root';
const DB_PASSWORD = process.env.DB_PASSWORD || 'admin';
const DELETE_INSTANCE_DIR_ON_STOP = String(process.env.DELETE_INSTANCE_DIR_ON_STOP || 'true').toLowerCase() === 'true';

function parsePortRange(input, fallbackStart, fallbackEnd) {
  if (!input || typeof input !== 'string') {
    return { start: fallbackStart, end: fallbackEnd };
  }

  const match = input.trim().match(/^(\d+)\s*-\s*(\d+)$/);
  if (!match) {
    return { start: fallbackStart, end: fallbackEnd };
  }

  const start = parseInt(match[1], 10);
  const end = parseInt(match[2], 10);

  if (!Number.isInteger(start) || !Number.isInteger(end)) {
    return { start: fallbackStart, end: fallbackEnd };
  }

  return { start, end };
}

const fallbackPortStart = parseInt(process.env.PORT_RANGE_START || '25580', 10);
const fallbackPortEnd = parseInt(process.env.PORT_RANGE_END || '25650', 10);
const resolvedPortRange = parsePortRange(PORT_RANGE, fallbackPortStart, fallbackPortEnd);
const PORT_RANGE_START = resolvedPortRange.start;
const PORT_RANGE_END = resolvedPortRange.end;

const CHANNELS = {
  JOIN_REQUEST: 'blitz:match:join-request',
  SPAWN_REQUEST: 'blitz:core:spawn-request',
  STOP_REQUEST: 'blitz:core:stop-request',
  HEARTBEAT: 'blitz:server:heartbeat',
  RUNTIME_CONFIG: 'blitz:server:runtime-config'
};

const instances = new Map();
let proxyRuntime = null;
let redisPub = null;
let redisSub = null;
let isShuttingDown = false;

function redisConnectionOptions() {
  const options = { url: REDIS_URL };
  if (REDIS_PASSWORD) {
    options.password = REDIS_PASSWORD;
  }
  return options;
}

async function initializeRedis() {
  redisPub = createClient(redisConnectionOptions());
  redisSub = createClient(redisConnectionOptions());

  redisPub.on('error', (err) => {
    console.error('Redis publisher error:', err.message);
  });

  redisSub.on('error', (err) => {
    console.error('Redis subscriber error:', err.message);
  });

  await redisPub.connect();
  await redisSub.connect();

  await redisSub.subscribe(CHANNELS.SPAWN_REQUEST, async (message) => {
    try {
      const payload = JSON.parse(message);
      await startFromSpawnRequest(payload);
    } catch (err) {
      console.error('Failed to process spawn request:', err.message);
    }
  });

  await redisSub.subscribe(CHANNELS.STOP_REQUEST, async (message) => {
    try {
      const payload = JSON.parse(message);
      if (payload?.serverId) {
        await stopInstance(payload.serverId, payload.timeoutMs);
      }
    } catch (err) {
      console.error('Failed to process stop request:', err.message);
    }
  });
}

function isPortInUse(port) {
  for (const instance of instances.values()) {
    if (instance.port === port) {
      return true;
    }
  }
  return false;
}

function allocatePort() {
  for (let port = PORT_RANGE_START; port <= PORT_RANGE_END; port += 1) {
    if (!isPortInUse(port)) {
      return port;
    }
  }
  throw new Error(`No available ports in range ${PORT_RANGE_START}-${PORT_RANGE_END}`);
}


function stageWorldAndMapConfig(instance) {
  const normalizedType = String(instance.type || 'game').toUpperCase();
  const mapName = sanitizeMapName(instance.mapName);

  if (normalizedType === 'HUB') {
    const optionalHubWorld = path.join(MAP_WORLD_SOURCE_DIR, 'world');
    if (!fs.existsSync(optionalHubWorld)) {
      console.warn(`No hub world found at ${optionalHubWorld}, skipping world staging for hub instance ${instance.id}`);
      return;
    }

    const worldDestination = path.join(instance.cwd, 'world');
    if (fs.existsSync(worldDestination)) {
      fs.rmSync(worldDestination, { recursive: true, force: true });
    }
    copyDirectoryRecursive(optionalHubWorld, worldDestination);
    return;
  }

  if (!mapName) {
    return;
  }

  const worldTemplateSource = path.join(MAP_WORLD_SOURCE_DIR, mapName);
  if (!fs.existsSync(worldTemplateSource)) {
    throw new Error(`Missing world template: ${worldTemplateSource}`);
  }

  const worldDestination = path.join(instance.cwd, 'world');
  if (fs.existsSync(worldDestination)) {
    fs.rmSync(worldDestination, { recursive: true, force: true });
  }
  copyDirectoryRecursive(worldTemplateSource, worldDestination);

  const mapTemplateDestination = path.join(instance.cwd, 'arenas', mapName);
  if (fs.existsSync(mapTemplateDestination)) {
    fs.rmSync(mapTemplateDestination, { recursive: true, force: true });
  }
  copyDirectoryRecursive(worldTemplateSource, mapTemplateDestination);

  if (normalizedType !== 'HUB') {
    const mapConfigSource = path.join(MAP_CONFIG_SOURCE_DIR, `${mapName}.yml`);
    if (!fs.existsSync(mapConfigSource)) {
      throw new Error(`Missing map config: ${mapConfigSource}`);
    }

    const mapConfigDestination = path.join(instance.cwd, 'plugins', PLUGIN_DATA_FOLDER, 'arenas', `${mapName}.yml`);
    fs.mkdirSync(path.dirname(mapConfigDestination), { recursive: true });
    fs.copyFileSync(mapConfigSource, mapConfigDestination);
  }
}

function copyPluginJars(sourceDir, destinationDir) {
  if (!sourceDir) {
    return;
  }

  if (!fs.existsSync(sourceDir)) {
    throw new Error(`Missing plugins source directory: ${sourceDir}`);
  }

  fs.mkdirSync(destinationDir, { recursive: true });
  const entries = fs.readdirSync(sourceDir, { withFileTypes: true });
  for (const entry of entries) {
    if (!entry.isFile()) {
      continue;
    }
    if (!entry.name.toLowerCase().endsWith('.jar')) {
      continue;
    }

    const sourceJar = path.join(sourceDir, entry.name);
    const destinationJar = path.join(destinationDir, entry.name);
    fs.copyFileSync(sourceJar, destinationJar);
  }
}

function copyBackendFiles(sourceDir, destinationDir) {
  // just copies everything (including folders) to the instance directory, allowing for arbitrary file staging for game servers
  if (!sourceDir) { return; }
  if (!fs.existsSync(sourceDir)) {
    throw new Error(`Missing backend files source directory: ${sourceDir}`);
  }
  copyDirectoryRecursive(sourceDir, destinationDir);
}


function ensureVelocityToml(proxyDir) {
  const velocityTomlPath = path.join(proxyDir, 'velocity.toml');
  if (fs.existsSync(velocityTomlPath)) {
    const existing = fs.readFileSync(velocityTomlPath, 'utf8').trim();
    if (!existing) {
      throw new Error(`velocity.toml exists but is empty: ${velocityTomlPath}`);
    }
    return velocityTomlPath;
  }

  fs.mkdirSync(proxyDir, { recursive: true });

  if (PROXY_VELOCITY_TEMPLATE && fs.existsSync(PROXY_VELOCITY_TEMPLATE)) {
    const templateContent = fs.readFileSync(PROXY_VELOCITY_TEMPLATE, 'utf8').trim();
    if (!templateContent) {
      throw new Error(`velocity template is empty: ${PROXY_VELOCITY_TEMPLATE}`);
    }
    fs.copyFileSync(PROXY_VELOCITY_TEMPLATE, velocityTomlPath);
    return velocityTomlPath;
  }

  throw new Error(`Missing velocity.toml and no template found at ${PROXY_VELOCITY_TEMPLATE}`);
}

function wireVelocityLobby(proxyDir, backendPort) {
  const velocityTomlPath = ensureVelocityToml(proxyDir);
  const defaultHubServerName = `${AUTO_HUB_PREFIX}1`;

  const original = fs.readFileSync(velocityTomlPath, 'utf8');
  let updated = original
    .replace(/player-info-forwarding-mode\s*=\s*"[^"]*"/g, 'player-info-forwarding-mode = "legacy"');

  if (!/player-info-forwarding-mode\s*=\s*"legacy"/m.test(updated)) {
    updated += '\nplayer-info-forwarding-mode = "legacy"\n';
  }

  if (Number.isInteger(backendPort) && backendPort >= 1 && backendPort <= 65535) {
    updated = updated
      .replace(/(lobby|hub-\d+)\s*=\s*"127\.0\.0\.1:\d+"/g, `${defaultHubServerName} = "127.0.0.1:${backendPort}"`)
      .replace(/try\s*=\s*\[[\s\S]*?\]/m, `try = [\n    "${defaultHubServerName}"\n]`);
  }

  if (updated !== original) {
    fs.writeFileSync(velocityTomlPath, updated, 'utf8');
  }
}

function writeInstanceMetadata(instance) {
  const lines = [
    `server.id=${instance.id}`,
    `server.name=${instance.serverName}`,
    `server.type=${instance.type.toUpperCase() === 'HUB' ? 'HUB' : 'GAME'}`,
    `server.host=${PUBLIC_HOST}`,
    `match.map=${instance.mapName || ''}`
  ];
  fs.writeFileSync(path.join(instance.cwd, 'instance.properties'), `${lines.join('\n')}\n`, 'utf8');
}

function writeInstanceDatabaseConfig(workDir) {
  if (!DB_AUTO_GENERATE) {
    return;
  }

  const databaseFile = path.join(workDir, 'plugins', PLUGIN_DATA_FOLDER, 'database.yml');
  fs.mkdirSync(path.dirname(databaseFile), { recursive: true });
  const content = [
    `type: ${DB_TYPE}`,
    `host: ${DB_HOST}`,
    `port: '${DB_PORT}'`,
    `database: ${DB_NAME}`,
    `username: ${DB_USERNAME}`,
    `password: ${DB_PASSWORD}`
  ].join('\n') + '\n';

  fs.writeFileSync(databaseFile, content, 'utf8');
}

function getRuntimeConfigKey(instanceId) {
  return `blitz:server:runtime:${instanceId}`;
}

async function publishRuntimeConfig(instance) {
  if (!redisPub || !redisPub.isReady) {
    return;
  }

  const payload = {
    serverId: instance.id,
    serverName: instance.serverName,
    serverType: instance.type.toUpperCase() === 'HUB' ? 'HUB' : 'GAME',
    mapName: instance.mapName || '',
    timestamp: Date.now()
  };

  const serialized = [
    `serverId=${payload.serverId}`,
    `serverName=${payload.serverName}`,
    `serverType=${payload.serverType}`,
    `mapName=${payload.mapName}`,
    `timestamp=${payload.timestamp}`
  ].join(';');

  try {
    await redisPub.set(getRuntimeConfigKey(instance.id), serialized, {
      EX: Number.isInteger(RUNTIME_CONFIG_TTL_SECONDS) ? RUNTIME_CONFIG_TTL_SECONDS : 900
    });
    await redisPub.publish(CHANNELS.RUNTIME_CONFIG, JSON.stringify(payload));
  } catch (err) {
    console.error(`Failed to publish runtime config for ${instance.id}:`, err.message);
  }
}

function ensureInstanceFilesystem(instanceId, port, workDir) {
  fs.mkdirSync(workDir, { recursive: true });

  const eulaPath = path.join(workDir, 'eula.txt');
  if (!fs.existsSync(eulaPath)) {
    fs.writeFileSync(eulaPath, 'eula=true\n', 'utf8');
  }

  const serverPropertiesPath = path.join(workDir, 'server.properties');
  const baseProperties = [
    `server-port=${port}`,
    `motd=Blitz Instance ${instanceId}`,
    'online-mode=false',
    'level-name=world',
    'allow-nether=false',
    'spawn-protection=0',
    'enable-command-block=false'
  ].join('\n') + '\n';
  fs.writeFileSync(serverPropertiesPath, baseProperties, 'utf8');

  const bukkitConfigPath = path.join(workDir, 'bukkit.yml');
  const bukkitConfig = [
    'settings:',
    '  allow-end: false'
  ].join('\n') + '\n';
  fs.writeFileSync(bukkitConfigPath, bukkitConfig, 'utf8');

  const spigotConfigPath = path.join(workDir, 'spigot.yml');
  const spigotConfig = [
    'settings:',
    '  baby-zombie-movement-speed: 0.5',
    '  limit-player-interactions: true',
    '  debug: false',
    '  save-user-cache-on-stop-only: false',
    '  late-bind: false',
    '  bungeecord: true'
  ].join('\n') + '\n';
  fs.writeFileSync(spigotConfigPath, spigotConfig, 'utf8');

  if (BACKEND_PLUGINS_SOURCE_DIR) {
    copyPluginJars(BACKEND_PLUGINS_SOURCE_DIR, path.join(workDir, 'plugins'));
  }

  if (BACKEND_FILES_SOURCE_DIR) {
    copyBackendFiles(BACKEND_FILES_SOURCE_DIR, workDir);
  }


  writeInstanceDatabaseConfig(workDir);
}

function ensureLocalServerJar(sourceJarPath, workDir, targetFileName) {
  const destinationJarPath = path.join(workDir, targetFileName);
  fs.copyFileSync(sourceJarPath, destinationJarPath);
  return destinationJarPath;
}

function cleanupStoppedInstanceDirectory(instance) {
  if (!DELETE_INSTANCE_DIR_ON_STOP || !instance || !instance.cwd) {
    return;
  }

  if (!isInsidePath(INSTANCES_ROOT, instance.cwd)) {
    return;
  }

  try {
    fs.rmSync(instance.cwd, { recursive: true, force: true });
  } catch (err) {
    console.error(`Failed to remove instance directory ${instance.cwd}:`, err.message);
  }
}

function cleanupAllInstanceDirectories() {
  if (!DELETE_INSTANCE_DIR_ON_STOP) {
    return;
  }

  if (!fs.existsSync(INSTANCES_ROOT)) {
    return;
  }

  for (const entry of fs.readdirSync(INSTANCES_ROOT, { withFileTypes: true })) {
    const target = path.join(INSTANCES_ROOT, entry.name);
    try {
      fs.rmSync(target, { recursive: true, force: true });
    } catch (err) {
      console.error(`Failed to remove ${target}:`, err.message);
    }
  }
}

async function publishHeartbeat(instance, stateOverride) {
  if (!redisPub || !redisPub.isReady) {
    return;
  }

  const heartbeat = {
    serverId: instance.id,
    serverName: instance.serverName,
    serverType: instance.type.toUpperCase() === 'HUB' ? 'HUB' : 'GAME',
    state: stateOverride || instance.status,
    mapName: instance.mapName || null,
    host: PUBLIC_HOST,
    port: instance.port,
    currentPlayers: 0,
    maxPlayers: instance.maxPlayers,
    timestamp: Date.now()
  };

  try {
    await redisPub.publish(CHANNELS.HEARTBEAT, JSON.stringify(heartbeat));
  } catch (err) {
    console.error(`Failed to publish heartbeat for ${instance.id}:`, err.message);
  }
}

function buildJavaArgs(payload) {
  const maxMemoryMb = Number.isInteger(payload.maxMemoryMb) ? payload.maxMemoryMb : 1024;
  const minMemoryMb = Number.isInteger(payload.minMemoryMb) ? payload.minMemoryMb : Math.min(512, maxMemoryMb);
  const jvmArgs = Array.isArray(payload.jvmArgs) ? payload.jvmArgs : [];
  const programArgs = Array.isArray(payload.programArgs) ? payload.programArgs : ['nogui'];

  return [
    `-Xms${minMemoryMb}M`,
    `-Xmx${maxMemoryMb}M`,
    ...jvmArgs,
    '-jar',
    payload.jarPath,
    ...programArgs
  ];
}

function serializeInstance(instance) {
  return {
    id: instance.id,
    type: instance.type,
    port: instance.port,
    jarPath: instance.jarPath,
    cwd: instance.cwd,
    pid: instance.process.pid,
    startedAt: instance.startedAt,
    status: instance.status
  };
}

function getPortUsageSummary() {
  const totalPorts = Math.max(0, PORT_RANGE_END - PORT_RANGE_START + 1);
  const usedPorts = Array.from(instances.values())
    .map((instance) => instance.port)
    .sort((a, b) => a - b);
  const usedCount = usedPorts.length;
  const freeCount = Math.max(0, totalPorts - usedCount);

  return {
    rangeStart: PORT_RANGE_START,
    rangeEnd: PORT_RANGE_END,
    totalPorts,
    usedCount,
    freeCount,
    usedPorts
  };
}

function serializeProxy() {
  if (!proxyRuntime) {
    return null;
  }

  return {
    id: proxyRuntime.id,
    type: 'PROXY',
    jarPath: proxyRuntime.jarPath,
    cwd: proxyRuntime.cwd,
    pid: proxyRuntime.process ? proxyRuntime.process.pid : null,
    startedAt: proxyRuntime.startedAt,
    status: proxyRuntime.status,
    exitCode: proxyRuntime.exitCode,
    signal: proxyRuntime.signal
  };
}

function buildProxyJavaArgs(payload) {
  const maxMemoryMb = Number.isInteger(payload.maxMemoryMb) ? payload.maxMemoryMb : 1024;
  const minMemoryMb = Number.isInteger(payload.minMemoryMb) ? payload.minMemoryMb : Math.min(512, maxMemoryMb);
  const jvmArgs = Array.isArray(payload.jvmArgs) ? payload.jvmArgs : [];
  const programArgs = Array.isArray(payload.programArgs) ? payload.programArgs : [];

  return [
    `-Xms${minMemoryMb}M`,
    `-Xmx${maxMemoryMb}M`,
    ...jvmArgs,
    '-jar',
    payload.jarPath,
    ...programArgs
  ];
}

function startProxy(payload = {}) {
  if (proxyRuntime && proxyRuntime.status !== 'OFFLINE') {
    return { error: 'proxy already running', status: 409 };
  }

  const jarPath = payload.jarPath || PROXY_SERVER_JAR;
  if (!jarPath || typeof jarPath !== 'string') {
    return { error: 'proxy jarPath is required', status: 400 };
  }

  const resolvedJar = path.resolve(jarPath);
  if (!fs.existsSync(resolvedJar)) {
    return { error: `proxy jar not found: ${resolvedJar}`, status: 404 };
  }

  const proxyWorkDir = payload.workDir ? path.resolve(payload.workDir) : PROXY_WORKDIR;
  fs.mkdirSync(proxyWorkDir, { recursive: true });
  const localProxyJar = ensureLocalServerJar(resolvedJar, proxyWorkDir, 'proxy.jar');
  if (PROXY_PLUGINS_SOURCE_DIR) {
    copyPluginJars(PROXY_PLUGINS_SOURCE_DIR, path.join(proxyWorkDir, 'plugins'));
  }
  try {
    wireVelocityLobby(proxyWorkDir, payload.initialServerPort);
  } catch (err) {
    return { error: err.message, status: 500 };
  }

  const runtime = {
    id: payload.id || 'proxy-1',
    jarPath: localProxyJar,
    cwd: proxyWorkDir,
    process: null,
    startedAt: Date.now(),
    status: 'BOOTING',
    logs: [],
    logFile: path.join(proxyWorkDir, 'logs', 'core-proxy.log')
  };

  const args = buildProxyJavaArgs({ ...payload, jarPath: localProxyJar });
  const proc = spawn(JAVA_BIN, args, {
    cwd: proxyWorkDir,
    stdio: ['pipe', 'pipe', 'pipe']
  });

  runtime.process = proc;
  proxyRuntime = runtime;

  proc.stdout.on('data', (buffer) => {
    const line = buffer.toString();
    runtime.logs.push(`[OUT] ${line}`);
    appendLogLine(runtime.logFile, `[OUT] ${line}`);
    if (runtime.logs.length > 300) {
      runtime.logs.shift();
    }
    if (runtime.status !== 'READY' && isProxyReadyLogLine(line)) {
      runtime.status = 'READY';
    }
  });

  proc.stderr.on('data', (buffer) => {
    const line = buffer.toString();
    runtime.logs.push(`[ERR] ${line}`);
    appendLogLine(runtime.logFile, `[ERR] ${line}`);
    if (runtime.logs.length > 300) {
      runtime.logs.shift();
    }
  });

  proc.on('exit', (code, signal) => {
    runtime.status = 'OFFLINE';
    runtime.exitCode = code;
    runtime.signal = signal;
  });

  return {
    status: 201,
    payload: {
      message: 'proxy started',
      proxy: serializeProxy(),
      command: [JAVA_BIN, ...args]
    }
  };
}

async function stopProxy(timeoutMs) {
  if (!proxyRuntime || proxyRuntime.status === 'OFFLINE') {
    return;
  }

  proxyRuntime.status = 'DRAINING';
  await new Promise((resolve) => {
    const timer = setTimeout(() => {
      if (proxyRuntime && proxyRuntime.process) {
        proxyRuntime.process.kill('SIGKILL');
      }
      resolve();
    }, Number.isInteger(timeoutMs) ? timeoutMs : DEFAULT_STOP_TIMEOUT_MS);

    proxyRuntime.process.once('exit', () => {
      clearTimeout(timer);
      resolve();
    });

    proxyRuntime.process.stdin.write('shutdown\n');
  });
}

async function bootstrapNetwork(payload = {}) {
  const response = {
    proxy: null,
    hubs: []
  };

  const hubCount = Number.isInteger(payload.hubCount)
    ? payload.hubCount
    : (Number.isInteger(AUTO_START_HUBS) ? AUTO_START_HUBS : 0);
  const hubPrefix = payload.hubPrefix || AUTO_HUB_PREFIX;
  const hubJarPath = payload.hubJarPath || HUB_SERVER_JAR;

  for (let index = 1; index <= hubCount; index += 1) {
    const id = `${hubPrefix}${index}`;
    if (instances.has(id)) {
      response.hubs.push({ id, status: 'ALREADY_RUNNING' });
      continue;
    }

    const startResult = startInstance({
      id,
      serverName: id,
      type: 'hub',
      mapName: '',
      jarPath: hubJarPath,
      maxPlayers: Number.isInteger(payload.hubMaxPlayers) ? payload.hubMaxPlayers : 200,
      maxMemoryMb: Number.isInteger(payload.hubMaxMemoryMb) ? payload.hubMaxMemoryMb : 1024,
      minMemoryMb: Number.isInteger(payload.hubMinMemoryMb) ? payload.hubMinMemoryMb : 512
    });

    if (startResult.error) {
      throw new Error(`failed to start hub ${id}: ${startResult.error}`);
    }

    response.hubs.push(startResult.payload.server);
  }

  const shouldStartProxy = payload.startProxy !== false;
  if (shouldStartProxy) {
    const firstHubPort = response.hubs.length > 0 && Number.isInteger(response.hubs[0].port)
      ? response.hubs[0].port
      : null;

    const proxyStartResult = startProxy({
      ...(payload.proxy || {}),
      initialServerPort: firstHubPort
    });
    if (proxyStartResult.error && proxyStartResult.status !== 409) {
      throw new Error(proxyStartResult.error);
    }
    response.proxy = serializeProxy();
  }

  return response;
}

function startInstance(payload) {
  const id = payload.id;
  const requestedPort = Number.isInteger(payload.port) ? payload.port : allocatePort();
  const jarPath = payload.jarPath || GAME_SERVER_JAR;

  if (!id || typeof id !== 'string') {
    return { error: 'id is required', status: 400 };
  }

  if (!Number.isInteger(requestedPort) || requestedPort < 1 || requestedPort > 65535) {
    return { error: 'port must be an integer between 1 and 65535', status: 400 };
  }

  if (!jarPath || typeof jarPath !== 'string') {
    return { error: 'jarPath is required', status: 400 };
  }

  if (instances.has(id)) {
    return { error: `instance ${id} already running`, status: 409 };
  }

  for (const existing of instances.values()) {
    if (existing.port === requestedPort) {
      return { error: `port ${requestedPort} already in use by ${existing.id}`, status: 409 };
    }
  }

  const resolvedJar = path.resolve(jarPath);
  if (!fs.existsSync(resolvedJar)) {
    return { error: `jar not found: ${resolvedJar}`, status: 404 };
  }

  const instanceDir = payload.workDir ? path.resolve(payload.workDir) : path.join(INSTANCES_ROOT, id);
  const instance = {
    id,
    serverName: payload.serverName || id,
    type: payload.type || 'game',
    port: requestedPort,
    mapName: sanitizeMapName(payload.mapName),
    maxPlayers: Number.isInteger(payload.maxPlayers) ? payload.maxPlayers : 24,
    jarPath: resolvedJar,
    cwd: instanceDir,
    process: null,
    startedAt: Date.now(),
    status: 'BOOTING',
    logs: [],
    logFile: path.join(instanceDir, 'logs', 'core-instance.log')
  };

  try {
    ensureInstanceFilesystem(id, requestedPort, instanceDir);
    const localJarName = String(instance.type || 'game').toUpperCase() === 'HUB' ? 'hub-server.jar' : 'game-server.jar';
    instance.jarPath = ensureLocalServerJar(resolvedJar, instanceDir, localJarName);
    stageWorldAndMapConfig(instance);
    writeInstanceMetadata(instance);
  } catch (err) {
    return { error: err.message, status: 500 };
  }

  const args = buildJavaArgs({ ...payload, jarPath: instance.jarPath });
  const proc = spawn(JAVA_BIN, args, {
    cwd: instanceDir,
    stdio: ['pipe', 'pipe', 'pipe']
  });

  instance.process = proc;

  publishRuntimeConfig(instance).catch(() => {});
  publishHeartbeat(instance).catch(() => {});

  proc.stdout.on('data', (buffer) => {
    const line = buffer.toString();
    instance.logs.push(`[OUT] ${line}`);
    appendLogLine(instance.logFile, `[OUT] ${line}`);
    if (instance.logs.length > 200) {
      instance.logs.shift();
    }
    if (instance.status !== 'READY' && isBackendReadyLogLine(line)) {
      instance.status = 'READY';
      publishHeartbeat(instance, 'READY').catch(() => {});
    }
  });

  proc.stderr.on('data', (buffer) => {
    const line = buffer.toString();
    instance.logs.push(`[ERR] ${line}`);
    appendLogLine(instance.logFile, `[ERR] ${line}`);
    if (instance.logs.length > 200) {
      instance.logs.shift();
    }
  });

  proc.on('exit', (code, signal) => {
    instance.status = 'OFFLINE';
    instance.exitCode = code;
    instance.signal = signal;
    publishHeartbeat(instance, 'OFFLINE').catch(() => {});
    instances.delete(id);
  });

  instances.set(id, instance);

  return {
    status: 201,
    payload: {
      message: `started ${id}`,
      server: serializeInstance(instance),
      command: [JAVA_BIN, ...args]
    }
  };
}

registerRoutes(app, {
  instances,
  serializeProxy,
  serializeInstance,
  getPortUsageSummary,
  startInstance,
  stopInstance,
  startProxy,
  stopProxy,
  bootstrapNetwork,
  stopAllInstances,
  DEFAULT_STOP_TIMEOUT_MS
});

async function stopInstance(id, timeoutMs) {
  const instance = instances.get(id);
  if (!instance) {
    return;
  }

  instance.status = 'DRAINING';
  await publishHeartbeat(instance, 'DRAINING');

  await new Promise((resolve) => {
    const timer = setTimeout(() => {
      if (instance.process && !instance.process.killed) {
        instance.process.kill('SIGKILL');
      }
      resolve();
    }, Number.isInteger(timeoutMs) ? timeoutMs : DEFAULT_STOP_TIMEOUT_MS);

    if (!instance.process) {
      clearTimeout(timer);
      resolve();
      return;
    }

    instance.process.once('exit', () => {
      clearTimeout(timer);
      resolve();
    });

    try {
      if (instance.process.stdin && instance.process.stdin.writable) {
        instance.process.stdin.write('stop\n');
      }
    } catch (err) {
      clearTimeout(timer);
      resolve();
    }
  });

  cleanupStoppedInstanceDirectory(instance);
}

async function stopAllInstances(timeoutMs) {
  const stoppedServers = Array.from(instances.keys());
  for (const id of stoppedServers) {
    await stopInstance(id, timeoutMs);
  }
  await stopProxy(timeoutMs);
  return stoppedServers;
}

async function gracefulShutdown(reason) {
  if (isShuttingDown) {
    return;
  }

  isShuttingDown = true;
  console.log(`[core] shutdown requested (${reason})`);

  try {
    await stopAllInstances(DEFAULT_STOP_TIMEOUT_MS);
  } catch (err) {
    console.error('[core] error while stopping instances during shutdown:', err.message);
  }

  try {
    cleanupAllInstanceDirectories();
  } catch (err) {
    console.error('[core] error while cleaning instance directories:', err.message);
  }

  try {
    if (redisSub && redisSub.isReady) {
      await redisSub.quit();
    }
    if (redisPub && redisPub.isReady) {
      await redisPub.quit();
    }
  } catch (err) {
    console.error('[core] error while closing redis connections:', err.message);
  }

  process.exit(0);
}

async function startFromSpawnRequest(payload) {
  const serverType = (payload?.serverType || 'GAME').toUpperCase();
  const mapName = sanitizeMapName(payload?.mapName);
  if (serverType !== 'HUB' && !mapName) {
    throw new Error('Spawn request missing mapName');
  }

  if (mapName) {
    for (const instance of instances.values()) {
      if (instance.mapName === mapName && instance.status !== 'OFFLINE') {
        return;
      }
    }
  }

  const instanceId = payload.preferredServerId || `${mapName}-${Date.now()}`;
  const instancePort = Number.isInteger(payload.preferredPort) ? payload.preferredPort : allocatePort();

  const result = startInstance({
    id: instanceId,
    serverName: instanceId,
    type: serverType.toLowerCase(),
    mapName,
    port: instancePort,
    maxPlayers: Number.isInteger(payload.maxPlayers) ? payload.maxPlayers : 24,
    jarPath: GAME_SERVER_JAR
  });

  if (result.error) {
    throw new Error(result.error);
  }
}

setInterval(() => {
  for (const instance of instances.values()) {
    publishHeartbeat(instance).catch(() => {});
  }
}, 5000);

process.on('SIGINT', () => {
  gracefulShutdown('SIGINT').catch((err) => {
    console.error('[core] shutdown failure:', err.message);
    process.exit(1);
  });
});

process.on('SIGTERM', () => {
  gracefulShutdown('SIGTERM').catch((err) => {
    console.error('[core] shutdown failure:', err.message);
    process.exit(1);
  });
});

app.listen(CONTROL_PORT, () => {
  fs.mkdirSync(INSTANCES_ROOT, { recursive: true });

  if (!Number.isInteger(PORT_RANGE_START)
    || !Number.isInteger(PORT_RANGE_END)
    || PORT_RANGE_START < 1
    || PORT_RANGE_END > 65535
    || PORT_RANGE_START > PORT_RANGE_END) {
    console.error(`Invalid port range ${PORT_RANGE_START}-${PORT_RANGE_END}`);
    process.exit(1);
  }

  initializeRedis().catch((err) => {
    console.error('Failed to initialize Redis:', err.message);
    process.exit(1);
  });

  if (AUTO_START_PROXY || AUTO_START_HUBS > 0) {
    bootstrapNetwork({
      startProxy: AUTO_START_PROXY,
      hubCount: AUTO_START_HUBS,
      hubPrefix: AUTO_HUB_PREFIX
    }).catch((err) => {
      console.error('Failed to bootstrap network automatically:', err.message);
    });
  }

  initializeCli({
    serializeProxy,
    serializeInstance,
    instances,
    bootstrapNetwork,
    stopAllInstances,
    startInstance,
    stopInstance,
    startProxy,
    stopProxy,
    AUTO_START_HUBS,
    DEFAULT_STOP_TIMEOUT_MS,
    HUB_SERVER_JAR,
    GAME_SERVER_JAR
  });

  console.log(`blitz-core listening on ${CONTROL_PORT} with instance port range ${PORT_RANGE_START}-${PORT_RANGE_END}`);
});
