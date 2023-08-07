import { AST_NODE_TYPES, TSESTree } from "@typescript-eslint/types";

/**
 * returns true if the given `node` is a deep child
 * of a node with the given `nodeType`.
 */
const isChildOf = <Node extends TSESTree.Node>(
  node: TSESTree.Node,
  nodeType: Node["type"],
  property: keyof Node
) => {
  let next: TSESTree.Node | undefined = node;
  while (next) {
    if (
      next.parent?.type === nodeType &&
      (next.parent as Node | undefined)?.[property] === next
    ) {
      return true;
    }
    next = next.parent;
  }
  return false;
};

// from https://github.com/jsx-eslint/eslint-plugin-react/blame/abb4871/lib/rules/no-array-index-key.js#L63-L74
const iteratorFunctionsToIndexParameterPosition: Record<string, number> = {
  every: 1,
  filter: 1,
  find: 1,
  findIndex: 1,
  flatMap: 1,
  forEach: 1,
  map: 1,
  reduce: 2,
  reduceRight: 2,
  some: 1,
};

export function getIsIndex(
  node: TSESTree.Identifier | TSESTree.PrivateIdentifier
): boolean {
  // private identifiers can never be indexes
  if (node.type === AST_NODE_TYPES.PrivateIdentifier) return false;

  if (
    isChildOf<TSESTree.ForStatement>(node, AST_NODE_TYPES.ForStatement, "init")
  ) {
    return true;
  }

  if (
    node.parent!.type === AST_NODE_TYPES.ArrowFunctionExpression ||
    node.parent!.type === AST_NODE_TYPES.FunctionExpression ||
    node.parent!.type === AST_NODE_TYPES.FunctionDeclaration
  ) {
    const callee = (<TSESTree.MemberExpression>(
      (<TSESTree.CallExpression>node.parent!.parent!).callee
    )).property;
    const calleeName = "name" in callee ? callee.name : undefined;

    const methodIndex = node.parent?.params.indexOf(node);

    const isIndexArgument =
      !!calleeName &&
      iteratorFunctionsToIndexParameterPosition[calleeName] === methodIndex;

    if (isIndexArgument) return true;
  }

  return false;
}
