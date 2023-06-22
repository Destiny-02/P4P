import json
from os import path

def fixUSSpelling(word: str) -> str:
    """
    If the word is a word in US English, it returns the UK English spelling of the word.
    Otherwise, it returns itself.
    american_spellings.json must be in the data/downloaded folder
    """
    # If it is less than five characters and not a word, it is more likely that it is a abbreviation or acronym so we won't touch it
    if len(word) < 5:
        return word

    with open(getPath('../../data/downloaded/american_spellings.json')) as f:
        spellings = json.load(f)
    
    britishSpelling = spellings.get(word)
    if britishSpelling:
        return cleanString(britishSpelling)
    else:
        return word

def cleanString(term: str) -> str:
  """
  Converts a term to stripped lowercase English letters
  """
  return ''.join(char for char in term if char.isalpha()).lower().strip()

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)