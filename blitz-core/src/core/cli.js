const readline = require('readline');

function initializeCli(deps) {
  const {
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
  } = deps;

  if (!process.stdin.isTTY || !process.stdout.isTTY) {
    return;
  }

  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
    terminal: true
  });

  console.log('[core-cli] Ready. Commands: list, network-start [hubs], network-stop, start <id> <type> [map], stop <id>, logs <id>, proxy-start, proxy-stop');

  rl.on('line', async (raw) => {
    const input = (raw || '').trim();
    if (!input) {
      return;
    }

    const [command, ...args] = input.split(/\s+/);
    try {
      switch (command.toLowerCase()) {
        case 'list': {
          console.log(JSON.stringify({ proxy: serializeProxy(), servers: Array.from(instances.values()).map(serializeInstance) }, null, 2));
          break;
        }
        case 'network-start': {
          const hubCount = args[0] ? parseInt(args[0], 10) : AUTO_START_HUBS;
          const network = await bootstrapNetwork({ startProxy: true, hubCount: Number.isInteger(hubCount) ? hubCount : 0 });
          console.log('[core-cli] network started', JSON.stringify(network, null, 2));
          break;
        }
        case 'network-stop': {
          await stopAllInstances(DEFAULT_STOP_TIMEOUT_MS);
          console.log('[core-cli] network stopped');
          break;
        }
        case 'start': {
          const id = args[0];
          const type = (args[1] || 'game').toLowerCase();
          const mapName = args[2] || '';
          if (!id) {
            console.log('[core-cli] usage: start <id> <type> [mapName]');
            break;
          }
          const result = startInstance({
            id,
            serverName: id,
            type,
            mapName,
            jarPath: type === 'hub' ? HUB_SERVER_JAR : GAME_SERVER_JAR
          });
          if (result.error) {
            console.log(`[core-cli] start failed: ${result.error}`);
          } else {
            console.log(`[core-cli] started ${id}`);
          }
          break;
        }
        case 'stop': {
          const id = args[0];
          if (!id) {
            console.log('[core-cli] usage: stop <id>');
            break;
          }
          await stopInstance(id, DEFAULT_STOP_TIMEOUT_MS);
          console.log(`[core-cli] stop requested for ${id}`);
          break;
        }
        case 'logs': {
          const id = args[0];
          if (!id) {
            console.log('[core-cli] usage: logs <id>');
            break;
          }
          const instance = instances.get(id);
          if (!instance) {
            console.log(`[core-cli] instance not found: ${id}`);
            break;
          }
          console.log(instance.logs.slice(-40).join(''));
          break;
        }
        case 'proxy-start': {
          const result = startProxy({});
          if (result.error) {
            console.log(`[core-cli] proxy start failed: ${result.error}`);
          } else {
            console.log('[core-cli] proxy started');
          }
          break;
        }
        case 'proxy-stop': {
          await stopProxy(DEFAULT_STOP_TIMEOUT_MS);
          console.log('[core-cli] proxy stop requested');
          break;
        }
        default:
          console.log('[core-cli] unknown command');
      }
    } catch (err) {
      console.error('[core-cli] command error:', err.message);
    }
  });
}

module.exports = {
  initializeCli
};
