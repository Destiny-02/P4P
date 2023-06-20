from os import path
import sys
from porter2stemmer import Porter2Stemmer
from typeDefs import CategorisedIdentifier, CategorisedWord
from validators.index import validators


# To fix import errors
sys.path.insert(0, path.abspath(path.join(path.dirname(__file__), "..")))

from helper.invokeParser import invokeParser
from helper.io import findJavaFiles, readSheet, saveJsonFile
from helper.conversion import stringsToProcessable

#
# run with:
# python src/categoriseIdentifiers/categoriseIdentifiers.py --cache "ugrad-009-01/design1000"
#

ALL_DATA_FOLDER = path.join(path.dirname(__file__), "../../data")
ALL_VOCAB_FOLDER = path.join(path.dirname(__file__), "../vocabularies")
ALL_RESULTS_FOLDER = path.join(path.dirname(__file__), "../../results")


def categoriseIdentifiers(repoName: str):
    """
    FIXME: does stuff
    """

    repoFolder = path.join(ALL_DATA_FOLDER, repoName)
    vocabFolder = path.join(ALL_VOCAB_FOLDER, repoName, "..")
    outputFile = path.join(ALL_RESULTS_FOLDER, repoName) + ".json"

    (identifiers, _) = invokeParser(findJavaFiles(repoFolder))
    contextWords = readSheet(path.join(vocabFolder, "context.txt"))
    designWords = readSheet(path.join(vocabFolder, "context.txt"))

    stemmer = Porter2Stemmer()

    output: list[CategorisedIdentifier] = []

    for identifier in identifiers:
        # this function splits the identifer into terms, normalises american
        # english, and removes stop words etc.
        words = stringsToProcessable(set([identifier]))

        # a list of every word in the identifer, with out categorisation
        # and any relevant diagnostics
        components: list[CategorisedWord] = []

        for word in words:
            stemmedWord = stemmer.stem(word)
            if stemmedWord in designWords:
                components.append({"word": word, "category": "design"})
            elif stemmedWord in contextWords:
                components.append({"word": word, "category": "context"})

            else:
                # it's neither context nor design, so run the word thru each
                # validator until we find a match
                validatorResults = [
                    validator(word, identifier) for validator in validators
                ]

                # filter out all checks that passed
                issues = [issue for issue in validatorResults if issue]

                components.append(
                    {"word": word, "category": "neither", "diagnostics": issues}
                )

        output.append(
            {
                "identifier": identifier,
                "components": components,
                # TODO: include parser location token for IDE suggestions
                "loc": "notImplemented.java:45:12",
            }
        )

    # save the results to a file
    saveJsonFile(output, outputFile)


if __name__ == "__main__":
    # use CLI arguments or fallback to #9-1
    categoriseIdentifiers("ugrad-009-01/design1000")
