import json
import re
from os import path

def fixUSSpelling(word):
    """
	If the word is a word in US English, it returns the UK English spelling of the word.
	Otherwise, it returns itself.
    """
    # If it is less than five characters and not a word, it is more likely that it is a abbreviation or acronym so we won't touch it
    if len(word) < 5:
        return word

    with open(getPath('american-british-english-translator.json')) as f:
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
  return re.sub(r'[^a-zA-ZÀ-ÖØ-öø-ÿ]', '', term).lower().strip()

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)