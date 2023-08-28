from os import path
from ..helper.invokeParser import invokeParser
from ..helper.conversion import (
    txtToSetWithEquivalents,
    convertEquivalents,
    setToStemmedSet,
    stringsToProcessable
)
from ..helper.stats import findLA, getDVInSet, average
from ..helper.io import saveJsonFile, findRepoPaths, findFiles


def main(pathToData):
    domainTerms, equivalents = txtToSetWithEquivalents(getPath("domain-terms.txt"))

    # To be used for averages
    inEither = set()
    inOnlyIdentifiers = set()
    inOnlyComments = set()
    vocabs = list()
    debugData = {}

    for repoPath in findRepoPaths(getPath(pathToData)):
        print("Processing " + repoPath)

        (identifiers, comments) = invokeParser(findFiles(repoPath))

        # Split, clean and stem the identifiers and comments into terms
        identifierTerms = setToStemmedSet(stringsToProcessable(identifiers))
        commentTerms = setToStemmedSet(stringsToProcessable(comments))

        # Convert equivalents
        identifierTerms = convertEquivalents(identifierTerms, equivalents)
        commentTerms = convertEquivalents(commentTerms, equivalents)

        # Count the number of domain terms in the identifiers and comments
        dvIdentifiers = getDVInSet(domainTerms, identifierTerms)
        dvComments = getDVInSet(domainTerms, commentTerms)
        dv = dvIdentifiers.union(dvComments)
        numDT = len(domainTerms)

        # Add to the sets for average stats later
        inEither.add(len(dv)/numDT)
        inOnlyIdentifiers.add(len(dvIdentifiers.difference(dvComments))/numDT)
        inOnlyComments.add(len(dvComments.difference(dvIdentifiers))/numDT)
        vocabs.append(dv)

        debugData[repoPath] = {}
        debugData[repoPath]["processedIdentifiers"] = list(identifierTerms)
        debugData[repoPath]["processedComments"] = list(commentTerms)

    printStats(inEither, inOnlyIdentifiers, inOnlyComments, findLA(vocabs))

    saveJsonFile(debugData, getPath("debug-output.json"))


def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)


def printStats(isEitherSet, isOnlyIdentifiersSet, isOnlyCommentsSet, la):
    # Average % domain terms in source code
    print("1: {:.2%}".format(average(isEitherSet)))

    # Average % domain terms only in identifiers
    print("2.1: {:.2%}".format(average(isOnlyIdentifiersSet)))

    # Average % domain terms only in comments
    print("2.2: {:.2%}".format(average(isOnlyCommentsSet)))

    # LA between any 2 pairs of domain vocabs
    print("3: {:.2%}".format(la))


if __name__ == "__main__":
    main("../../data/ugrad-009-01/")
