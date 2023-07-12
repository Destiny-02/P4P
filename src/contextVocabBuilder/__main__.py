from os import path
import csv
from ..helper.conversion import (
    stemTerm,
    setToTxtNoDuplicates,
    stringsToProcessable,
    setIntersectionStemmed,
    setToStemmedSet,
    termsListToStemmedFrequencyDict,
    stringToTermsList,
)
from ..helper.io import setToSheet
from ..pathConstants import VOCAB_FOLDER
from ..vocabularlyBuilder.__main__ import getVocabularies

TO_DETERMINE_FILE = "to-determine.csv"


def saveTermsToBeDetermined(pathToDomainDescription, domainFolderName):
    """
    Saves the terms to be determined as from the domain or not from the descriptor to a spreadsheet
    """
    # Get the terms in the domain description
    with open(pathToDomainDescription, "r", encoding="utf8") as file:
        domainDescription = file.read()

    # Split the domain description into terms by whitespace
    terms = domainDescription.split()

    # Get the answers to the context terms we have already seen and categorised
    (
        _,
        _,
        _,
        combinedTerms,
        contextTermsAnswers,
        _,
        _,
        combinedTermsAnswers,
    ) = getVocabularies(domainFolderName, includeAnswers=True)

    # Split the words into standardised terms suitable for a human to manually look through
    terms = stringsToProcessable(set(terms), combinedTerms)

    # Do the same but as if the seen terms included the answers
    terms = stringsToProcessable(terms, combinedTermsAnswers.union(combinedTerms))

    # The terms we can automatically determine are
    # the terms that are in context-answer.txt and in the domain description
    # Write the terms to context.txt
    knownTerms = setIntersectionStemmed(terms, contextTermsAnswers)
    setToTxtNoDuplicates(
        setToStemmedSet(knownTerms),
        getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"),
    )

    # Count the number of times each term was mentioned in the domain description
    frequencyDict = termsListToStemmedFrequencyDict(
        stringToTermsList(domainDescription)
    )

    # Sort the terms by the number of times they were mentioned
    # If the number of times they were mentioned is the same, sort alphabetically
    # to keep it deterministic
    terms = sorted(
        terms,
        key=lambda term: (frequencyDict[stemTerm(term)], term),
        reverse=True,
    )

    # Print the terms and their frequency
    for term in terms:
        print(term, frequencyDict[stemTerm(term)])

    # Write the terms to be determined to a spreadsheet
    setToSheet(terms, getPath(TO_DETERMINE_FILE))


def saveDomainSheetToTxt(domainFolderName):
    """
    Saves the to-determine.csv spreadsheet containing terms that have been categorised as
    domain terms / context schema (c) to the context.txt text file
    """
    dSet = set()

    with open(getPath(TO_DETERMINE_FILE), "r", encoding="utf8") as file:
        reader = csv.reader(file)
        for row in reader:
            word, letter, _ = row
            if letter == "c":
                dSet.add(stemTerm(word))

    setToTxtNoDuplicates(
        dSet, getPath(VOCAB_FOLDER + domainFolderName + "/context.txt")
    )


def getPath(relativePath):
    return path.join(path.dirname(__file__), relativePath)


if __name__ == "__main__":
    # Build the context terms from a piece of descriptive text
    saveTermsToBeDetermined(getPath("../../data/chess/domain-description.txt"), "chess")
    # saveDomainSheetToTxt("chess")
