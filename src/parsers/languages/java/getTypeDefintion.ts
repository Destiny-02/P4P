import type { IToken, CstElement, CstChildrenDictionary } from "java-parser";
import type { Parser } from "../../Parser";
import type { CstNode, CstTypesWeUnderstand } from "./java";

const firstChild = (
  object: CstChildrenDictionary | undefined
): CstElement | undefined => Object.values(object || {})[0]?.[0];

export function getDeepTypeDefinitionForIdentifier(
  innerType: CstElement | undefined
): IToken | undefined {
  let result: IToken | undefined;
  for (let index = 0; index < 10; index += 1) {
    // go maximum 10 nodes deep, if we still find nothing
    // then abort
    if (!innerType) break; // no result
    if ("children" in innerType) {
      // it's a CstNode
      innerType =
        // if it has an idenfier child, prefer that one
        innerType?.children?.Identifier?.[0] ||
        // otherwise, explore the first (and only) child
        firstChild(innerType?.children);
    } else {
      // it's an IToken
      const isIdentifier = "image" in innerType;
      if (isIdentifier) {
        result = innerType;
        break;
      }
    }
  }
  return result;
}

const getTypeNodeForSpreadArguments = (token: CstNode) =>
  token.parent?.children?.unannType?.[0];

const getTypeNodeForMethodArgument = (token: CstNode) =>
  token.parent?.parent?.children.unannType?.[0];

const getTypeNodeForInterfaceMember = (token: CstNode) =>
  token.parent?.parent?.parent?.parent?.children?.unannType?.[0];

const getTypeNodeForVariableWithUserDefinedType = (token: CstNode) =>
  token.parent?.parent?.parent?.parent?.children?.localVariableType?.[0];

const getTypeNodeForVariable = (token: CstNode) =>
  token.parent?.parent?.parent?.parent?.children?.variableDeclaratorList?.[0];

function getModifiers(token: CstNode): Parser.Modifier[] | undefined {
  // explore uṗ the tree, until we find a sibling node which
  // has modifiers.
  let next: CstNode | undefined = token;
  let modifiers: CstNode[] | undefined;
  while (!modifiers && next) {
    modifiers = <CstNode[] | undefined>(
      (next.parent?.children?.fieldModifier ||
        next.parent?.children?.methodModifier)
    );
    next = next.parent;
  }

  if (!modifiers) return undefined;

  const modifierNames = modifiers.map(
    (modifier) =>
      (<IToken>firstChild(modifier.children))!.image as Parser.Modifier
  );
  if (!modifierNames.length) return undefined;

  return modifierNames;
}

export function getTypeDefinition(
  token: CstNode,
  parentType: CstTypesWeUnderstand
): Parser.TypeDefinition | undefined {
  switch (parentType) {
    case "variableArityParameter":
    case "variableDeclaratorId": {
      const innerType =
        getTypeNodeForMethodArgument(token) ||
        getTypeNodeForInterfaceMember(token) ||
        getTypeNodeForSpreadArguments(token) ||
        getTypeNodeForVariableWithUserDefinedType(token) ||
        getTypeNodeForVariable(token); // this one should be the last

      const result = getDeepTypeDefinitionForIdentifier(innerType);

      return {
        typeName: result?.image || "any",
        modifiers: getModifiers(token),
      };
    }
    case "methodDeclarator": {
      const methodArguments = <CstNode[]>(
        (<CstNode>token.parent?.children.formalParameterList?.[0])?.children
          .formalParameter
      );

      const argumentIdentifiers = methodArguments.map(
        (methodArgument) =>
          (<CstNode>firstChild(methodArgument.children))?.children?.unannType[0]
      );

      const argumentTypes = argumentIdentifiers.map(
        getDeepTypeDefinitionForIdentifier
      );

      return {
        typeName: "function",
        argumentTypes: argumentTypes.map((type) => type?.image || "any"),
      };
    }
    // enum members can't have a custom type, they're intrinsic in java
    case "enumConstant": {
      return { typeName: "intrinsic" };
    }

    case "typeIdentifier": {
      const siblingNodes = token.parent?.parent?.parent?.children;
      const firstSibling = <CstNode>firstChild(siblingNodes);

      switch (firstSibling?.name) {
        case "classModifier": {
          return { typeName: "class" };
        }
        case "enumDeclaration": {
          return { typeName: "enum" };
        }
        case "normalInterfaceDeclaration": {
          return { typeName: "interface" };
        }
        default: {
          return undefined;
        }
      }
    }

    default: {
      throw new Error(`Don't understand ${parentType}`);
    }
  }
}
