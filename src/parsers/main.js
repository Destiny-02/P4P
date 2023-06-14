// @ts-check
const { promises: fs } = require("node:fs");
const { join } = require("node:path");
const { getParserForLanguage } = require("./getParserForLanguage");

async function main() {
  const fileNames = process.argv[2].split("📚").map((file) => file.trim());
  let outputPath = join(process.cwd(), "parser-output.json");
  if (process.argv.length >= 3) outputPath = process.argv[3];

  if (!fileNames?.length) {
    console.error(
      "You need to run this script as `node parsers/language 'file1.java, file2.py, ...'`"
    );
    return;
  }

  console.log(`\t Parsing ${fileNames.length} files...`);

  /** @type {Record<string, import("./languages/Parser").Parser.Result>} */
  const output = {};
  for (const fileName of fileNames) {
    const parser = getParserForLanguage(fileName);
    if (!parser) {
      console.log(`Skipping unintelligible file: ${fileName}`);
      continue;
    }

    output[fileName] = await parser.parse(fileName);
  }

  await fs.writeFile(outputPath, JSON.stringify(output, null, 2));
  // console.log("Saved to", outputPath);
}

main();
