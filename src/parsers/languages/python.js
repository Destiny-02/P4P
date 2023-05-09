const { promises: fs } = require("node:fs");
const { execSync } = require("node:child_process");
const filbert = require("filbert");

/**
 * @param {unknown} node
 * @param {import("./Parser").Parser.Results} output
 * @param {unknown | undefined} parent
 */
function walkTree(node, output, parent) {
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
      type: parent?.type + "." + node.subType,
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
    for (const node of childNodes) {
      node.subType = "argument";
      children.push(node);
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

/** @type {import("./Parser").Parser} */
const pythonParser = {
  language: "python",
  async parse(fileName) {
    const fileInput = await fs.readFile(fileName, "utf8");
    const AST = filbert.parse(fileInput, {
      locations: true,
      ranges: true,
    });

    const output = {
      comments: [],
      identifiers: [],
    };

    walkTree(AST, output);

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
  },
};

module.exports = pythonParser;
