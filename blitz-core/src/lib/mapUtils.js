const fs = require('fs');
const path = require('path');

function sanitizeMapName(mapName) {
  return (mapName || '').trim().toLowerCase();
}

function copyDirectoryRecursive(source, destination) {
  if (!fs.existsSync(source)) {
    throw new Error(`Missing source directory: ${source}`);
  }

  fs.mkdirSync(destination, { recursive: true });
  for (const entry of fs.readdirSync(source, { withFileTypes: true })) {
    const srcPath = path.join(source, entry.name);
    const dstPath = path.join(destination, entry.name);

    if (entry.isDirectory()) {
      copyDirectoryRecursive(srcPath, dstPath);
      continue;
    }

    fs.copyFileSync(srcPath, dstPath);
  }
}

module.exports = {
  sanitizeMapName,
  copyDirectoryRecursive
};
