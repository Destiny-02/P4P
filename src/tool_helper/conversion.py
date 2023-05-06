from splitting import splitIdentifier as s
import json
from porter2stemmer import Porter2Stemmer

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

# Converts a set of terms to a set of terms with equivalents replaced with the equivalent original domain term
def convertEquivalents(data: set, equivalents: dict) -> set:
  newData = set()
  for word in data:
    if word in equivalents:
      newData.add(equivalents[word])
    else:
      newData.add(word)
  return newData

# Converts a set of terms to a set of stemmed terms
def setToStemmedSet(data: set) -> set:
  stemmer = Porter2Stemmer()
  stemmedData = set()
  for word in data:
    stemmedData.add(stemmer.stem(word))
  return stemmedData