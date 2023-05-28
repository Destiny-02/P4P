import json
from helper.conversion import dictToCsv


# create a map of words and the number of codebases which use that word.
# if a word appears multiple times in one codebase, it is only counted once,
# since this is a count of codebases, not occurrances.
def compareVocabFile(filePath: str):
    output: dict[str, int] = {}

    with open("./debug-output.json", encoding="utf-8") as jsonString:
        cleanedParserOutput = json.load(jsonString)

        with open(filePath, encoding="utf-8") as txtFile:
            words = txtFile.read().split()

            # loop thru each codebase
            for codebasePath in cleanedParserOutput:
                identifiers: list[str] = cleanedParserOutput[codebasePath][
                    "processedIdentifiers"
                ]

                for word in words:
                    # initialize
                    if word not in output:
                        output[word] = 0

                    # increment if this codebase uses this term
                    if word in identifiers:
                        output[word] += 1

        # sort by count, accending
        output = dict(sorted(output.items(), key=lambda kv: kv[1], reverse=True))

        # convert each value from a count to a percentage
        output = dict(
            map(
                # given (k, v) return (k, ⌊100(v/totalCodebases)⌉)
                lambda kv: (kv[0], round(100 * (kv[1] / len(cleanedParserOutput)))),
                output.items(),
            )
        )

    # given any file, save a file in the same folder with a different file extension
    outputFileName = filePath.replace(".txt", "-count.csv")

    with open(outputFileName, "w", encoding="utf-8") as outputFile:
        outputFile.write(dictToCsv(output))


compareVocabFile("./src/vocabularies/final/design.txt")
compareVocabFile("./src/vocabularies/final/context.txt")
