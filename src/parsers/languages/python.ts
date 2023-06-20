import { promises as fs } from "node:fs";
import { execSync } from "node:child_process";
import filbert, { ASTNode } from "filbert";
import { Parser } from "../Parser";

const AST_TYPES_TO_KEEP = new Set([
  "VariableDeclarator", // variable defintion
  "FunctionDeclaration", // function defintion
  "ClassDeclaration", // class definition
  "MemberExpression", // class method defintion
]);

// filbert injects fake arguments to make the grammar conform with estree
const BLOCK_LISTED_ARGS = new Set([
  "__hasParams0",
  "__params0",
  "__realArgCount0",
]);

function walkTree(
  node: ASTNode,
  output: Parser.Results,
  parent: ASTNode | undefined,
  subType: string | undefined
) {
  const type = node.type;
  // console.log("found", type);

  // check if this node is a triple-quote string, if so, record it as a comment
  // TODO: this needs refinement
  if (type === "ExpressionStatement") {
    output.comments.push(node.expression!.value!);
  }

  // check if this node is an identifier, if so, record it
  if (type === "Identifier") {
    if (
      parent &&
      AST_TYPES_TO_KEEP.has(parent.type) &&
      !BLOCK_LISTED_ARGS.has(node.name)
    ) {
      output.identifiers.push({
        type: `${parent?.type}.${subType}`,
        name: node.name,
      });
    } else {
      console.log("Skipping", parent?.type);
    }
  }

  // now explore all the children of this node
  const children: { node: ASTNode; subType: string }[] = [];

  if (node.body) {
    children.push(
      ...(Array.isArray(node.body) ? node.body : [node.body]).map((n) => ({
        node: n,
        subType: "child",
      }))
    );
  }

  // variable declaration
  if (node.declarations) {
    children.push(
      ...node.declarations.map((n) => ({ node: n, subType: "declaration" }))
    );
  }

  // any expression (not necessarily assignment)
  if (node.expression) {
    children.push({ node: node.expression, subType: "expression" });
  }

  // LHS/RHS of an expression
  if (node.left) children.push({ node: node.left, subType: "left" });
  if (node.right) children.push({ node: node.right, subType: "right" });

  // property of the LHS/RHS of an expression
  if (node.property) {
    children.push({ node: node.property, subType: "property" });
  }

  // add the method's arguments
  if (node.params) {
    const childNodes = Array.isArray(node.params) ? node.params : [node.params];

    // add all childNodes, and add a subType
    for (const child of childNodes) {
      children.push({ node: child, subType: "argument" });
    }
  }

  // add ownId
  if (node.id) {
    children.push({ node: node.id, subType: "ownId" });
  }

  for (const child of children) {
    walkTree(child.node, output, node, child.subType);
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

    walkTree(AST, output, undefined, undefined);

    // now we need to invoke some python code to parse the inline comments
    const inlineComments = execSync(
      `python languages/pythonComments.py "${fileName}"`
    )
      .toString()
      .replaceAll("\r\n", "\n") // CRLF to LF
      .split("\n")
      .filter((line) => line !== ""); // remove blank lines

    output.comments.push(...inlineComments);

    return output;
  }
}
