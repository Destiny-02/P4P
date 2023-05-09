from os import path
from helper.invokeParser import invokeParser
from helper.conversion import (
    txtToSetWithEquivalents,
    convertEquivalents,
    setToStemmedSet,
    extractWordsFromSet,
    cleanSetOfTerms,
)
from helper.stats import findLA, getDVInSet
from helper.io import printStats, saveJsonDebugFile, findRepoPaths, findJavaFiles
from helper.splitting import splitIdentifiers
import time


def main(pathToData):
    domainTerms, equivalents = txtToSetWithEquivalents(pathToData + "domain-terms.txt")

    # To be used for averages
    inEither = set()
    inOnlyIdentifiers = set()
    inOnlyComments = set()
    vocabs = list()
    debugData = {}

    for repoPath in findRepoPaths(pathToData):
        print("Processing " + repoPath)

        (identifiers, comments) = invokeParser(findJavaFiles(repoPath))

        # Split identifiers and comments into words
        identifiers = splitIdentifiers(identifiers)
        comments = extractWordsFromSet(comments)

        # "Clean" the identifiers and comments
        identifiers = cleanSetOfTerms(identifiers)
        comments = cleanSetOfTerms(comments)
        # Note: all comments and terms should be single, lowercase, stripped, non-empty words at this point

        # Stem identifiers and comments
        identifiers = setToStemmedSet(identifiers)
        comments = setToStemmedSet(comments)

        # Convert equivalents
        identifiers = convertEquivalents(identifiers, equivalents)
        comments = convertEquivalents(comments, equivalents)

        # Count the number of domain terms in the identifiers and comments
        dvIdentifiers = getDVInSet(domainTerms, identifiers)
        dvComments = getDVInSet(domainTerms, comments)
        dv = dvIdentifiers.union(dvComments)
        numDT = len(domainTerms)

        # Add to the sets for average stats later
        inEither.add(len(dv)/numDT)
        inOnlyIdentifiers.add(len(dvIdentifiers.difference(dvComments))/numDT)
        inOnlyComments.add(len(dvComments.difference(dvIdentifiers))/numDT)
        vocabs.append(dv)

        debugData[repoPath] = {}
        debugData[repoPath]["processedIdentifiers"] = list(identifiers)
        debugData[repoPath]["processedComments"] = list(comments)

    printStats(inEither, inOnlyIdentifiers, inOnlyComments, findLA(vocabs))

    saveJsonDebugFile(debugData)


if __name__ == "__main__":
    # TODO: get this from the command line
    folderToParse = path.join(path.dirname(__file__), "../data/ugrad-009-01/")

    startTime = time.time()

    main(folderToParse)

    endTime = time.time()

    timeTaken = round(endTime - startTime)
    print("Time taken: " + str(timeTaken) + " seconds")

    # Use this if we are writing results to the out folder
    # cleanOutFolder('../out/')
