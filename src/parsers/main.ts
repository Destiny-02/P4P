import { promises as fs } from "node:fs";
import { join } from "node:path";
import { Parser } from "./Parser";
import { getParserForLanguage } from "./getParserForLanguage";

async function main() {
  const fileNames = process.argv[2].split("📚").map((file) => file.replaceAll("^", "").trim());

  // always save to repo root, no matter where this script is executed from
  const outputPath = join(__dirname, "../../parser-output.json");

  if (!fileNames?.length) {
    console.error("You need to run this script as `node parsers/language 'file1.java, file2.py, ...'`");
    return;
  }

  console.log(`\t Parsing ${fileNames.length} files...`);

  const output: {
    [fileName: string]: Parser.PostProcessedResults;
  } = {};
  for (const fileName of fileNames) {
    const LangParser = getParserForLanguage(fileName);
    if (!LangParser) {
      console.log(`Skipping unintelligible file: ${fileName}`);
      continue;
    }

    const parser = new LangParser();

    output[fileName] = await parser.parse(fileName);
  }

  await fs.writeFile(outputPath, JSON.stringify(output, undefined, 2));
  // console.log("Saved to", outputPath);
}

main();
