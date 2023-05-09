// @ts-check
const javaParser = require("./languages/java");
const javascriptParser = require("./languages/javascript");
const pythonParser = require("./languages/python");

/** @param {string} fileName */
const getParserForLanguage = (fileName) =>
  fileName.endsWith(".java")
    ? javaParser
    : fileName.endsWith(".js")
    ? javascriptParser
    : fileName.endsWith(".py")
    ? pythonParser
    : undefined;

module.exports = { getParserForLanguage };
