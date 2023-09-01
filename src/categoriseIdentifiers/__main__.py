from os import path
from argparse import ArgumentParser
from porter2stemmer import Porter2Stemmer

from .typeDefs import (
    CategorisedIdentifier,
    CategorisedWord,
    GlobalValidatorContext,
    Diagonstics,
)
from .validators.index import validators
from .lexicon.addLexiconContext import addLexiconContext
from .lexicon.determineRelevanceToSchema import createDetermineRelevanceToSchema
from .helpers.createDomainSpecificAbbreviationDictionary import (
    createDomainSpecificAbbreviationDictionary,
)
from .helpers.createSynonymMap import createSynonymMap
from .helpers.getPossibleDomains import domainList, getReposForDomain
from ..helper.invokeParser import invokeParserWithMetadata, IdentifersWithContext
from ..helper.io import findFiles, readSheet, saveJsonFile
from ..helper.conversion import preprocessIdentifier

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
    shouldRunExpensiveChecks: bool,
):
    """
    This function processes each identifier, and runs various validators
    on each suspicious word in the identifier.
    """

    stemmer = Porter2Stemmer()

    # TODO: this doesn't work well because designWords is already stemmed, and
    # stemming is a lossy operation that we can't easily revert
    determineRelevanceToDesignSchema = (
        createDetermineRelevanceToSchema(designWords)
        if shouldRunExpensiveChecks
        else None
    )
    determineRelevanceToContextSchema = (
        createDetermineRelevanceToSchema(contextWords)
        if shouldRunExpensiveChecks
        else None
    )

    output: list[CategorisedIdentifier] = []

    # see typedef for info
    context: GlobalValidatorContext = {
        "domainSpecificAbbreviationDictionary": createDomainSpecificAbbreviationDictionary(
            allComments
        ),
        "synonymMap": createSynonymMap(contextWords),
    }

    for [identifier, sourceLocations] in identifiers.items():
        # this function splits the identifer into terms, normalises american
        # english, and removes stop words etc.
        words = preprocessIdentifier(identifier, False)

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
                diagnostic: Diagonstics | None = None
                for validator in validators:
                    diagnostic = validator(
                        word=word,
                        identifier=identifier,
                        context=context,
                        allComments=allComments,
                        sourceLocations=sourceLocations,
                    )

                    if diagnostic:
                        break  # once we have a match, skip the remaining validators

                metadata = addLexiconContext(word, shouldRunExpensiveChecks)

                relevanceToDesign = (
                    determineRelevanceToDesignSchema(word, metadata)
                    if determineRelevanceToDesignSchema
                    else None
                )
                relevanceToContext = (
                    determineRelevanceToContextSchema(word, metadata)
                    if determineRelevanceToContextSchema
                    else None
                )

                components.append(
                    {
                        "word": word,
                        "category": "neither",
                        "metadata": metadata,
                        "relevanceToDesign": relevanceToDesign,
                        "relevanceToContext": relevanceToContext,
                        "diagnostics": [diagnostic] if diagnostic else [],
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


def evaluateIdentifiers(
    categoriseOutput: list[CategorisedIdentifier],
) -> tuple[list[str], list[str]]:
    """
    This function evaluates the results of the categorisation
    and classifies each identifier as meaningful/non-meaningful.
    """
    meaningfulIdentifiers = set()
    nonMeaningfulIdentifiers = set()
    for o in categoriseOutput:
        # count the number of terms that are design or context
        # check if it's >= 50% of the terms
        terms = o["components"]
        count = sum(1 for t in terms if t["category"] in ("design", "context"))
        if count / len(terms) >= 0.5:
            meaningfulIdentifiers.add(o["identifier"])
        else:
            nonMeaningfulIdentifiers.add(o["identifier"])

    # Alphabetical
    meaningfulIdentifiers = list(meaningfulIdentifiers)
    nonMeaningfulIdentifiers = list(nonMeaningfulIdentifiers)
    meaningfulIdentifiers.sort()
    nonMeaningfulIdentifiers.sort()

    return meaningfulIdentifiers, nonMeaningfulIdentifiers


def readFilesAndCategoriseIdentifiers(
    domainName: str,
    repoNames: list[str],
    shouldRunExpensiveChecks: bool,
    classifyIdentifiers: bool,
):
    """
    reads files and invokes categoriseIdentifiers. Separated from
    that function to make it easier to write tests
    """

    vocabFolder = path.join(ALL_VOCAB_FOLDER, domainName)
    contextWords = readSheet(path.join(vocabFolder, "context.txt"))
    designWords = readSheet(path.join(vocabFolder, "design.txt"))

    for repoName in repoNames:
        print(f"Processing {repoName}…")
        repoFolder = path.join(ALL_DATA_FOLDER, domainName, repoName)
        outputFile = path.join(
            TOOL_OUTPUT_FOLDER, domainName, repoName, "validator.json"
        )

        (identifiers, comments) = invokeParserWithMetadata(findFiles(repoFolder))

        output = categoriseIdentifiers(
            identifiers, contextWords, designWords, comments, shouldRunExpensiveChecks
        )

        # save the results to a file
        saveJsonFile(output, outputFile)

        # save meaningful/non-meaningful classifications to two text files
        if classifyIdentifiers:
            meaningfulIdentifiers, nonMeaningfulIdentifiers = evaluateIdentifiers(
                output
            )
            meaningfulIdentifiersFile = outputFile = path.join(
                TOOL_OUTPUT_FOLDER, domainName, repoName, "meaningful.txt"
            )
            nonMeaningfulIdentifiersFile = outputFile = path.join(
                TOOL_OUTPUT_FOLDER, domainName, repoName, "nonmeaningful.txt"
            )
            with open(meaningfulIdentifiersFile, "w", encoding="utf-8") as f:
                f.writelines("\n".join(meaningfulIdentifiers))
            with open(nonMeaningfulIdentifiersFile, "w", encoding="utf-8") as f:
                f.writelines("\n".join(nonMeaningfulIdentifiers))


if __name__ == "__main__":
    parser = ArgumentParser()
    parser.add_argument(
        "domainName",
        choices=domainList,
        help="the name of the domain (i.e. the folder name)",
    )
    parser.add_argument(
        "-s",
        "--slow",
        action="store_true",
        help="run optional computationally expensive checks",
    )
    parser.add_argument(
        "-r",
        "--repo",
        help="optional, the name of the repo. Defaults to all repos in the domain",
    )
    parser.add_argument(
        "--cache",
        help="(debugging only) This will use the cached data from the parser",
    )
    parser.add_argument(
        "--list",
        action="store_true",
        help="This will classify each identifier as meaningful/non-meaningful and"
        + " save the results to two text files",
    )

    args = parser.parse_args()

    # if a specific repository was specified, check only that one.
    # otherwise check all repositories in that domain
    repoNamesToScan = [args.repo] if args.repo else getReposForDomain(args.domainName)

    if repoNamesToScan:
        readFilesAndCategoriseIdentifiers(
            args.domainName, repoNamesToScan, args.slow, args.list
        )
    else:
        print(f"(!) There are no repositories in the folder “{args.domainName}”")
