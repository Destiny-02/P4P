from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet,
	cleanSetOfTerms, removeSeenStemmed, setToTxt
)
from helper.io import findJavaFiles, setToSheet, deleteFileIfExists
from helper.splitting import splitIdentifiers
from os import path
import csv


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
	setToSheet(newTerms, "toCategorise.csv")

def saveCategoriseSheetToTxt(domainFolderName):
	"""
	Saves the toCategorise.csv spreadsheet containing terms that have been categorised as 
	context (c), design (d) or neither (n) to the context.txt, design.txt and neither.txt text files
	"""
	dSet = set()
	cSet = set()
	nSet = set()
	undefinedSet = set()

	with open('toCategorise.csv', 'r') as file:
		reader = csv.reader(file)
		for row in reader:
			word, letter = row
			if letter == 'd':
				dSet.add(word)
			elif letter == 'c':
				cSet.add(word)
			elif letter == 'n':
				nSet.add(word)
			else:
				undefinedSet.add(word)

	setToTxt(dSet, getPath("vocabularies/" + domainFolderName + "/design.txt"))
	setToTxt(cSet, getPath("vocabularies/" + domainFolderName + "/context.txt"))
	setToTxt(nSet, getPath("vocabularies/" + domainFolderName + "/neither.txt"))

	if (len(undefinedSet) > 0):
		print("The following terms have not been categorised:")
		print(undefinedSet)

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
	"""
	Instructions: 
	1. Pick a folder in the data folder that you want to categorise the identifiers for
	2. Change the path in the call to saveTermsToBeCategorised to the path of the folder you picked
	3. Run this file
	4. Open the toCategorise.csv spreadsheet
	5. For each term, decide whether it is a context term, a design term or neither
	6. Enter the letter c, d or n in the second column of the spreadsheet
	7. Save the spreadsheet
	8. Comment out the call to saveTermsToBeCategorised and uncomment the call to saveCategoriseSheetToTxt
	9. Run this file
	10. context.txt, design.txt and neither.txt will be updated with the terms you categorised
	"""
	# saveTermsToBeCategorised(getPath("../data/ugrad-009-01/design1000"), "ugrad-009-01")
	# saveCategoriseSheetToTxt("ugrad-009-01")
