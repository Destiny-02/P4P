export namespace Parser {
  export interface ImportSource {
    /** in most languages this is the name of the file or library where the import is from */
    source: string;

    /** true if this import is from the language's std lib (as opposed to a local file) */
    isLibraryFile: boolean;
  }
  export interface Results {
    imports: {
      named: {
        [localVariableName: string]: ImportSource & {
          /** some languages let you rename import in the declaration */
          originalName: string;
        };
      };
      /** list of sources where wildcard imports were used, if the language supports it */
      wildcard: ImportSource[];
    };
    comments: string[];
    identifiers: {
      type: string;
      name: string;
    }[];
  }

  /** intersection of the original interface + these extra properties */
  export type PostProcessedResults = Results & {
    identifiers: {
      /**
       * if true, the identifer name is from a library, and the user did
       * not rename it while importing.
       */
      isLibraryName?: boolean;
    }[];
  };
}

export abstract class Parser {
  // eslint-disable-next-line no-useless-constructor -- bug in the rule
  constructor(public language: string) {}

  protected abstract internalParse(fileName: string): Promise<Parser.Results>;

  private postProcessParserResults(
    results: Parser.Results
  ): Parser.PostProcessedResults {
    const output: Parser.PostProcessedResults = results;

    // for each identier, check if it was imported from a builtin library
    // without being renamed
    for (const identifier of output.identifiers) {
      const importMetadata = output.imports.named[identifier.name];

      identifier.isLibraryName =
        !!importMetadata &&
        importMetadata.isLibraryFile &&
        importMetadata.originalName === identifier.name;
    }
    return results;
  }

  public async parse(fileName: string): Promise<Parser.PostProcessedResults> {
    const unprocessedResults = await this.internalParse(fileName);

    const postProcessedResults =
      this.postProcessParserResults(unprocessedResults);

    return postProcessedResults;
  }
}
