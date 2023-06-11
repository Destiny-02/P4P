// @ts-check

import { JavaParser } from "./languages/java";
import { JsTsParser } from "./languages/javascript";
import { PythonParser } from "./languages/python";

export const getParserForLanguage = (fileName: string) =>
  fileName.endsWith(".java")
    ? JavaParser
    : fileName.match(/.(j|t)sx?$/)
    ? JsTsParser
    : fileName.endsWith(".py")
    ? PythonParser
    : undefined;
