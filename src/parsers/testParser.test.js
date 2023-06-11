import { readdirSync } from "node:fs";
import { join } from "node:path";
import { getParserForLanguage } from "./getParserForLanguage";

const testCaseFolderPath = join(__dirname, "tests");
const testCaseFileNames = readdirSync(testCaseFolderPath);

// this file automatically creates test cases for every file in the "tests"
// directory. We shouldn't need to edit this file.

describe("test case files", () => {
  for (const fileName of testCaseFileNames) {
    const Parser = getParserForLanguage(fileName);
    if (Parser) {
      // generate a test case for each file

      const parser = new Parser();

      it(`parses ${fileName} (${parser.language})`, async () => {
        const pathToFile = join(testCaseFolderPath, fileName);
        expect(await parser.parse(pathToFile)).toMatchSnapshot();
      });
    } else {
      it.todo(`parses ${fileName} (no parser available)`);
    }
  }
});
