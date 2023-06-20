import json
from os import path, system

def invokeParser(absoluteFileNames: set[str], outputFilePath: str = "parser-output.json") -> tuple[set, set]:
    """
    invokes the NodeJS script, parses its output, and returns
    the result of all files merged together

    returns `(identifiers: set, comments: set)`
    """

    scriptPath = path.join(path.dirname(__file__), "..")

    cleanedPaths = [path.normpath(p).replace("/", "\\") for p in absoluteFileNames]

    joinedFileNames = " 📚 ".join(cleanedPaths)

    # invoke the nodejs script
    system(f'cd {scriptPath} && npm run parse -- "{joinedFileNames}"')

    # read the output from the script
    identifiers = set()
    comments = set()

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
