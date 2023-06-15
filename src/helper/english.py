import enchant
import re

def fixSpelling(word):
    """
    If the word is less than five characters, it returns itself.
    If the word is a valid word in UK English, it returns itself.
	If the word is a valid word in US English, it returns the UK English spelling of the word.
	If the word is misspelled and has a suggestion in the UK English dictionary, it returns the suggested word.
	Otherwise, it returns itself.
    """
    # If it is less than five characters and not a word, it is likely an abbreviation
    if len(word) < 5:
        return word

    ukDict = enchant.Dict("en_GB")

    if ukDict.check(word):
        return word
    
    suggestions = ukDict.suggest(word)
    if suggestions and suggestions[0] == convertToLowercase(suggestions[0]):
        print("Fixed spelling of " + word + " to " + suggestions[0])
        return suggestions[0]
    
    return word

def convertToLowercase(term: str) -> str:
  """
  Converts a term to stripped lowercase
  """
  return re.sub(r'[^a-zA-ZÀ-ÖØ-öø-ÿ]', '', term).lower().strip()
