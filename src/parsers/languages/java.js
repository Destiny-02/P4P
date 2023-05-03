const parser = require("java-parser");

/**
 * @param {import("java-parser").CstNode} node
 * @param {string} fallbackType
 * @param {import("./Parser").Parser.Results} output
 * @param {string[] | undefined} parent
 */
function walkTree(node, fallbackType, output, parents = []) {
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
  if (type === "Identifier") {
    output.identifiers.push({
      type: parents.at(-2), // use the second last AST node as its type
      name: node.image,
    });
  }

  // now explore all the children of this node
  for (const childType in node.children) {
    const children = node.children[childType];

    for (const child of children) {
      // walk thru this child, and keep track of how far down the tree
      // we are by appending to the parents[] array.
      walkTree(child, childType, output, [...parents, childType]);
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
