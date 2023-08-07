import { AST_NODE_TYPES, TSESTree } from "@typescript-eslint/types";
import { Parser } from "../../Parser";
import { getModifiersForJSTS } from "./getModifiersForJSTS";
import { getIsIndex } from "./getIsIndex";

const stringifyTypeAnnotation = (
  node: TSESTree.TSTypeAnnotation | undefined,
  fileInput: string
): string => {
  if (!node) return "any";
  const [start, end] = node.range;
  return fileInput.slice(start + 1, end).trim();
};

export function getTypeDefinitionForJSTS(
  node: TSESTree.Identifier | TSESTree.PrivateIdentifier,
  fileInput: string
): Parser.TypeDefinition | undefined {
  const parent = node.parent!;
  // TS makes this so much easier than java <3
  switch (parent.type) {
    case AST_NODE_TYPES.VariableDeclarator:
    case AST_NODE_TYPES.MemberExpression:
    case AST_NODE_TYPES.AssignmentExpression:
    case AST_NODE_TYPES.ClassDeclaration:
    case AST_NODE_TYPES.TSEnumDeclaration:
    case AST_NODE_TYPES.RestElement:
    case AST_NODE_TYPES.ArrowFunctionExpression:
    case AST_NODE_TYPES.FunctionExpression:
    case AST_NODE_TYPES.TSEnumMember: {
      let typeAnnotation =
        "typeAnnotation" in node ? node.typeAnnotation : undefined;

      // slightly different for the rest element
      if (parent.type === AST_NODE_TYPES.RestElement) {
        typeAnnotation ||= parent.typeAnnotation;
      }

      return {
        typeName: stringifyTypeAnnotation(typeAnnotation, fileInput),
        modifiers: getModifiersForJSTS(node),
        classification: getIsIndex(node) ? "index" : undefined,
      };
    }

    case AST_NODE_TYPES.FunctionDeclaration: {
      const argumentTypes = parent.params.map((parameter) =>
        "typeAnnotation" in parameter ? parameter.typeAnnotation : undefined
      );

      return {
        typeName: "function",
        returnType: stringifyTypeAnnotation(parent.returnType, fileInput),
        argumentTypes: argumentTypes.map((argumentType) =>
          stringifyTypeAnnotation(argumentType, fileInput)
        ),
        modifiers: getModifiersForJSTS(node),
      };
    }

    // skip type declarations
    case AST_NODE_TYPES.TSTypeAliasDeclaration:
    case AST_NODE_TYPES.TSInterfaceDeclaration: {
      return undefined;
    }

    default: {
      throw new Error(`Unexpected node type: ${parent.type}`);
    }
  }
}
