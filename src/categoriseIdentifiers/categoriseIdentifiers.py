from os import path
import sys
from porter2stemmer import Porter2Stemmer

# To fix import errors
sys.path.insert(0, path.abspath(path.join(path.dirname(__file__), "..")))

from typeDefs import CategorisedIdentifier, CategorisedWord, GlobalValidatorContext
from validators.index import validators
from lexicon.addLexiconContext import addLexiconContext
from lexicon.determineRelevanceToSchema import createDetermineRelevanceToSchema
from helpers.createDomainSpecificAbbreviationDictionary import (
    createDomainSpecificAbbreviationDictionary,
)
from helper.invokeParser import invokeParserWithMetadata, IdentifersWithContext
from helper.io import findJavaFiles, readSheet, saveJsonFile
from helper.conversion import preprocessIdentifier

ALL_DATA_FOLDER = path.join(path.dirname(__file__), "../../data")
ALL_VOCAB_FOLDER = path.join(path.dirname(__file__), "../vocabularies")
# save output to the categoriseIdentifiers folder
# all the tools should write to their own folder
# the results/ folder is for finalised and organised results so tools should not write to it
TOOL_OUTPUT_FOLDER = path.join(path.dirname(__file__), "../../output")


def categoriseIdentifiers(
    identifiers: IdentifersWithContext,
    contextWords: set[str],
    designWords: set[str],
    allComments: set[str],
):
    """
    This function processes each identifier, and runs various validators
    on each suspicious word in the identifier.
    """

    stemmer = Porter2Stemmer()

    # TODO: this doesn't work well because designWords is already stemmed, and
    # stemming is a lossy operation that we can't easily revert
    determineRelevanceToDesignSchema = createDetermineRelevanceToSchema(designWords)
    determineRelevanceToContextSchema = createDetermineRelevanceToSchema(contextWords)

    output: list[CategorisedIdentifier] = []

    # see typedef for info
    context: GlobalValidatorContext = {
        "domainSpecificAbbreviationDictionary": createDomainSpecificAbbreviationDictionary(
            allComments
        )
    }

    for [identifier, sourceLocations] in identifiers.items():
        # this function splits the identifer into terms, normalises american
        # english, and removes stop words etc.
        words = preprocessIdentifier(identifier)

        # a list of every word in the identifer, with out categorisation
        # and any relevant diagnostics
        components: list[CategorisedWord] = []

        for word in words:
            stemmedWord = stemmer.stem(word)  # type: ignore
            if stemmedWord in designWords:
                components.append({"word": word, "category": "design"})
            elif stemmedWord in contextWords:
                components.append({"word": word, "category": "context"})

            else:
                # it's neither context nor design, so run the word thru each
                # validator until we find a match
                validatorResults = [
                    validator(
                        word=word,
                        identifier=identifier,
                        context=context,
                        allComments=allComments,
                    )
                    for validator in validators
                ]

                # filter out all checks that passed
                issues = [issue for issue in validatorResults if issue]

                metadata = addLexiconContext(word)

                relevanceToDesign = determineRelevanceToDesignSchema(word, metadata)
                relevanceToContext = determineRelevanceToContextSchema(word, metadata)

                components.append(
                    {
                        "word": word,
                        "category": "neither",
                        "metadata": metadata,
                        "relevanceToDesign": relevanceToDesign,
                        "relevanceToContext": relevanceToContext,
                        "diagnostics": issues,
                    }
                )

        output.append(
            {
                "identifier": identifier,
                "components": components,
                "sourceLocations": sourceLocations,
            }
        )

    return output


def readFilesAndCategoriseIdentifiers(repoName: str):
    """
    reads files and invokes categoriseIdentifiers. Separated from
    that function to make it easier to write tests
    """

    repoFolder = path.join(ALL_DATA_FOLDER, repoName)
    vocabFolder = path.join(ALL_VOCAB_FOLDER, repoName, "..")
    outputFile = path.join(TOOL_OUTPUT_FOLDER, repoName, "validator.json")

    (identifiers, comments) = invokeParserWithMetadata(findJavaFiles(repoFolder))
    contextWords = readSheet(path.join(vocabFolder, "context.txt"))
    designWords = readSheet(path.join(vocabFolder, "design.txt"))

    output = categoriseIdentifiers(identifiers, contextWords, designWords, comments)

    # save the results to a file
    saveJsonFile(output, outputFile)


if __name__ == "__main__":
    # use CLI arguments or fallback to #9-1
    readFilesAndCategoriseIdentifiers("ugrad-009-01/design1000")
