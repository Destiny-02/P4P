import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from os import path
from helper.conversion import dictToCsv, stringsToProcessable, setToStemmedSet, txtToSet
from helper.io import findJavaFiles, csvToSheet, findRepoPaths
from helper.invokeParser import invokeParser
from pathConstants import DATA_FOLDER, VOCAB_FOLDER

# create a map of words and the number of codebases which use that word.
# if a word appears multiple times in one codebase, it is only counted once,
# since this is a count of codebases, not occurrances.
def main(repoPaths: str, vocabFile: str) -> dict[str, int]:
    output: dict[str, int] = {}

    for repoPath in repoPaths:
        (identifiers, _) = invokeParser(findJavaFiles(repoPath), getPath("parser-output.json"))
        vocabTerms = txtToSet(getPath(VOCAB_FOLDER + vocabFile))
        
        terms = setToStemmedSet(stringsToProcessable(identifiers, set()))

        for v in vocabTerms:
            # initialise
            if v not in output:
                output[v] = 0

            # increment if this codebase uses this term
            if v in terms:
                output[v] += 1

    # sort by count, ascending
    return dict(sorted(output.items(), key=lambda kv: kv[1], reverse=True))

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
    freqDict = main(findRepoPaths((getPath(DATA_FOLDER + "ugrad-009-01"))), "ugrad-009-01/design.txt")
    csvToSheet(dictToCsv(freqDict), getPath("tool-results-design.csv"))

    freqDict = main(findRepoPaths((getPath(DATA_FOLDER + "ugrad-009-01"))), "ugrad-009-01/context.txt")
    csvToSheet(dictToCsv(freqDict), getPath("tool-results-context.csv"))