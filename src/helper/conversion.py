import re

from .splitting import splitIdentifier
import json
from porter2stemmer import Porter2Stemmer

def txtToSet(filename):
  """
  Converts a text file with one term per line to a set
  """
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

def setToTxt(data, filename):
  """
  Appends the terms in a set to a text file, one term per line
  """
  with open(filename, 'a') as txtfile:
    for word in data:
      txtfile.write(word + "\n")

def jsonToSet(filename: str) -> set:
  """
  Converts a json file of the parser output to a set of terms found in identifiers
  """
  data = set()
  with open(filename, encoding="utf-8") as jsonFile:
    parserOutputJson = json.load(jsonFile)

    for item in parserOutputJson['identifiers']:
      words: list[str] = splitIdentifier(item['name'])
      for word in words:
        data.add(word)

  return data

def txtToSetWithEquivalents(filename):
  """
  Converts a txt file with one term per line to a set of terms and a dict of equivalents
  Some lines will have two words, separated by a space
  The first word is a variation of a domain term
  The second word is the domain term that it should be converted to
  """
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

def convertEquivalents(data: set, equivalents: dict) -> set:
  """
  Converts a set of terms to a set of terms with equivalents replaced with the equivalent original domain term
  """
  newData = set()
  for word in data:
    if word in equivalents:
      newData.add(equivalents[word])
    else:
      newData.add(word)
  return newData

def setToStemmedSet(data: set) -> set:
  """
  Converts a set of terms to a set of stemmed terms
  """
  stemmer = Porter2Stemmer()
  stemmedData = set()
  for word in data:
    stemmedData.add(stemmer.stem(word))
  return stemmedData

def stemTerm(term: str) -> str:
  """
  Converts a term to its stemmed version
  """
  stemmer = Porter2Stemmer()
  return stemmer.stem(term)

def extractWords(data: str):
  """
  Converts a string to a list of words
  """
  # Replace all non-letter characters with spaces
  clean_text = re.sub(r'[^a-zA-ZÀ-ÖØ-öø-ÿ\s]', ' ', data)
  
  # Split the cleaned text into a list of words
  words = clean_text.split()
  
  return words

def extractWordsFromSet(data: set):
  """
  Converts a set of strings to a list of words
  """
  words = set()
  for word in data:
    words.update(extractWords(word))
  return words

def cleanSetOfTerms(data: set) -> set:
  """
  Converts a set of terms to a set of lowercase, stripped terms and non-empty terms
  """
  newData = set()
  for term in data:
    cleanedTerm = term.lower().strip()
    if cleanedTerm != "":
      newData.add(cleanedTerm)
  return newData

def removeSeenStemmed(data: set, seen: set) -> set:
  """
  Removes all terms from data that have a stemmed version in seen
  """
  newData = set()
  for term in data:
    if stemTerm(term) not in seen:
      newData.add(term)
  return newData