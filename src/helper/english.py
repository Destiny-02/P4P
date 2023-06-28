import json
from os import path
import enchant

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
	
def fixSpelling(word: str, retUsedForDisplay=True) -> str:
	"""
	If the word is less than five characters, it returns the word.
	If the word seems to be misspelt, it returns the corrected spelling.
	Otherwise, it returns the word.
	Note: if the word is misspelt, the function can sometimes return more than one word (separated by a space) 
	so this function should only be used to display feedback to the user.
	"""
	if len(word) < 5:
		return word

	ukDict = enchant.Dict("en_GB")

	if ukDict.check(word):
		return word

	suggestions = ukDict.suggest(word)
	if suggestions and suggestions[0]:
		print("Fixed spelling of " + word + " to " + suggestions[0])
		return cleanString(suggestions[0], retUsedForDisplay)

	return word
   

def cleanString(term: str, keepSpaces=False) -> str:
	"""
	Converts a term to stripped lowercase English letters
	keepSpaces - If true, spaces are kept in the converted output string
	"""
	if keepSpaces:
		return ''.join(char for char in term if char.isalpha() or char.isspace()).lower().strip()

	return ''.join(char for char in term if char.isalpha()).lower().strip()

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)