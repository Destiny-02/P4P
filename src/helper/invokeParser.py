import sys
import json
from os import path, system, name
from .parserTypes import ParsedEntityContext

# by default, don't use the cache unless the CLI
# flag --cache is used or if this argument is set to true
DEFAULT_USE_CACHE = "--cache" in sys.argv

PATH_SEPARATOR = "\\" if name == "nt" else "/"

# type definition
IdentifersWithContext = dict[str, list[ParsedEntityContext]]


def invokeParserWithMetadata(
    absoluteFileNames: set[str],
    useCache: bool = DEFAULT_USE_CACHE,
) -> tuple[IdentifersWithContext, set[str]]:
    """
    invokes the NodeJS script, parses its output, and returns
    the result of all files merged together

    returns `(identifiers: set, comments: set)`
    """

    scriptPath = path.join(path.dirname(__file__), "..")

    cleanedPaths = [
        path.normpath(p).replace("/", PATH_SEPARATOR) for p in absoluteFileNames
    ]

    joinedFileNames = " 📚 ".join(cleanedPaths)

    # temp hack to bypass CLI command max length for huge repositories
    inputFilePath = path.join(path.dirname(__file__), "../../parser-input.txt")
    with open(inputFilePath, "w", encoding="utf8") as inputFile:
        inputFile.write(joinedFileNames)

    # invoke the nodejs script
    if useCache:
        print("Using cached parser results")
    else:
        system(f'cd {scriptPath} && npm run parse -- "USE_INPUT_FILE"')

    # read the output from the script
    identifiers: IdentifersWithContext = {}
    comments: set[str] = set()

    # must be same as the output path in parsers/main.ts
    outputFilePath = path.join(path.dirname(__file__), "../../parser-output.json")

    with open(outputFilePath, encoding="utf-8") as jsonFile:
        parserOutputJson = json.load(jsonFile)

        for fileName in parserOutputJson:
            # read identifers for this file
            for identifier in parserOutputJson[fileName]["identifiers"]:
                context: ParsedEntityContext = {
                    "fileName": fileName,
                    "startOffset": identifier["sourceLocation"][0],
                    "endOffset": identifier["sourceLocation"][1],
                }

                if identifier["name"] not in identifiers:
                    # first time that we've seen this identifier
                    identifiers[identifier["name"]] = [context]
                else:
                    # we've seen this identifier before
                    identifiers[identifier["name"]].append(context)

            # read comments for this file
            for comment in parserOutputJson[fileName]["comments"]:
                comments.add(comment)

    return (identifiers, comments)


def invokeParser(
    absoluteFileNames: set[str],
    useCache: bool = DEFAULT_USE_CACHE,
) -> tuple[set[str], set[str]]:
    """LEGACY - prefer invokeParserWithMetadata for new code"""
    (identifiers, comments) = invokeParserWithMetadata(absoluteFileNames, useCache)

    return (set(identifiers.keys()), comments)
