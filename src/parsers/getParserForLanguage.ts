// @ts-check

import { JavaParser } from "./languages/java";
import { JsTsParser } from "./languages/javascript";
import { PythonParser } from "./languages/python";

export const getParserForLanguage = (fileName: string) =>
  fileName.endsWith(".java")
    ? JavaParser
    : /.[ajt]sx?$/.test(fileName)
    ? JsTsParser
    : fileName.endsWith(".py")
    ? PythonParser
    : undefined;
