export namespace Parser {
  export interface Results {
    imports: {
      named: {
        [localVariableName: string]: {
          /** in most languages this is the name of the file or library where the import is from */
          source: string;
          /** some languages let you rename import in the declaration */
          originalName: string;
        };
      };
      /** list of sources where wildcard imports were used, if the language supports it */
      wildcard: string[];
    };
    comments: string[];
    identifiers: {
      type: string;
      name: string;
    }[];
  }
}

export interface Parser {
  language: string;
  parse(fileName: string): Promise<Parser.Results>;
}
