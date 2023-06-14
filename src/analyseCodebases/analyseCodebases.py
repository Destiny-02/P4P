import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet, stemTerm, cleanSetOfTerms
)
from helper.io import findJavaFiles, findRepoPaths, deleteFileIfExists
from helper.splitting import splitIdentifier
from helper.stats import findLA
from os import path
import csv
import re 

VOCAB_FOLDER = "../vocabularies/"
DATA_FOLDER = "../../data/"

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

def writeResultsToCsv(designCounts, contextCounts, neitherCounts, csvPath):
  deleteFileIfExists(csvPath)
  with open(csvPath, 'w', newline='') as file:
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
    (identifiers, _) = invokeParser(findJavaFiles(repoPath), getPath("parser-output.json"))
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
  terms = splitIdentifier(identifier)

  # Make sure terms are lowercase, stripped and non-empty
  terms = list(cleanSetOfTerms(terms))

  for term in terms:
    if checkTermWithVocabularies(term, vocab):
      return True

  return False

def checkTermWithVocabularies(term, vocab):
  """
  Checks if the term is in the vocabulary
  """
  # Remove numbers if the term is not a number e.g. emp1 --> emp
  if not term.isdigit():
    term = re.sub(r'\d+', '', term)

  # As our vocabularies are stemmed, we need to use the stemmed version of the term
  stemmedTerm = stemTerm(term)

  if stemmedTerm in vocab:
    return True

  return False

def main(domainFolderName, vocabPath = None):
  if vocabPath == None:
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
    (identifiers, _) = invokeParser(findJavaFiles(repoPath), getPath("parser-output.json"))
          
    # Count the number of design, context and neither terms in the identifiers
    # An identifier qualifies as design or context if it contains at least one design or context term 
    numDesignTerms = 0
    numContextTerms = 0
    numNeitherTerms = 0

    for identifier in identifiers:
      # Check for context terms, then design terms. The rest are neither. 
      if checkIdentifierWithVocabularies(identifier, contextTerms):
        numContextTerms += 1
      elif checkIdentifierWithVocabularies(identifier, designTerms):
        numDesignTerms += 1
      else:
        numNeitherTerms += 1
        print(identifier)

    # Add this to the stats sets
    allNumDesignTerms.append(numDesignTerms)
    allNumContextTerms.append(numContextTerms)
    allNumNeitherTerms.append(numNeitherTerms)
    allTotalTerms.append(numDesignTerms + numContextTerms + numNeitherTerms)

  return (allNumDesignTerms, allNumContextTerms, allNumNeitherTerms, allTotalTerms)

def findVocabsLA(repoPaths, domainFolderName):
  contextTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))
  designTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/design.txt"))

  # To be used for stats
  designVocabs = []
  contextVocabs = []
  neitherVocabs = []

  for repoPath in repoPaths:
    print(repoPath)
          
    # Parse the identifiers
    (identifiers, _) = invokeParser(findJavaFiles(repoPath), getPath("parser-output.json"))
          
    # Build up the context, design (and neither) vocabularies from the terms in the identifiers
    design = set()
    context = set()
    neither = set()

    for identifier in identifiers:
      # Split the identifier into terms
      # Make sure terms are lowercase, stripped and non-empty
      terms = splitIdentifier(identifier)
      terms = list(cleanSetOfTerms(terms))
      
      for term in terms:
        if checkTermWithVocabularies(term, contextTerms):
          context.add(term)
        elif checkTermWithVocabularies(term, designTerms):
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
  Find the percentage of terms that are design, context or neither
  """
  # (designCounts, contextCounts, neitherCounts, totalCounts) = main("ugrad-009-01")
  # writeResultsToCsv(designCounts, contextCounts, neitherCounts, getPath("ugrad-009-01-stats.csv"))

  """
  Find the LA (takes a while to run)
  """
  # LA for small and large codebases
  # (smallRepoPaths, largeRepoPaths, threshold) = splitRepoPathsByNumIdentifiers(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")))
  # (smallDesignVocabs, smallContextVocabs, smallNeitherVocabs) = findVocabsLA(smallRepoPaths, "ugrad-009-01")
  # (largeDesignVocabs, largeContextVocabs, largeNeitherVocabs) = findVocabsLA(largeRepoPaths, "ugrad-009-01")

  # print("Median number of identifiers: {}".format(threshold))

  # print("Small Design LA: {:.0%}".format(findLA(smallDesignVocabs)))
  # print("Small Context LA: {:.0%}".format(findLA(smallContextVocabs)))
  # print("Small Neither LA: {:.0%}".format(findLA(smallNeitherVocabs)))

  # print("Large Design LA: {:.0%}".format(findLA(largeDesignVocabs)))
  # print("Large Context LA: {:.0%}".format(findLA(largeContextVocabs)))
  # print("Large Neither LA: {:.0%}".format(findLA(largeNeitherVocabs)))

  # # LA for all codebases
  # (designVocabs, contextVocabs, neitherVocabs) = findVocabsLA(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")), "ugrad-009-01")
  # print("Design LA: {:.0%}".format(findLA(designVocabs)))
  # print("Context LA: {:.0%}".format(findLA(contextVocabs)))
  # print("Neither LA: {:.0%}".format(findLA(neitherVocabs)))