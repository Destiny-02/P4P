import { promises as fs } from "node:fs";
import { parse, CstNode as OriginalCstNode, IToken } from "java-parser";
import { Parser } from "../../Parser";
import { getTypeDefinition } from "./getTypeDefintion";

export type CstNode = OriginalCstNode & { parent?: CstNode };

// TODO: not sure if this is sufficient
const isLibrary = (importPath: string) =>
  importPath.startsWith("java.") || importPath.startsWith("com.sun.");

const AST_TYPES_TO_KEEP = new Set([
  "typeIdentifier", // class definition
  "methodDeclarator", // method definition
  "variableDeclaratorId", // variable definition
  "variableArityParameter", // spread argument defintion
  "enumConstant", // enum member definition
] as const);

// hack to convert the array above into a string union
export type CstTypesWeUnderstand = typeof AST_TYPES_TO_KEEP extends Set<infer T>
  ? T
  : never;

const shouldKeep = (tokenType: string): tokenType is CstTypesWeUnderstand =>
  AST_TYPES_TO_KEEP.has(tokenType as never);

function walkTree(
  node: CstNode,
  fallbackType: string,
  output: Parser.Results,
  parents: string[] = []
) {
  const type = node.name || fallbackType;

  // if this is an import, record the variable name that was imported
  if (type === "importDeclaration") {
    const importPath: IToken[] =
      // @ts-expect-error -- temp
      node.children.packageOrTypeName[0].children.Identifier;

    const isWildcardImport = node.children.Star?.[0];

    if (isWildcardImport) {
      const importPathString = importPath
        .map((identifier) => identifier.image)
        .join(".");

      output.imports.wildcard.push({
        source: importPathString,
        isLibraryFile: isLibrary(importPathString),
      });
    } else {
      // normal import
      const variableName = importPath.at(-1)!.image;
      const importPathString = importPath
        .slice(0, -1)
        .map((identifier) => identifier.image)
        .join(".");

      output.imports.named[variableName] = {
        originalName: variableName, // java doesn't allow renaming
        source: importPathString,
        isLibraryFile: isLibrary(importPathString),
      };
    }
  }

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
    const parentType = parents.at(-2); // use the second last AST node as its type

    // skip import statements, they're handled separately and it would misleading
    // to include terms like "java" or "util" that purely constitute the import path.
    if (
      parentType &&
      parentType !== "packageOrTypeName" &&
      shouldKeep(parentType)
    ) {
      const token = <IToken>(<unknown>node);
      output.identifiers.push({
        type: parentType,
        name: token.image,
        typeDefinition: getTypeDefinition(node, parentType),
        sourceLocation: [token.startOffset, token.endOffset],
      });
    }
  }

  // now explore all the children of this node
  for (const childType in node.children) {
    const children = node.children[childType];

    for (let child of children) {
      // walk thru this child, and keep track of how far down the tree
      // we are by appending to the parents[] array.

      // @ts-expect-error -- hack
      child = { ...child, parent: node };

      walkTree(<CstNode>child, childType, output, [...parents, childType]);
    }
  }
}

export class JavaParser extends Parser {
  constructor() {
    super("java");
  }

  override async internalParse(fileName: string) {
    const fileInput = await fs.readFile(fileName, "utf8");
    const AST = parse(fileInput);

    const output = {
      imports: { named: {}, wildcard: [] },
      comments: [],
      identifiers: [],
    };

    walkTree(AST, "root", output);

    return output;
  }
}
