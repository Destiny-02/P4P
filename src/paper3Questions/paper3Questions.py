import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from os import path
from helper.invokeParser import invokeParser
from helper.conversion import (
    txtToSetWithEquivalents,
    convertEquivalents,
    setToStemmedSet,
    extractWordsFromSet,
    cleanSetOfTerms,
)
from helper.stats import findLA, getDVInSet, average
from helper.io import saveJsonDebugFile, findRepoPaths, findJavaFiles
from helper.splitting import splitIdentifiers


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

        (identifiers, comments) = invokeParser(findJavaFiles(repoPath), getPath("parser-output.json"))

        # Split identifiers and comments into terms
        identifierTerms = splitIdentifiers(identifiers)
        commentTerms = extractWordsFromSet(comments)

        # "Clean" the terms
        identifierTerms = cleanSetOfTerms(identifierTerms)
        commentTerms = cleanSetOfTerms(commentTerms)
        # Note: all terms should be single, lowercase, stripped, non-empty words at this point

        # Stem terms
        identifierTerms = setToStemmedSet(identifierTerms)
        commentTerms = setToStemmedSet(commentTerms)

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

    saveJsonDebugFile(debugData, getPath("debug-output.json"))

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