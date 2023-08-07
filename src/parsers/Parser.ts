import type { Range } from "@typescript-eslint/types/dist/generated/ast-spec";

export namespace Parser {
  export interface ImportSource {
    /** in most languages this is the name of the file or library where the import is from */
    source: string;

    /** true if this import is from the language's std lib (as opposed to a local file) */
    isLibraryFile: boolean;
  }

  /** not all langauges support all of these modifiers */
  export type Modifier =
    | "public"
    | "private"
    | "protected"
    | "final"
    | "static"
    | "abstract"
    | "transient"
    | "synchronized"
    | "volatile";

  export interface TypeDefinition {
    /**
     * this could be a user-defined name, a built in type
     * or an intrinsic type (we follow the TS convention,
     * so that could be `any`, `unknown`, `never`, or `intrinsic`).
     */
    typeName: string;
    argumentTypes?: string[];
    /** if explicitly marked as readonly */
    modifiers?: Modifier[];
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
      typeDefinition?: TypeDefinition;
      sourceLocation: Range;
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

    // remove windows line-endings
    output.comments = output.comments
      .filter(Boolean)
      .map((comment) => comment.replaceAll("\r\n", "\n"));

    return results;
  }

  public async parse(
    fileName: string
  ): Promise<Parser.PostProcessedResults | undefined> {
    try {
      const unprocessedResults = await this.internalParse(fileName);

      const postProcessedResults =
        this.postProcessParserResults(unprocessedResults);

      return postProcessedResults;
    } catch (ex) {
      console.error(`Failed to parse “${fileName}” using ${this.language}`);
      console.error(ex);
      return undefined;
    }
  }
}
