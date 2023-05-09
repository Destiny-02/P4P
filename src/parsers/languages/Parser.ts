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
  parse(fileName: string): Promise<Parser.Results>;
}
