const path = require('path');

function isInsidePath(parentPath, targetPath) {
  const relative = path.relative(path.resolve(parentPath), path.resolve(targetPath));
  return relative && !relative.startsWith('..') && !path.isAbsolute(relative);
}

module.exports = {
  isInsidePath
};
