const { join } = require("node:path");
const { readdirSync, promises: fs } = require("node:fs");
const { getParserForLanguage } = require("./getParserForLanguage");

const testCaseFolderPath = join(__dirname, "tests");
const testCaseFileNames = readdirSync(testCaseFolderPath);

// this file automatically creates test cases for every file in the "tests"
// directory. We shouldn't need to edit this file.

describe("test case files", () => {
  for (const fileName of testCaseFileNames) {
    const parser = getParserForLanguage(fileName);
    if (parser) {
      // generate a test case for each file

      it(`parses ${fileName} (${parser.language})`, async () => {
        const pathToFile = join(testCaseFolderPath, fileName);
        expect(await parser.parse(pathToFile)).toMatchSnapshot();
      });
    } else {
      it.todo(`parses ${fileName} (no parser available)`);
    }
  }
});
