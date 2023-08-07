import { AST_NODE_TYPES, type TSESTree } from "@typescript-eslint/types";
import type { Parser } from "../../Parser";

export function getModifiersForJSTS(
  node: TSESTree.Identifier | TSESTree.PrivateIdentifier
): Parser.Modifier[] | undefined {
  const parent = node.parent!;
  const modifiers = new Set<Parser.Modifier>();

  switch (parent.type) {
    case AST_NODE_TYPES.VariableDeclaration: {
      // const delcarations are (shallowly) readonly
      if (parent.kind === "const") modifiers.add("final");
      break;
    }

    case AST_NODE_TYPES.MethodDefinition: {
      // by default JS class method are public, unless they start
      // with a # (PrivateIdentifier)
      const defaultType: TSESTree.Accessibility =
        node.type === AST_NODE_TYPES.PrivateIdentifier ? "private" : "public";

      modifiers.add(parent.accessibility || defaultType);

      // JS methods can be static
      if (parent.static) modifiers.add("static");

      break;
    }

    // we don't consider the readonly keyword, because it only
    // applies to properties of types, or to arrays, and this project
    // is only considering identifiers.

    default:
    // no-op
  }

  return modifiers.size ? [...modifiers] : undefined;
}
