export namespace Parser {
  export interface Results {
    comments: string[];
    identifiers: {
      type: string;
      name: string;
    }[];
  }
}

export interface Parser {
  language: string;
  parse(inputFile: string): Promise<Parser.Results>;
}
