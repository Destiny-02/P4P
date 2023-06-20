// typedefs not available
declare module "filbert" {
  // eslint-disable-next-line unicorn/no-static-only-class -- this is how the library is designed
  export default class Fibert {
    static parse(fileName: string, options: unknown): ASTNode;
  }

  // this is far from correct, it's just to give us a rough idea
  export interface ASTNode {
    type: string;
    name: string;
    id?: ASTNode;
    body?: ASTNode | ASTNode[];
    expression?: ASTNode;
    value?: string;
    declarations?: ASTNode[];
    left?: ASTNode;
    right?: ASTNode;
    property?: ASTNode;
    params?: ASTNode | ASTNode[];
  }
}
