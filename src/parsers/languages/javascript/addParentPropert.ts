import type { TSESTree } from "@typescript-eslint/types";
// eslint-disable-next-line import/no-extraneous-dependencies -- transient dep
import { simpleTraverse } from "@typescript-eslint/typescript-estree";

export function addParentProperty(AST: TSESTree.Node) {
  simpleTraverse(AST, {
    enter(node, parent) {
      node.parent = parent;
    },
  });
}
