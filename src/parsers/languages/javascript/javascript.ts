import { promises as fs } from "node:fs";
import { parse } from "@typescript-eslint/parser";
import { AST_NODE_TYPES, TSESTree } from "@typescript-eslint/types";
import { Parser } from "../../Parser";
import { getTypeDefinitionForJSTS } from "./getTypeDefinitionForJSTS";
import { addParentProperty } from "./addParentPropert";

type ASTRootNode = ReturnType<typeof parse>;

type Context =
  | "ownId"
  | "argument"
  | "property"
  | "export"
  | "enumProperty"
  | "left"
  | "right"
  | undefined;

type NodeWithContext = {
  context: Context;
  node: TSESTree.Node;
};

const AST_TYPES_TO_KEEP = new Set((<const>[
  AST_NODE_TYPES.VariableDeclarator, // variable defintion
  // AST_NODE_TYPES.MemberExpression, // object property assignment - deliberately not included
  AST_NODE_TYPES.AssignmentExpression, // object property assignment
  AST_NODE_TYPES.FunctionDeclaration, // function defintion
  AST_NODE_TYPES.FunctionExpression, // function defintion
  AST_NODE_TYPES.ArrowFunctionExpression, // function defintion
  AST_NODE_TYPES.ClassDeclaration, // class definition
  AST_NODE_TYPES.TSEnumDeclaration, // enum definition
  AST_NODE_TYPES.TSEnumMember, // enum property definition
  AST_NODE_TYPES.TSTypeAliasDeclaration, // type definition
  AST_NODE_TYPES.TSInterfaceDeclaration, // interface definition
  AST_NODE_TYPES.RestElement, // the rest element of a function's arguments
]) satisfies readonly AST_NODE_TYPES[]);

// hack to convert the array above into a string union
export type AstTypesWeUnderstand = typeof AST_TYPES_TO_KEEP extends Set<infer T>
  ? T
  : never;

const shouldKeep = (tokenType: string): tokenType is AstTypesWeUnderstand =>
  AST_TYPES_TO_KEEP.has(<never>tokenType);

function walkTree(
  node: TSESTree.Node,
  output: Parser.Results,
  parent: TSESTree.Node | undefined,
  context: Context,
  fileInput: string
) {
  const type = node.type;

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
  if (
    type === AST_NODE_TYPES.Identifier ||
    type === AST_NODE_TYPES.PrivateIdentifier
  ) {
    const parentType = parent?.type;
    if (parentType && shouldKeep(parentType)) {
      output.identifiers.push({
        type: `${parent?.type}.${context}`,
        name: node.name,
        sourceLocation: node.range,
        typeDefinition: getTypeDefinitionForJSTS(node, fileInput),
      });
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

  // for-index loops
  if (node.type === AST_NODE_TYPES.ForStatement && node.init) {
    // add only the initialiser
    children.push({ context: undefined, node: node.init });
  }

  // for-in and for-of loops
  if (
    node.type === AST_NODE_TYPES.ForInStatement ||
    node.type === AST_NODE_TYPES.ForOfStatement
  ) {
    // add only the left side
    children.push({ context: undefined, node: node.left });
  }

  // multiple variable declarations separated by a comma
  if (node.type === AST_NODE_TYPES.VariableDeclaration) {
    children.push(
      ...node.declarations.map((declaration) => ({
        context: undefined,
        node: declaration,
      }))
    );
  }

  // expressions
  if (node.type === AST_NODE_TYPES.ExpressionStatement) {
    children.push({ context: undefined, node: node.expression });
  }
  if (node.type === AST_NODE_TYPES.CallExpression) {
    children.push(
      { context: undefined, node: node.callee },
      ...node.arguments.map((argument) => ({
        context: undefined,
        node: argument,
      }))
    );
  }
  if (node.type === AST_NODE_TYPES.MemberExpression) {
    children.push({ context: undefined, node: node.object });
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

  if (node.type === AST_NODE_TYPES.ExportNamedDeclaration && node.declaration) {
    children.push({ context: "export", node: node.declaration });
  }

  if (node.type === AST_NODE_TYPES.RestElement) {
    children.push({ context: "argument", node: node.argument });
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
    walkTree(child.node, output, node, child.context, fileInput);
  }
}

function walkProgram(
  node: ASTRootNode,
  output: Parser.Results,
  fileInput: string
) {
  // if this node has comments, record them
  if (node.comments) {
    const comments = node.comments.map((token) => token.value);
    output.comments.push(...comments);
  }

  walkTree(node, output, undefined, undefined, fileInput);
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
      ecmaFeatures: { jsx: !fileName.endsWith(".ts") },
      ecmaVersion: "latest",
      lib: ["dom", "esnext"],
    });

    const output = {
      imports: { named: {}, wildcard: [] },
      comments: [],
      identifiers: [],
    };

    addParentProperty(AST);

    walkProgram(AST, output, fileInput);

    return output;
  }
}
