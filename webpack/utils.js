const fs = require('fs');
const path = require('path');
const dateFormat = require('dateformat');

module.exports = {
    parseVersion,
    root,
    isExternalLib
};

const parseString = require('xml2js').parseString;
// return the version number from `pom.xml` file
function parseVersion() {
    let version = null;
    let pomVersion = null;
    const pomXml = fs.readFileSync('pom.xml', 'utf8');
    parseString(pomXml, (err, result) => {
        if (err) {
            throw new Error('Failed to parse pom.xml: ' + err);
        }
        if (result.project.version && result.project.version[0]) {
            pomVersion = result.project.version[0];
        } else if (result.project.parent && result.project.parent[0] && result.project.parent[0].version && result.project.parent[0].version[0]) {
            pomVersion = result.project.parent[0].version[0];
        }
    });
    if (pomVersion === null) {
        throw new Error('pom.xml is malformed. No version is defined');
    }
    
    var now = new Date();
    const buildTimestamp = dateFormat(now, 'yyyy-mm-dd');
    version = pomVersion + ' ' + buildTimestamp;
    return version;
}

const _root = path.resolve(__dirname, '..');

function root(args) {
  args = Array.prototype.slice.call(arguments, 0);
  return path.join.apply(path, [_root].concat(args));
}

function isExternalLib(module, check = /node_modules/) {
    const req = module.userRequest;
    if (typeof req !== 'string') {
        return false;
    }
    return req.search(check) >= 0;
}
