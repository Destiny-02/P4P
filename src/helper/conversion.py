from .splitting import splitIdentifier, splitIdentifiers
from .english import fixUSSpelling
import json
from porter2stemmer import Porter2Stemmer
import nltk
nltk.download('stopwords')

stopWords = set(nltk.corpus.stopwords.words('english'))

def txtToSet(filename):
  """
  Converts a text file with one term per line to a set
  """
  data = set()
  isFirst = True

  try:
    with open(filename, 'r', newline='') as txtfile:
      for line in txtfile:
        # Remove the BOM from the first term
        if isFirst:
          line = line.strip('ï»¿')
          isFirst = False

        words = line.lower().split()
        for word in words:
          data.add(word)
  except FileNotFoundError:
    return set()

  return data

def setToTxt(data, filename):
  """
  Appends the terms in a set to a text file, one term per line. 
  Note that this will not overwrite the file, but will append to it so new words may be duplicated. 
  """
  with open(filename, 'a') as txtfile:
    for word in data:
      txtfile.write(word + "\n")

def setToTxtNoDuplicates(data, filename):
  """
  Writes the terms in a set to a text file, one term per line. 
  Unlike setToTxt, this will read the file first and remove any duplicates before writing to the file.
  """
  terms = txtToSet(filename)
  terms.update(data)
  terms = sorted(terms) # Sort the terms alphabetically to make it easier to view the diff

  with open(filename, 'w') as txtfile:
    for word in terms:
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

def setIntersectionStemmed(firstSet: set, secondSetStemmed: set) -> set:
  """
  Returns a set of terms that are in both the first and second set
  The first set is not stemmed (normal English words)
  The second set is stemmed
  e.g. {"cats"} - {"cat"} = {}
  """
  firstSetStemmed = setToStemmedSet(firstSet)
  stemmedIntersection = firstSetStemmed.intersection(secondSetStemmed)

  returnSet = set()
  for word in firstSet:
    if stemTerm(word) in stemmedIntersection:
      returnSet.add(word)
  
  return returnSet

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
  clean_text = ''.join(char if char.isalpha() else ' ' for char in data)
  
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
    cleanedTerm = convertToLowercase(term)
    if cleanedTerm != "":
      newData.add(cleanedTerm)
  return newData

def convertToLowercase(term: str) -> str:
  """
  Converts a term to stripped lowercase
  """
  return ''.join(char for char in term if char.isalpha()).lower().strip()

def removeSeenStemmed(data: set, seen: set) -> set:
  """
  Removes all terms from data that have a stemmed version in seen
  data is not already stemmed
  seen is already stemmed
  """
  newData = set()
  newDataStemmed = set() # Used to check if the stemmed version of a term is already in the return set
  for term in data:
    stemmedTerm = stemTerm(term)
    if stemmedTerm not in seen and stemmedTerm not in newDataStemmed:
      newData.add(term)
      newDataStemmed.add(stemTerm(term))
  return newData

def stringsToProcessable(strings: set[str], excludeListStemmed: set[str] | None = None) -> set[str]:
  """
  Converts a set of strings to a set of standardised terms that is ready for a human to process manually. 
  """
  # Split each string by treating it as an identifier
  terms = splitIdentifiers(strings)

  # Make sure terms consist of lowercase letters are stripped and non-empty
  terms = cleanSetOfTerms(terms)

  # Remove single-letter words
  terms = {term for term in terms if len(term) > 1}

  # Remove stop words
  stopWords = set(nltk.corpus.stopwords.words('english'))
  terms = {term for term in terms if term not in stopWords}

  # Fix spelling
  terms = {fixUSSpelling(term) for term in terms}

  # Remove the terms we save seen i.e. the stemmed version is in the combined set
  if excludeListStemmed:
    terms = removeSeenStemmed(terms, excludeListStemmed)

  return terms

def preprocessIdentifier(identifier: str) -> list[str]:
    """
    almost identical to `stringsToProcessable`, but this function uses
    a deterministic order
    TODO: deduplicate
    """
    # Split each string by treating it as an identifier
    terms = splitIdentifier(identifier)

    # To lowercase
    terms = [convertToLowercase(term) for term in terms if term != ""]

    # Remove single-letter words
    terms = [term for term in terms if len(term) > 1]

    # Remove stop words
    terms = [term for term in terms if term not in stopWords]

    # Fix spelling
    terms = [fixUSSpelling(term) for term in terms]

    return terms

def dictToCsv(myDict: dict[any, any]) -> str:
  lines = []
  for k, v in myDict.items():
    if isinstance(v, list):
      v = ",".join(map(str, v))
    else:
      v = str(v)
    lines.append(f"{k},{v}")
  return "\n".join(lines)
