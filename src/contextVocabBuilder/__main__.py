from os import path
import argparse
import csv
from ..helper.conversion import (
    stemTerm,
    setToTxtNoDuplicates,
    stringsToProcessable,
    setIntersectionStemmed,
    setIntersectionStemmed2,
    setToStemmedSet,
    termsListToStemmedFrequencyDict,
    stringToTermsList,
    preprocessIdentifier,
    extractCapitalisedWords,
)
from ..categoriseIdentifiers.helpers.getPossibleDomains import domainList
from ..helper.io import writeDictAsCsv
from ..helper.generic import flatten
from ..pathConstants import VOCAB_FOLDER
from ..vocabularlyBuilder.__main__ import getVocabularies
from .computeFrequency import computeFrequency
from .determineIfContextSchema import initialiseEnglishCorpus, determineIfContextSchema
from .types import TermToDetermine

TO_DETERMINE_FILE = "to-determine.csv"


def saveTermsToBeDetermined(
    pathToDomainDescription, domainFolderName, termsCsvPath: str | None = None
):
    """
    Saves the terms to be determined as from the domain or not from the descriptor to a spreadsheet
    """
    # Get the terms in the domain description
    with open(pathToDomainDescription, "r", encoding="utf8") as file:
        domainDescription = file.read()

    # Split the domain description into terms by whitespace
    originalTerms = domainDescription.split()

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
    terms = stringsToProcessable(set(originalTerms), combinedTerms)

    # need to do the same thing to the original list
    # stringsToProcessable only works on sets so using preprocessIdentifier instead,
    # even though these are not identifiers
    processedOriginalTerms = flatten(
        [preprocessIdentifier(term) for term in originalTerms]
    )

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

    # Find terms that are capitalised in the domain description
    capitalisedTerms = extractCapitalisedWords(domainDescription)

    # compute frequencies of each term within the doc
    termFrequencies = computeFrequency(processedOriginalTerms)

    # load frequencies of each term within the entire corpus
    initialiseEnglishCorpus()

    # Only keep the terms that are in the csv file
    # (which are the terms that are used in the codebase)
    if termsCsvPath is not None:
        keepTheseTerms = set()
        with open(termsCsvPath, "r", encoding="utf8") as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                keepTheseTerms.add(row[0])
        terms = setIntersectionStemmed2(terms, keepTheseTerms, False, False)

    # calculate tf-idf for all the terms
    tfidfDict: dict[str, float] = dict()
    for term in terms:
        tfidfDict[term] = determineIfContextSchema(term, termFrequencies.get(term, 0))

    # used for min-max scaling
    # we can't use dict.values() because it uses the original domainDescription,
    # which includes stopwords like "the", which would skew the min-max scaling
    # the next-values are used to avoid 0-scores
    minFrequency = float("inf")
    nextMinFrequency = float("inf")
    maxFrequency = 1
    minTfidf = 1
    maxTfidf = 0
    nextMaxTfidf = 1
    for term in terms:
        freq = frequencyDict.get(stemTerm(term), 1)
        tfidf = tfidfDict.get(term, 0)
        if freq < minFrequency:
            nextMinFrequency = minFrequency
            minFrequency = freq
        if freq > maxFrequency:
            maxFrequency = freq
        if tfidf < minTfidf:
            minTfidf = tfidf
        if tfidf > maxTfidf:
            nextMaxTfidf = maxTfidf
            maxTfidf = tfidf
    if nextMinFrequency == float("inf"):
        nextMinFrequency = minFrequency + 1
    if nextMaxTfidf == 1:
        nextMaxTfidf = maxTfidf * 0.9

    # add extra columns to the output
    termsWithExtraColumns: list[TermToDetermine] = []
    for term in terms:
        freq = frequencyDict.get(stemTerm(term), 1)
        tfidf = tfidfDict.get(term, 0)
        freqScaled = (freq - minFrequency) / (maxFrequency - minFrequency)
        tfidfScaled = (tfidf - minTfidf) / (maxTfidf - minTfidf)

        # avoid 0-scores
        if freqScaled == 0:
            freqScaled = ((freq + nextMinFrequency) / 2 - minFrequency) / (
                maxFrequency - minFrequency
            )
        if tfidfScaled == 1:
            tfidfScaled = ((freq + nextMaxTfidf) / 2 - minFrequency) / (
                maxFrequency - minFrequency
            )

        # calculate scores
        freqScore = freqScaled * 10
        tfidfScore = (1 - tfidfScaled) * 10
        contextLikelihoodScore = freqScaled * (1 - tfidfScaled) * 100
        if stemTerm(term) in capitalisedTerms:
            contextLikelihoodScore += 25

        termsWithExtraColumns.append(
            {
                "term": term,
                "userClassification": "",
                "frequencyInDocument": freq,
                "tfidf": tfidf,
                "freqScore": freqScore,
                "tfidfScore": tfidfScore,
                "contextLikelihoodScore": contextLikelihoodScore,
            }
        )

    # Sort the terms by the context likelihood score (based on tf-idf and frequency in document)
    termsWithExtraColumns = sorted(
        termsWithExtraColumns,
        key=lambda csvRow: csvRow["contextLikelihoodScore"],
        reverse=True,
    )

    # Write the terms to be determined to a spreadsheet
    writeDictAsCsv(termsWithExtraColumns, getPath(TO_DETERMINE_FILE))


def saveDomainSheetToTxt(domainFolderName):
    """
    Saves the to-determine.csv spreadsheet containing terms that have been categorised as
    domain terms / context schema (c) to the context.txt text file
    """
    dSet = set()

    with open(getPath(TO_DETERMINE_FILE), "r", encoding="utf8") as file:
        reader = csv.reader(file)
        for row in reader:
            word = row[0]
            letter = row[1]
            if letter == "c":
                dSet.add(stemTerm(word))

    setToTxtNoDuplicates(
        dSet, getPath(VOCAB_FOLDER + domainFolderName + "/context.txt")
    )


def getPath(relativePath):
    return path.join(path.dirname(__file__), relativePath)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Build the context terms from a piece of descriptive text"
    )
    parser.add_argument("domainName", choices=domainList)

    parser.add_argument(
        "-c",
        "--command",
        required=True,
        choices=["createToDetermine", "readToDetermine"],
    )
    parser.add_argument(
        "-o",
        "--onlyToCategorise",
        action="store_true",
    )

    args = parser.parse_args()

    if args.command == "createToDetermine":
        saveTermsToBeDetermined(
            getPath(f"../../data/{args.domainName}/domain-description.md"),
            args.domainName,
            getPath("../../src/vocabularlyBuilder/to-categorise.csv")
            if args.onlyToCategorise
            else None,
        )
    else:
        # command == readToDetermine
        saveDomainSheetToTxt(args.domainName)
