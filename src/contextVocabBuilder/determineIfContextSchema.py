from os import path
import csv
from math import log10
from porter2stemmer import Porter2Stemmer

softwareCorpusFile = path.join(
    path.dirname(__file__), "../../data/downloaded/frequencies-full.csv"
)
literatureCorpusFile = path.join(
    path.dirname(__file__), "../../data/downloaded/enwiki-2023-04-13.txt"
)

softwareCorpus: dict[str, float] = {}
literatureCorpus: dict[str, float] = {}

stemmer = Porter2Stemmer()


def initialiseEnglishCorpus() -> None:
    """
    Reading stuff takes ages.
    Run this function first, before using `determineIfContextSchema`
    """
    # TODO: this whole function could be run as a preprocessing step
    #       after downloading these corpusses.

    # first read the corpus of terms in software
    print("Reading software corpus...")
    with open(softwareCorpusFile, "r", encoding="utf8") as csvFile:
        maxFrequency: int | None = None
        for row in csv.reader(csvFile):
            term, _frequency = row

            stemmedTerm = stemmer.stem(term).lower()
            frequency = int(_frequency)

            # the first row is the most frequent
            if not maxFrequency:
                maxFrequency = frequency

            percent = frequency / maxFrequency
            softwareCorpus[stemmedTerm] = percent

    # now we read the corpus of terms in literature
    print("Reading literature corpus...")
    with open(literatureCorpusFile, "r", encoding="utf8") as csvFile:
        rows = csv.reader(csvFile, delimiter=" ")

        maxFrequency: int | None = None
        for row in rows:
            term, _frequency = row

            stemmedTerm = stemmer.stem(term).lower()
            frequency = int(_frequency)

            # the first row is the most frequent
            if not maxFrequency:
                maxFrequency = frequency

            percent = frequency / maxFrequency
            literatureCorpus[stemmedTerm] = percent

    print("Done")


def determineIfContextSchema(term: str, frequencyInDocument: float) -> float:
    """
    This function uses several algorithms to determine whether
    a term might be a context-schema term.
    """
    stemmedTerm = stemmer.stem(term).lower()

    frequencyInCodeCorpus = (
        softwareCorpus[stemmedTerm] if stemmedTerm in softwareCorpus else 0
    )
    frequencyInProseCorpus = (
        literatureCorpus[stemmedTerm] if stemmedTerm in literatureCorpus else 0
    )

    # take the max, to avoid rare english words that are common in code, e.g. console (verb)
    frequencyInCorpus = max(frequencyInCodeCorpus, frequencyInProseCorpus)

    # return 0 if the term does not appear in either of the corpusses
    if frequencyInCorpus == 0:
        return 0

    # this is not strictly tf-idf since we don't know the corpus document
    # count, only the corpus frequency.
    tfidf = abs(frequencyInDocument * log10(frequencyInCorpus))

    return tfidf
