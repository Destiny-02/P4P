from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet,
	cleanSetOfTerms, removeSeenStemmed, setToTxt
)
from helper.io import findJavaFiles, setToSheet, findRepoPaths, deleteFileIfExists
from helper.splitting import splitIdentifiers
from os import path
import csv

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

def writeResultsToCsv(designCounts, contextCounts, neitherCounts, csvName):
  deleteFileIfExists(csvName)
  with open(csvName, 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Design", "Context", "Neither"])
    for i in range(len(designCounts)):
      writer.writerow([designCounts[i], contextCounts[i], neitherCounts[i]])

def main(pathToData, domainFolderName):
  contextTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/context.txt"))
  designTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/design.txt"))

  # To be used for stats
  allNumDesignTerms = []
  allNumContextTerms = []
  allNumNeitherTerms = []
  allTotalTerms = []

  for repoPath in findRepoPaths(pathToData):
    print(repoPath)
          
    # Parse the identifiers
    (identifiers, comments) = invokeParser(findJavaFiles(repoPath))
          
    # Count the number of design, context and neither terms in the identifiers
    numDesignTerms = 0
    numContextTerms = 0
    numNeitherTerms = 0

    for identifier in identifiers:
      if identifier in designTerms:
        numDesignTerms += 1
      elif identifier in contextTerms:
        numContextTerms += 1
      else:
        numNeitherTerms += 1

    # Add this to the stats sets
    allNumDesignTerms.append(numDesignTerms)
    allNumContextTerms.append(numContextTerms)
    allNumNeitherTerms.append(numNeitherTerms)
    allTotalTerms.append(numDesignTerms + numContextTerms + numNeitherTerms)

  return (allNumDesignTerms, allNumContextTerms, allNumNeitherTerms, allTotalTerms)

if __name__ == "__main__":
  """
  Analyses all the repos for a domain and produces stats on the percentage of terms that are design, context or neither
  """
  (designCounts, contextCounts, neitherCounts, totalCounts) = main(getPath("../data/ugrad-009-01/"), "ugrad-009-01")
  writeResultsToCsv(designCounts, contextCounts, neitherCounts, "ugrad-009-01-stats.csv")