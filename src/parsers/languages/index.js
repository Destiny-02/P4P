const { promises: fs } = require("node:fs");
const { join } = require("node:path");

const javaParser = require("./java");
const javascriptParser = require("./javascript");
const pythonParser = require("./python");

async function main() {
  const fileNames = process.argv[2].split("📚").map((file) => file.trim());

  if (!fileNames?.length) {
    console.error(
      "You need to run this script as `node parsers/language 'file1.java, file2.py, ...'`"
    );
    return;
  }

  console.log(`\t Parsing ${fileNames.length} files...`);

  /** @type {Record<string, import("./Parser").Parser.Result>} */
  const output = {};
  for (const fileName of fileNames) {
    const parser = fileName.endsWith(".java")
      ? javaParser
      : fileName.endsWith(".js")
      ? javascriptParser
      : fileName.endsWith(".py")
      ? pythonParser
      : undefined;

    if (!parser) {
      console.log(`Skipping unintelligible file: ${fileName}`);
      continue;
    }

    const fileContents = await fs.readFile(fileName, "utf8");
    output[fileName] = await javaParser.parse(fileContents);
  }

  const outputPath = join(process.cwd(), "parser-output.json");
  await fs.writeFile(outputPath, JSON.stringify(output, null, 2));
  // console.log("Saved to", outputPath);
}

main();
