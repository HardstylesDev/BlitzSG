const fs = require('fs');
const path = require('path');
const { sanitizeMapName, copyDirectoryRecursive } = require('../lib/mapUtils');
const { isInsidePath } = require('../lib/pathUtils');

function createFilesystemService(config) {
  const {
    MAP_WORLD_SOURCE_DIR,
    MAP_CONFIG_SOURCE_DIR,
    PROXY_VELOCITY_TEMPLATE,
    PLUGIN_DATA_FOLDER,
    PUBLIC_HOST,
    DB_AUTO_GENERATE,
    DB_TYPE,
    DB_HOST,
    DB_PORT,
    DB_NAME,
    DB_USERNAME,
    DB_PASSWORD,
    BACKEND_PLUGINS_SOURCE_DIR,
    INSTANCES_ROOT,
    DELETE_INSTANCE_DIR_ON_STOP
  } = config;

  function stageWorldAndMapConfig(instance) {
    const normalizedType = String(instance.type || 'game').toUpperCase();
    const mapName = sanitizeMapName(instance.mapName);

    if (normalizedType === 'HUB') {
      const optionalHubWorld = path.join(MAP_WORLD_SOURCE_DIR, 'world');
      if (!fs.existsSync(optionalHubWorld)) {
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

    const mapConfigSource = path.join(MAP_CONFIG_SOURCE_DIR, `${mapName}.yml`);
    if (!fs.existsSync(mapConfigSource)) {
      throw new Error(`Missing map config: ${mapConfigSource}`);
    }

    const mapConfigDestination = path.join(instance.cwd, 'plugins', PLUGIN_DATA_FOLDER, 'arenas', `${mapName}.yml`);
    fs.mkdirSync(path.dirname(mapConfigDestination), { recursive: true });
    fs.copyFileSync(mapConfigSource, mapConfigDestination);
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

  return {
    stageWorldAndMapConfig,
    copyPluginJars,
    wireVelocityLobby,
    writeInstanceMetadata,
    writeInstanceDatabaseConfig,
    ensureInstanceFilesystem,
    ensureLocalServerJar,
    cleanupStoppedInstanceDirectory,
    cleanupAllInstanceDirectories
  };
}

module.exports = {
  createFilesystemService
};
