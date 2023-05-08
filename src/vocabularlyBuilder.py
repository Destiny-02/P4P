from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet,
	cleanSetOfTerms, removeSeenStemmed
)
from helper.io import findJavaFiles, setToSheet, deleteFileIfExists
from helper.splitting import splitIdentifiers
from os import path


def saveTermsToBeCategorised(pathToData, domainFolderName):
	"""
	Saves the terms to be categorised to a spreadsheet
	"""
	# Get the terms we have already seen and categorised
	contextTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/context.txt"))
	designTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/design.txt"))
	neitherTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/neither.txt"))
	combinedTerms = contextTerms.union(designTerms).union(neitherTerms)

	# Parse the identifiers
	(identifiers, comments) = invokeParser(findJavaFiles(pathToData))
	print(identifiers)

	# Split identifiers
	terms = splitIdentifiers(identifiers)

	# "Clean" the terms
	terms = cleanSetOfTerms(terms)

	# Remove the terms whose stemmed version is in the combined set
	newTerms = removeSeenStemmed(terms, combinedTerms)

	# Write the terms to be categorised to a spreadsheet
	setToSheet(newTerms, "toCategorise.xlsx")

def saveSheetToTxt():
	"""
	Saves the toCategorise.xlsx spreadsheet containing terms that have been categorised as 
	context (c), design (d) or neither (n) to the context.txt, design.txt and neither.txt text files
	"""
	return

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
	saveTermsToBeCategorised(getPath("../data/ugrad-009-01/design1000"), "ugrad-009-01")