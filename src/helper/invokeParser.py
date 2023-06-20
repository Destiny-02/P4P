import sys
import json
from os import path, system, name

# by default, don't use the cache unless the CLI
# flag --cache is used or if this argument is set to true
DEFAULT_USE_CACHE = "--cache" in sys.argv

PATH_SEPARATOR = "\\" if name == "nt" else "/"


def invokeParser(
    absoluteFileNames: set[str],
    useCache: bool = DEFAULT_USE_CACHE,
) -> tuple[set[str], set[str]]:
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

    # invoke the nodejs script
    if useCache:
        print("Using cached parser results")
    else:
        system(f'cd {scriptPath} && npm run parse -- "{joinedFileNames}"')

    # read the output from the script
    identifiers: set[str] = set()
    comments: set[str] = set()

    # must be same as the output path in parsers/main.ts
    outputFilePath = path.join(path.dirname(__file__), "../../parser-output.json")

    with open(outputFilePath, encoding="utf-8") as jsonFile:
        parserOutputJson = json.load(jsonFile)

        for fileName in parserOutputJson:
            # read identifers for this file
            for identifier in parserOutputJson[fileName]["identifiers"]:
                identifiers.add(identifier["name"])

            # read comments for this file
            for comment in parserOutputJson[fileName]["comments"]:
                comments.add(comment)

    return (identifiers, comments)
