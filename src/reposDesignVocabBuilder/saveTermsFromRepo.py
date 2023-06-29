import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from os import path
import json
from helper.invokeParser import invokeParser
from helper.conversion import (
    setToStemmedSet,
    stringsToProcessable
)
from helper.io import findJavaFiles, saveJsonFile

DESIGN_TERMS_FILE = "design-terms.json"
MAX_FILES_PER_INVOCATION = 10

def main(repoPath: str):
    # Get the terms from the repo
    # Parsing only needs to be done once for these repos so we don't need to cache the output
    javaFiles = list(findJavaFiles(repoPath))
    identifiers = set()

    # Split the Java files into chunks of maximum size
    # This is to avoid running out of memory when parsing large repos
    for i in range(0, len(javaFiles), MAX_FILES_PER_INVOCATION):
        chunk = javaFiles[i:i+MAX_FILES_PER_INVOCATION]
        (chunkIdentifiers, _) = invokeParser(set(chunk), False)
        identifiers.update(chunkIdentifiers)

    stemmedTerms = setToStemmedSet(stringsToProcessable(identifiers))
    print("Found", len(stemmedTerms), "terms in", repoPath)

    # Get the existing terms and frequencies
    oldCounts: dict[str, int] = {}
    if path.exists(getPath(DESIGN_TERMS_FILE)):
        with open(getPath(DESIGN_TERMS_FILE), 'r', encoding="utf-8") as f:
            oldCounts = json.load(f)
    else:
        print("No existing design terms file found. Using a new one.")

    # Update the terms and frequencies
    for term in stemmedTerms:
        if term in oldCounts:
            oldCounts[term] += 1
        else:
            oldCounts[term] = 1

    # Overwrite the file with the updated dictionary
    saveJsonFile(oldCounts, getPath(DESIGN_TERMS_FILE))

def saveDesignTermsAsVocabFile(minCount):
    """
    Saves design-terms.json as design-terms.txt, but only including terms that
    appear at least minCount times.
    """
    designTerms: dict[str, int] = {}
    with open(getPath(DESIGN_TERMS_FILE), 'r', encoding="utf-8") as f:
        designTerms = json.load(f)
    with open(getPath("design-terms.txt"), 'w', encoding="utf-8") as f:
        for term in designTerms:
            if designTerms[term] >= minCount:
                f.write(term + "\n")

def getPath(relativePath):
    return path.join(path.dirname(__file__), relativePath)


if __name__ == "__main__":
    # main(getPath("../../data/ugrad-009-01/design1000"))
    main(getPath("../../src/donotcommit/java8-tutorial-master"))
    # saveDesignTermsAsVocabFile(5)
