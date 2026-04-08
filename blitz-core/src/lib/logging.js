const fs = require('fs');
const path = require('path');

function appendLogLine(filePath, line) {
  fs.mkdirSync(path.dirname(filePath), { recursive: true });
  fs.appendFileSync(filePath, `${line.endsWith('\n') ? line : `${line}\n`}`, 'utf8');
}

module.exports = {
  appendLogLine
};
