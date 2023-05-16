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

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

def writeResultsToCsv(designCounts, contextCounts, neitherCounts, csvName):
  deleteFileIfExists(csvName)
  with open(csvName, 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Design", "Context", "Neither"])
    for i in range(len(designCounts)):
      writer.writerow([designCounts[i], contextCounts[i], neitherCounts[i]])

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

def main(pathToData, pathToVocabularies):
  contextTerms = txtToSet(getPath(pathToVocabularies + "/context.txt"))
  designTerms = txtToSet(getPath(pathToVocabularies + "/design.txt"))

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

def findVocabsLA(pathToData, pathToVocabularies):
  contextTerms = txtToSet(getPath(pathToVocabularies + "/context.txt"))
  designTerms = txtToSet(getPath(pathToVocabularies + "/design.txt"))

  # To be used for stats
  designVocabs = []
  contextVocabs = []
  neitherVocabs = []

  for repoPath in findRepoPaths(pathToData):
    print(repoPath)
          
    # Parse the identifiers
    (identifiers, comments) = invokeParser(findJavaFiles(repoPath))
          
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
  Analyses all the repos for a domain and produces stats on the percentage of terms that are design, context or neither
  """
  (designCounts, contextCounts, neitherCounts, totalCounts) = main(getPath("../data/ugrad-009-01/"), getPath("vocabularies/ugrad-009-01/"))
  writeResultsToCsv(designCounts, contextCounts, neitherCounts, "ugrad-009-01-stats.csv")

  # Uncomment this to find the LA between the repos (it might take a while to run)
  # (designVocabs, contextVocabs, neitherVocabs) = findVocabsLA(getPath("../data/ugrad-009-01/"), getPath("vocabularies/ugrad-009-01/"))
  # print("Design LA: {:.0%}".format(findLA(designVocabs)))
  # print("Context LA: {:.0%}".format(findLA(contextVocabs)))
  # print("Neither LA: {:.0%}".format(findLA(neitherVocabs)))