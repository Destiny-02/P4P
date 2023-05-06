import json
from porter2stemmer import Porter2Stemmer
from splitting import splitIdentifier as s
import os

stemmer = Porter2Stemmer()

"""
Stats functions
"""
def getDVInSet(domainTerms: set, words: set) -> set:
  dv = set()
  for term in domainTerms:
    if term in words:
      dv.add(term)
  return dv

def findLA(domainVocabs):
    n = len(domainVocabs)

    # 1. Find the average number of terms shared between 2 DV pairs
    totalSharedTerms = 0 # the total number of shared terms between all pairs of domain vocabs
    for i in range(n):
      for j in range(n):
        if i < j:
          sharedTerms = len(domainVocabs[i].intersection(domainVocabs[j]))
          totalSharedTerms += sharedTerms

    # 2!(n-2)!/n! = 2/(n(n-1)) 
    # This is the probability of choosing 2 items from a group of n items, regardless of order
    # AKA n(n-1)/2 possible unique pairs
    p = 2/(n*(n-1))

    averageSharedTerms = totalSharedTerms * p
    
    # 2. Find the average number of terms in a DV
    totalNumTerms = 0 # the total number of terms in all domain vocabs
    for i in range(n):
      totalNumTerms += len(domainVocabs[i])
    averageNumTerms = totalNumTerms / n
    
    # Calculate the level of lexical agreement (LA)
    if totalNumTerms == 0:
      return 0
    else:
      return averageSharedTerms / averageNumTerms

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
      # TODO: get a set of the identifiers
      # TODO: get a set of the comments
      identifiers = jsonToSet('../out/java.json')
      comments = {"the", "payroll", "system", "will", "process", "each", "salaried", "employee's", "payroll"}

      identifiers = setToStemmedSet(identifiers)
      comments = setToStemmedSet(comments)

      identifiers = removeEquivalents(identifiers, equivalents)
      comments = removeEquivalents(comments, equivalents)

      dvIdentifiers = getDVInSet(domainTerms, identifiers)
      dvComments = getDVInSet(domainTerms, comments)
      dv = dvIdentifiers.union(dvComments)
      numDT = len(domainTerms)

      # print("Terms in identifiers but not in domainTerms:")
      # print(identifiers - domainTerms) 
      # print("Terms in domainTerms but not in identifiers:")
      # print(domainTerms - identifiers)
      
      # % domain terms in source code
      print("1: {:.2%}".format(len(dv)/numDT))

      # % domain terms in identifiers
      print("2.1: {:.2%}".format(len(dvIdentifiers.difference(dvComments))/numDT))
      # % domain terms in comments
      print("2.2: {:.2%}".format(len(dvComments.difference(dvIdentifiers))/numDT))

# LA between any 2 pairs of domain vocabs
print("3: {:.2%}".format(findLA(domainTerms)))

# Clean up the out folder
outPath = '../out/'
outFiles = os.listdir(outPath)
for filename in outFiles:
    filePath = os.path.join(outPath, filename)
    if os.path.isfile(filePath):
        os.remove(filePath)
