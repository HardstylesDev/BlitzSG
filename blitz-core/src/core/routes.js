function registerRoutes(app, deps) {
  const {
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
  } = deps;

  app.get('/health', (req, res) => {
    res.json({
      ok: true,
      instances: instances.size,
      proxy: serializeProxy(),
      portRange: getPortUsageSummary()
    });
  });

  app.get('/servers', (req, res) => {
    const list = Array.from(instances.values()).map(serializeInstance);
    res.json({
      count: list.length,
      servers: list,
      portRange: getPortUsageSummary()
    });
  });

  app.get('/network', (req, res) => {
    const list = Array.from(instances.values()).map(serializeInstance);
    return res.json({
      proxy: serializeProxy(),
      count: list.length,
      servers: list,
      portRange: getPortUsageSummary(),
      timestamp: Date.now()
    });
  });

  app.get('/servers/:id', (req, res) => {
    const instance = instances.get(req.params.id);
    if (!instance) {
      return res.status(404).json({ error: 'instance not found' });
    }

    return res.json({
      server: serializeInstance(instance),
      recentLogs: instance.logs.slice(-20)
    });
  });

  app.get('/ports', (req, res) => {
    res.json(getPortUsageSummary());
  });

  app.get('/metrics', (req, res) => {
    const byStatus = {};
    const byType = {};
    for (const instance of instances.values()) {
      byStatus[instance.status] = (byStatus[instance.status] || 0) + 1;
      const normalizedType = (instance.type || 'game').toUpperCase();
      byType[normalizedType] = (byType[normalizedType] || 0) + 1;
    }

    res.json({
      instances: instances.size,
      byStatus,
      byType,
      portRange: getPortUsageSummary(),
      timestamp: Date.now()
    });
  });

  app.post('/servers/start', (req, res) => {
    const result = startInstance(req.body || {});
    if (result.error) {
      return res.status(result.status).json({ error: result.error });
    }
    return res.status(result.status).json(result.payload);
  });

  app.post('/servers/stop/:id', async (req, res) => {
    const id = req.params.id;
    const instance = instances.get(id);
    if (!instance) {
      return res.status(404).json({ error: `instance ${id} not found` });
    }

    const timeoutMs = Number.isInteger(req.body?.timeoutMs)
      ? req.body.timeoutMs
      : DEFAULT_STOP_TIMEOUT_MS;

    await stopInstance(id, timeoutMs);
    return res.json({ message: `${id} stopped` });
  });

  app.post('/network/start', async (req, res) => {
    try {
      const payload = req.body || {};
      const network = await bootstrapNetwork(payload);
      return res.status(201).json({
        message: 'network bootstrapped',
        network
      });
    } catch (err) {
      return res.status(500).json({ error: err.message });
    }
  });

  async function handleNetworkStop(req, res) {
    const timeoutMs = Number.isInteger(req.body?.timeoutMs)
      ? req.body.timeoutMs
      : DEFAULT_STOP_TIMEOUT_MS;

    const stoppedServers = await stopAllInstances(timeoutMs);

    return res.json({
      message: 'network stopped',
      stoppedServers,
      proxy: serializeProxy()
    });
  }

  app.post('/network/stop', handleNetworkStop);
  app.post('/stopnetwork/', handleNetworkStop);

  app.post('/proxy/start', (req, res) => {
    const result = startProxy(req.body || {});
    if (result.error) {
      return res.status(result.status).json({ error: result.error });
    }
    return res.status(result.status).json(result.payload);
  });

  app.post('/proxy/stop', async (req, res) => {
    const timeoutMs = Number.isInteger(req.body?.timeoutMs)
      ? req.body.timeoutMs
      : DEFAULT_STOP_TIMEOUT_MS;

    await stopProxy(timeoutMs);
    return res.json({ message: 'proxy stopped' });
  });

  app.get('/servers/:id/logs', (req, res) => {
    const instance = instances.get(req.params.id);
    if (!instance) {
      return res.status(404).json({ error: 'instance not found' });
    }

    return res.json({
      id: instance.id,
      status: instance.status,
      logs: instance.logs
    });
  });
}

module.exports = {
  registerRoutes
};
