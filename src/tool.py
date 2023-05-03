import json
from porter2stemmer import Porter2Stemmer
from splitting import splitIdentifier as s
import os

stemmer = Porter2Stemmer()

"""
Stats functions
"""
def findPercentDTInIdentifiers(domainTerms: set, identifiers: set) -> float:
  count = 0
  numDomainTerms = len(domainTerms)
  for term in domainTerms:
    if term in identifiers:
      count += 1
  return count/numDomainTerms


"""
Conversion functions
"""

# Converts a text file with one term per line to a set
def txtToSet(filename):
    data = set()
    isFirst = True

    with open(filename, 'r', newline='') as txtfile:
      for line in txtfile:
        # Remove the BOM from the first term
        if isFirst:
            line = line.strip('ï»¿')
            isFirst = False

        words = line.lower().split()
        for word in words:
            data.add(word)
    return data

# Converts a json file of the parser output to a set of terms
def jsonToSet(filename: str) -> set:
  data = set()
  with open(filename, encoding="utf-8") as jsonFile:
    parserOutputJson = json.load(jsonFile)

    for item in parserOutputJson['identifiers']:
      words: list[str] = s.splitIdentifier(item['name'])
      for word in words:
        data.add(word)

  return data

# Converts a txt file with one term per line to a set of terms and a dict of equivalents
# Some lines will have two words, separated by a space
# The first word is a variation of a domain term
# The second word is the domain term that it should be converted to
def txtToSetWithEquivalents(filename):
  data = set()
  equivalents = dict()
  isFirst = True

  with open(filename, 'r', newline='') as txtfile:
    for line in txtfile:
      # Remove the BOM from the first term
      if isFirst:
          line = line.strip('ï»¿')
          isFirst = False

      words = line.lower().split()
      if len(words) == 1:
        data.add(words[0])
      elif len(words) == 2:
        equivalents[words[0]] = words[1]
      else:
        print("Error: line has more than two words")
        print(line)

  return data, equivalents

def removeEquivalents(data: set, equivalents: dict) -> set:
  newData = set()
  for word in data:
    if word in equivalents:
      newData.add(equivalents[word])
    else:
      newData.add(word)
  return newData

# Converts a set of terms to a set of stemmed terms
def setToStemmedSet(data: set) -> set:
  stemmedData = set()
  for word in data:
    stemmedData.add(stemmer.stem(word))
  return stemmedData

"""
Main
"""
# TODO: get this from the command line
pathToData = '../data/ugrad-009-01/'

domainTerms, equivalents = txtToSetWithEquivalents(pathToData + 'domain_terms.txt')

# Loop through all the codebases
for folderName in os.listdir(pathToData):
    if os.path.isdir(os.path.join(pathToData, folderName)):
      repoPath = os.path.join(pathToData, folderName)
      print("Processing " + repoPath)
      
      # TODO: parse the codebase in this folder
      # TODO: use the parser output to get the identifiers
      identifiers = jsonToSet('../out/java.json')

      identifiers = setToStemmedSet(identifiers)
      identifiers = removeEquivalents(identifiers, equivalents)

      # print("Terms in identifiers but not in domainTerms:")
      # print(identifiers - domainTerms) 
      # print("Terms in domainTerms but not in identifiers:")
      # print(domainTerms - identifiers)
      
      print("1: {:.2%}".format(findPercentDTInIdentifiers(domainTerms, identifiers)))
      # TODO: answer 2: 

# TODO: answer 3:

# Clean up the out folder
outPath = '../out/'
outFiles = os.listdir(outPath)
for filename in outFiles:
    filePath = os.path.join(outPath, filename)
    if os.path.isfile(filePath):
        os.remove(filePath)
