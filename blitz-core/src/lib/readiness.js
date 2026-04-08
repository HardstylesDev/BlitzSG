function isBackendReadyLogLine(line) {
  if (!line) {
    return false;
  }

  const normalized = line.toLowerCase();
  return normalized.includes('done')
    || normalized.includes('finished loading the server')
    || normalized.includes('timings reset');
}

function isProxyReadyLogLine(line) {
  if (!line) {
    return false;
  }

  const normalized = line.toLowerCase();
  return normalized.includes('done')
    || normalized.includes('proxy initialized')
    || normalized.includes('started in')
    || normalized.includes('listening on');
}

module.exports = {
  isBackendReadyLogLine,
  isProxyReadyLogLine
};
