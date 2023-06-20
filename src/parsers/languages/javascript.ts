import { promises as fs } from "node:fs";
import { parse } from "@typescript-eslint/parser";
import { AST_NODE_TYPES, TSESTree } from "@typescript-eslint/types";
import { Parser } from "../Parser";

type ASTRootNode = ReturnType<typeof parse>;

type Context =
  | "ownId"
  | "argument"
  | "property"
  | "enumProperty"
  | "left"
  | "right"
  | undefined;

type NodeWithContext = {
  context: Context;
  node: TSESTree.Node;
};

const AST_TYPES_TO_KEEP = new Set<AST_NODE_TYPES>([
  AST_NODE_TYPES.VariableDeclarator, // variable defintion
  AST_NODE_TYPES.MemberExpression, // object property assignment
  AST_NODE_TYPES.FunctionDeclaration, // function defintion
  AST_NODE_TYPES.ClassDeclaration, // class definition
  AST_NODE_TYPES.TSEnumDeclaration, // enum definition
  AST_NODE_TYPES.TSEnumMember, // enum property definition
  AST_NODE_TYPES.TSTypeAliasDeclaration, // type definition
  AST_NODE_TYPES.TSInterfaceDeclaration, // interface definition
]);

function walkTree(
  node: TSESTree.Node,
  output: Parser.Results,
  parent: TSESTree.Node | undefined,
  context: Context
) {
  const type = node.type;
  // console.log("found", type, node);

  if (type === AST_NODE_TYPES.ImportDeclaration) {
    for (const specifier of node.specifiers) {
      const localVariableName = specifier.local.name;

      if (specifier.type !== AST_NODE_TYPES.ImportNamespaceSpecifier) {
        // skip legacy namespace imports for now

        const originalVariableName =
          specifier.type === AST_NODE_TYPES.ImportDefaultSpecifier
            ? "default"
            : specifier.imported.name;

        output.imports.named[localVariableName] = {
          originalName: originalVariableName,
          source: node.source.value,

          // if the import path does not start with . or @ or ~
          isLibraryFile: /^[^.@~]/.test(node.source.value),
        };
      }
    }
  }

  // check if this node is an identifier, if so, record it
  if (type === AST_NODE_TYPES.Identifier) {
    if (parent && AST_TYPES_TO_KEEP.has(parent.type)) {
      output.identifiers.push({
        type: `${parent?.type}.${context}`,
        name: node.name,
      });
    } else {
      console.warn("Skipping", parent?.type);
    }
  }

  // now explore all the children of this node
  const children: NodeWithContext[] = [];

  if ("body" in node && node.body) {
    const body = Array.isArray(node.body) ? node.body : [node.body];
    children.push(...body.map((n) => ({ context: undefined, node: n })));
  }

  // variable declarators
  if ("declarations" in node && node.declarations) {
    children.push(
      ...node.declarations.map((n) => ({ context: undefined, node: n }))
    );
  }

  // expressions
  if (node.type === AST_NODE_TYPES.ExpressionStatement) {
    children.push({ context: undefined, node: node.expression });
  }

  // assignment - check LHS and RHS sides
  if (node.type === AST_NODE_TYPES.AssignmentExpression) {
    children.push(
      { context: "left", node: node.left },
      { context: "right", node: node.right }
    );
  }

  // assignment - check LHS and RHS sides
  if (node.type === AST_NODE_TYPES.TSEnumDeclaration) {
    children.push(
      ...node.members.map(
        (m): NodeWithContext => ({ context: "enumProperty", node: m })
      )
    );
  }

  // accessing a property of an object - we only care if the parent node is an AssignmentExpression
  if (
    node.type === AST_NODE_TYPES.MemberExpression &&
    parent?.type === AST_NODE_TYPES.AssignmentExpression
  ) {
    children.push({ context: "property", node: node.property });
  }

  // add the method's arguments
  if ("params" in node) {
    const childNodes = Array.isArray(node.params) ? node.params : [node.params];

    // add all childNodes, and add a subType
    for (const child of childNodes) {
      children.push({ context: "argument", node: child });
    }
  }

  // add ownId
  if ("id" in node && node.id) {
    children.push({ context: "ownId", node: node.id });
  }

  for (const child of children) {
    walkTree(child.node, output, node, child.context);
  }
}

function walkProgram(node: ASTRootNode, output: Parser.Results) {
  // if this node has comments, record them
  if (node.comments) {
    const comments = node.comments.map((token) => token.value);
    output.comments.push(...comments);
  }

  walkTree(node, output, undefined, undefined);
}

export class JsTsParser extends Parser {
  constructor() {
    super("javascript");
  }

  override async internalParse(fileName: string) {
    const fileInput = await fs.readFile(fileName, "utf8");
    const AST = parse(fileInput, {
      range: true,
      comment: true,

      sourceType: "module",
      ecmaFeatures: { jsx: true },
      ecmaVersion: "latest",
      lib: ["dom", "esnext"],
    });

    const output = {
      imports: { named: {}, wildcard: [] },
      comments: [],
      identifiers: [],
    };

    walkProgram(AST, output);

    return output;
  }
}
