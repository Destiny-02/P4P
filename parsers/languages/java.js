const parser = require("java-parser");

/**
 * @param {import("java-parser").CstNode} node
 * @param {string} fallbackType
 * @param {import("./Parser").Parser.Results} output
 * @param {import("java-parser").CstNode | undefined} parent
 */
function walkTree(node, fallbackType, output, parent) {
  const type = node.name || fallbackType;
  // console.log("found", type);

  // if this node has leading comments, record them
  if (node.leadingComments) {
    const comments = node.leadingComments.map((token) => token.image);
    output.comments.push(...comments);
  }

  // same for trailingComments
  if (node.trailingComments) {
    const comments = node.trailingComments.map((token) => token.image);
    output.comments.push(...comments);
  }

  // if this node has an identifier, then record it
  if (node.id) {
    output.identifiers.push({ type: node.type, name: node.id });
  }

  // now explore all the children of this node
  for (const childType in node.children) {
    const children = node.children[childType];

    for (const child of children) {
      walkTree(child, childType, output, node);
    }
  }
}

/** @type {import("./Parser").Parser} */
const javaParser = {
  language: "java",
  async parse(fileInput) {
    const AST = parser.parse(fileInput);

    const output = {
      comments: [],
      identifiers: [],
    };

    walkTree(AST, "root", output);

    return output;
  },
};

module.exports = javaParser;
