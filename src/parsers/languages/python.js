import { promises as fs } from "node:fs";
import { execSync } from "node:child_process";
import filbert from "filbert";
import { Parser } from "../Parser";

function walkTree(node: any, output: Parser.Results, parent: any | undefined) {
  const type = node.type;
  // console.log("found", type);

  // check if this node is a triple-quote string, if so, record it as a comment
  // TODO: this needs refinement
  if (type === "ExpressionStatement") {
    output.comments.push(node.expression.value);
  }

  // check if this node is an identifier, if so, record it
  if (type === "Identifier") {
    output.identifiers.push({
      type: `${parent?.type}.${node.subType}`,
      name: node.name,
    });
  }

  // now explore all the children of this node
  const children = [];

  if (node.body) {
    children.push(...(Array.isArray(node.body) ? node.body : [node.body]));
  }

  // add the method's arguments
  if (node.params) {
    const childNodes = Array.isArray(node.params) ? node.params : [node.params];

    // add all childNodes, and add a subType
    for (const child of childNodes) {
      child.subType = "argument";
      children.push(child);
    }
  }

  // add ownId
  if (node.id) {
    node.id.subType = "ownId";
    children.push(node.id);
  }

  for (const child of children) {
    walkTree(child, output, node);
  }
}

export class PythonParser extends Parser {
  constructor() {
    super("python");
  }

  override async internalParse(fileName: string) {
    const fileInput = await fs.readFile(fileName, "utf8");
    const AST = filbert.parse(fileInput, {
      locations: true,
      ranges: true,
    });

    const output: Parser.Results = {
      imports: { named: {}, wildcard: [] },
      comments: [],
      identifiers: [],
    };

    walkTree(AST, output, undefined);

    // now we need to invoke some python code to parse the inline comments
    const inlineComments = execSync(
      `python languages/pythonComments.py "${fileName}"`
    )
      .toString()
      .replace(/\r\n/g, "\n") // CRLF to LF
      .split("\n")
      .filter((line) => line !== ""); // remove blank lines

    output.comments.push(...inlineComments);

    return output;
  }
}
