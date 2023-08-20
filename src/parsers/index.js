// This file is the entrypoint

/* eslint-disable import/no-dynamic-require, @typescript-eslint/no-var-requires */

const { execSync } = require("node:child_process");
const { existsSync } = require("node:fs");
const { join } = require("node:path");

const entrypoint = "../../dist/parsers/main";

// if the project has not yet been compiled, invoke the compiler
// eslint-disable-next-line unicorn/no-negated-condition
if (!existsSync(join(__dirname, entrypoint, ".."))) {
  execSync("npm run typecheck", { cwd: join(__dirname, "../..") });
}

// now start the program
require(entrypoint);
