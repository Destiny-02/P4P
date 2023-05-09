const { promises: fs } = require("node:fs");
const parser = require("@babel/eslint-parser");

/**
 * @param {unknown} node
 * @param {import("./Parser").Parser.Results} output
 * @param {unknown | undefined} parent
 */
function walkTree(node, output, parent) {
  const type = node.type;
  // console.log("found", type, node);

  // if this node has comments, record them
  if (node.comments) {
    const comments = node.comments.map((token) => token.value);
    output.comments.push(...comments);
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
const javascriptParser = {
  language: "javascript",
  async parse(fileName) {
    const fileInput = await fs.readFile(fileName, "utf8");
    const AST = parser.parse(fileInput, {
      sourceType: "module",
      requireConfigFile: false,
      babelOptions: {
        parserOpts: {
          plugins: ["jsx"],
        },
      },
    });

    const output = {
      comments: [],
      identifiers: [],
    };

    walkTree(AST, output);

    return output;
  },
};

module.exports = javascriptParser;
