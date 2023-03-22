const { promises: fs } = require("node:fs");
const { join } = require("node:path");

const javaParser = require("./java");
const javascriptParser = require("./javascript");
const pythonParser = require("./python");

// helper
function readSampleFile(fileName) {
  return fs.readFile(join(__dirname, `../input/${fileName}`), "utf8");
}

// helper
function save(language, result) {
  return fs.writeFile(
    join(__dirname, `../output/${language}.json`),
    JSON.stringify(result, null, 2)
  );
}

async function main() {
  await fs.mkdir(join(__dirname, "../output"), { recursive: true });

  await save(
    "java",
    await javaParser.parse(await readSampleFile("Sample.java"))
  );

  await save(
    "js",
    await javascriptParser.parse(await readSampleFile("sample.js"))
  );

  await save("py", await pythonParser.parse(await readSampleFile("sample.py")));
}

main();
