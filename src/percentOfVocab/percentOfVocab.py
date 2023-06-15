import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from os import path
from helper.conversion import dictToCsv, txtToSet
from helper.io import csvToSheet, saveAllRepoTermsToCache, getAllRepoTermsFromCache, findRepoPaths

from pathConstants import DATA_FOLDER, VOCAB_FOLDER, CACHED_TERMS

def main(allTerms: dict[str, list[str]], vocabFile: str) -> dict[str, list]:
    output = {"codebase": [], "vocabType": [], "percent": []}

    for repoPath in allTerms.keys():
        vocabTerms = txtToSet(getPath(VOCAB_FOLDER + vocabFile))
        terms = allTerms[repoPath]

        countSeen = 0
        for v in vocabTerms:
            if v in terms:
                countSeen += 1

        # Save results to output dictionary
        percent = countSeen / len(vocabTerms)
        codebaseName = repoPath.split("/")[-1].split("\\")[-1]
        
        output["codebase"].append(codebaseName)
        output["vocabType"].append(vocabFile.split('/')[-1].split('.')[0])
        output["percent"].append(round(percent * 100, 2))

    return output
                

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
    saveAllRepoTermsToCache(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")), CACHED_TERMS)
    allTerms = getAllRepoTermsFromCache(CACHED_TERMS)
    
    percentDict = main(allTerms, "ugrad-009-01/context.txt")
    csvToSheet(dictToCsv(percentDict), getPath("tool-results.csv"))

    percentDict = main(allTerms, "ugrad-009-01/design.txt")
    csvToSheet(dictToCsv(percentDict), getPath("tool-results-2.csv"))