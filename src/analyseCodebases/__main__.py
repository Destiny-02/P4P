from os import path
import csv
from ..helper.invokeParser import invokeParser
from ..helper.conversion import txtToSet, setToStemmedSet, stringsToProcessable
from ..helper.io import findFiles, findRepoPaths, deleteFileIfExists
from ..pathConstants import DATA_FOLDER, VOCAB_FOLDER


def getPath(relativePath):
    return path.join(path.dirname(__file__), relativePath)


def writeResultsToCsv(designCounts, contextCounts, neitherCounts, csvPath):
    deleteFileIfExists(csvPath)
    with open(csvPath, "w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["Design", "Context", "Neither"])
        for i in range(len(designCounts)):
            writer.writerow([designCounts[i], contextCounts[i], neitherCounts[i]])


def splitRepoPathsByNumIdentifiers(repoPaths):
    """
    Returns a tuple of two lists, where the first list contains the repoPaths with less than the median number of identifiers
    and the second list contains the repoPaths with more than the median number of identifiers
    """
    # Get the number of identifiers for each repo
    numIdentifiers = []
    for repoPath in repoPaths:
        (identifiers, _) = invokeParser(findFiles(repoPath))
        numIdentifiers.append(len(identifiers))

    # Get the median number of identifiers
    median = sorted(numIdentifiers)[len(numIdentifiers) // 2]

    # Split the repoPaths based on the median number of identifiers
    lessThanMedian = []
    moreThanMedian = []
    for i in range(len(repoPaths)):
        if numIdentifiers[i] < median:
            lessThanMedian.append(repoPaths[i])
        else:
            moreThanMedian.append(repoPaths[i])

    return (lessThanMedian, moreThanMedian, median)


def checkIdentifierWithVocabularies(identifier, vocab):
    """
    Checks if the identifier contains any terms from the vocabulary
    """
    terms = setToStemmedSet(stringsToProcessable(set([identifier])))

    for term in terms:
        if term in vocab:
            return True
    return False


def dcnCountsIdentifiers(domainFolderName, vocabPath=None):
    if vocabPath is None:
        vocabPath = VOCAB_FOLDER + domainFolderName

    contextTerms = txtToSet(getPath(vocabPath + "/context.txt"))
    designTerms = txtToSet(getPath(vocabPath + "/design.txt"))

    # To be used for stats
    allNumDesignIdentifiers = []
    allNumContextIdentifiers = []
    allNumNeitherIdentifiers = []
    allTotalIdentifiers = []

    for repoPath in findRepoPaths(getPath(DATA_FOLDER + domainFolderName)):
        print(repoPath)

        # Parse the identifiers
        (identifiers, _) = invokeParser(findFiles(repoPath))

        # Count the number of design, context and neither identifiers
        # An identifier qualifies as design or context
        # if it contains at least one design or context term
        numDesignIdentifiers = 0
        numContextIdentifiers = 0
        numNeitherIdentifiers = 0

        for identifier in identifiers:
            # Check for context terms, then design terms. The rest are neither.
            if checkIdentifierWithVocabularies(identifier, contextTerms):
                numContextIdentifiers += 1
            elif checkIdentifierWithVocabularies(identifier, designTerms):
                numDesignIdentifiers += 1
            else:
                numNeitherIdentifiers += 1
                print(identifier)

        # Add this to the stats sets
        allNumDesignIdentifiers.append(numDesignIdentifiers)
        allNumContextIdentifiers.append(numContextIdentifiers)
        allNumNeitherIdentifiers.append(numNeitherIdentifiers)
        allTotalIdentifiers.append(
            numDesignIdentifiers + numContextIdentifiers + numNeitherIdentifiers
        )

    return (
        allNumDesignIdentifiers,
        allNumContextIdentifiers,
        allNumNeitherIdentifiers,
        allTotalIdentifiers,
    )


def dcnCountsTerms(domainFolderName, vocabPath=None):
    if vocabPath is None:
        vocabPath = VOCAB_FOLDER + domainFolderName

    contextTerms = txtToSet(getPath(vocabPath + "/context.txt"))
    designTerms = txtToSet(getPath(vocabPath + "/design.txt"))

    # To be used for stats
    allNumDesignTerms = []
    allNumContextTerms = []
    allNumNeitherTerms = []
    allTotalTerms = []

    for repoPath in findRepoPaths(getPath(DATA_FOLDER + domainFolderName)):
        print(repoPath)

        # Parse the identifiers
        (identifiers, _) = invokeParser(findFiles(repoPath))

        # Find the number of design, context and neither terms
        # An identifier qualifies as design or context
        # if it contains at least one design or context term
        designTermsFromCodebase = set()
        contextTermsFromCodebase = set()
        neitherTermsFromCodebase = set()

        for identifier in identifiers:
            terms = setToStemmedSet(stringsToProcessable(set([identifier])))
            for term in terms:
                if term in contextTerms:
                    contextTermsFromCodebase.add(term)
                elif term in designTerms:
                    designTermsFromCodebase.add(term)
                else:
                    neitherTermsFromCodebase.add(term)

        # Add this to the stats sets
        allNumDesignTerms.append(len(designTermsFromCodebase))
        allNumContextTerms.append(len(contextTermsFromCodebase))
        allNumNeitherTerms.append(len(neitherTermsFromCodebase))
        allTotalTerms.append(
            len(designTermsFromCodebase)
            + len(contextTermsFromCodebase)
            + len(neitherTermsFromCodebase)
        )

    return (allNumDesignTerms, allNumContextTerms, allNumNeitherTerms, allTotalTerms)


def findVocabsForLA(repoPaths, domainFolderName):
    contextTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))
    designTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/design.txt"))

    # To be used for stats
    designVocabs = []
    contextVocabs = []
    neitherVocabs = []

    for repoPath in repoPaths:
        print(repoPath)

        # Parse the identifiers
        (identifiers, _) = invokeParser(findFiles(repoPath))

        # Build up the context, design (and neither) vocabularies from the terms in the identifiers
        design = set()
        context = set()
        neither = set()

        terms = setToStemmedSet(stringsToProcessable(identifiers))

        for term in terms:
            if term in contextTerms:
                context.add(term)
            elif term in designTerms:
                design.add(term)
            else:
                neither.add(term)

        # Add this to the stats sets
        designVocabs.append(design)
        contextVocabs.append(context)
        neitherVocabs.append(neither)

    return (designVocabs, contextVocabs, neitherVocabs)


if __name__ == "__main__":
    """
    Find the number of identifiers that are design, context or neither
    """
    # (designCounts, contextCounts, neitherCounts, totalCounts) = dcnCountsIdentifiers("ugrad-009-01")
    # writeResultsToCsv(designCounts, contextCounts, neitherCounts, getPath("tool-results.csv"))

    """
    Find the number of terms that are design, context or neither
    """
    (designCounts, contextCounts, neitherCounts, totalCounts) = dcnCountsTerms(
        "free-col"
    )
    writeResultsToCsv(
        designCounts, contextCounts, neitherCounts, getPath("tool-results.csv")
    )

    """
    Find the LA (takes a while to run)
    """
    # LA for small and large codebases
    # (smallRepoPaths, largeRepoPaths, threshold) = splitRepoPathsByNumIdentifiers(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")))
    # (smallDesignVocabs, smallContextVocabs, smallNeitherVocabs) = findVocabsForLA(smallRepoPaths, "ugrad-009-01")
    # (largeDesignVocabs, largeContextVocabs, largeNeitherVocabs) = findVocabsForLA(largeRepoPaths, "ugrad-009-01")

    # print("Median number of identifiers: {}".format(threshold))

    # print("Small Design LA: {:.0%}".format(findLA(smallDesignVocabs)))
    # print("Small Context LA: {:.0%}".format(findLA(smallContextVocabs)))
    # print("Small Neither LA: {:.0%}".format(findLA(smallNeitherVocabs)))

    # print("Large Design LA: {:.0%}".format(findLA(largeDesignVocabs)))
    # print("Large Context LA: {:.0%}".format(findLA(largeContextVocabs)))
    # print("Large Neither LA: {:.0%}".format(findLA(largeNeitherVocabs)))

    # # LA for all codebases
    # (designVocabs, contextVocabs, neitherVocabs) = findVocabsForLA(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")), "ugrad-009-01")
    # print("Design LA: {:.0%}".format(findLA(designVocabs)))
    # print("Context LA: {:.0%}".format(findLA(contextVocabs)))
    # print("Neither LA: {:.0%}".format(findLA(neitherVocabs)))
