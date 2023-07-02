import { promises as fs } from "node:fs";
import { Parser } from "../Parser";

export class MarkdownParser extends Parser {
  constructor() {
    super("md");
  }

  override async internalParse(fileName: string) {
    const fileInput = await fs.readFile(fileName, "utf8");

    const output: Parser.Results = {
      imports: { named: {}, wildcard: [] },
      comments: [],
      identifiers: [],
    };

    // add the entire file as 1 comment
    output.comments.push(fileInput);

    return output;
  }
}
