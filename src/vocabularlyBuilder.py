from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet, stemTerm, setToTxtNoDuplicates, stringsToProcessable
)
from helper.io import findJavaFiles, setToSheet
from os import path
import csv

def saveTermsToBeCategorised(pathToData, domainFolderName):
	"""
	Saves the terms to be categorised as context/design/neither from the source code to a spreadsheet
	"""
	# Get the terms we have already seen and categorised
	(contextTerms, designTerms, neitherTerms, combinedTerms) = getVocabularies(domainFolderName)

	# Parse the identifiers
	(identifiers, comments) = invokeParser(findJavaFiles(pathToData))

	# Split the identifiers into standardised terms suitable for a human to categorise manually
	terms = stringsToProcessable(identifiers, combinedTerms)

	# Write the terms to be categorised to a spreadsheet
	setToSheet(terms, "to-categorise.csv")

def saveTermsToBeDetermined(pathToDomainDescription, domainFolderName):
	"""
	Saves the terms to be determined as from the domain or not from the descriptor to a spreadsheet
	"""
	# Get the terms we have already seen and categorised
	(contextTerms, designTerms, neitherTerms, combinedTerms) = getVocabularies(domainFolderName)

	# Get the terms in the domain description
	with open(pathToDomainDescription, 'r') as file:
		domainDescription = file.read()

	# Split the domain description into terms by whitespace
	terms = domainDescription.split()
	
	# Split the identifiers into standardised terms suitable for a human to manually look through
	terms = stringsToProcessable(terms, combinedTerms)

	# Sort the terms alphabetically
	terms = sorted(terms)

	# Write the terms to be determined to a spreadsheet
	setToSheet(terms, "to-determine.csv")

def saveCategoriseSheetToTxt(domainFolderName):
	"""
	Saves the to-categorise.csv spreadsheet containing terms that have been categorised as 
	context (c), design (d) or neither (n) to the context.txt, design.txt and neither.txt text files
	"""
	dSet = set()
	cSet = set()
	nSet = set()
	undefinedSet = set()

	with open('to-categorise.csv', 'r') as file:
		reader = csv.reader(file)
		for row in reader:
			word, letter = row
			if letter == 'd':
				dSet.add(stemTerm(word))
			elif letter == 'c':
				cSet.add(stemTerm(word))
			elif letter == 'n':
				nSet.add(stemTerm(word))
			else:
				undefinedSet.add(word)

	setToTxtNoDuplicates(dSet, getPath("vocabularies/" + domainFolderName + "/design.txt"))
	setToTxtNoDuplicates(cSet, getPath("vocabularies/" + domainFolderName + "/context.txt"))
	setToTxtNoDuplicates(nSet, getPath("vocabularies/" + domainFolderName + "/neither.txt"))

	if (len(undefinedSet) > 0):
		print("The following terms have not been categorised:")
		print(undefinedSet)

def saveDomainSheetToTxt(domainFolderName):
	"""
	Saves the to-determine.csv spreadsheet containing terms that have been categorised as 
	domain terms / context schema (c) to the context.txt text file
	"""
	dSet = set()

	with open('to-determine.csv', 'r') as file:
		reader = csv.reader(file)
		for row in reader:
			word, letter = row
			if letter == 'c':
				dSet.add(stemTerm(word))

	setToTxtNoDuplicates(dSet, getPath("vocabularies/" + domainFolderName + "/context.txt"))

def getVocabularies(domainFolderName):
	contextTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/context.txt"))
	designTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/design.txt"))
	neitherTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/neither.txt"))
	combinedTerms = contextTerms.union(designTerms).union(neitherTerms)
	return (contextTerms, designTerms, neitherTerms, combinedTerms)

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
	"""
	Note: recommend extracting domain terms from the domain description first, 
	then categorising the terms from the identifiers in the data folder.

	Instructions for extracting domain terms fron a piece of text: 
	1. Run this file with only the call to saveTermsToBeDetermined uncommented
	2. Open the to-determine.csv spreadsheet
	3. For each term, decide whether it is a domain term (from the context schema)
	4. Enter the letter d in the second column of the spreadsheet for these terms
	5. Save the spreadsheet
	6. Run this file with only the call to saveDomainSheetToTxt uncommented
	7. context.txt will be updated with the terms you classified as domain terms

	Instructions for categorising terms: 
	1. Pick a folder in the data folder that you want to categorise the identifiers for
	2. Change the path in the call to saveTermsToBeCategorised to the path of the folder you picked
	3. Run this file with only the call to saveTermsToBeCategorised uncommented
	4. Open the to-categorise.csv spreadsheet
	5. For each term, decide whether it is a context term, a design term or neither
	6. Enter the letter c, d or n in the second column of the spreadsheet
	7. Save the spreadsheet
	9. Run this file with only the call to saveCategoriseSheetToTxt uncommented
	10. context.txt, design.txt and neither.txt will be updated with the terms you categorised
	"""
	# saveTermsToBeDetermined(getPath("../data/ugrad-009-01/domain-description.txt"), "ugrad-009-01")
	# saveDomainSheetToTxt("ugrad-009-01")
	# saveTermsToBeCategorised(getPath("../data/ugrad-009-01/design1010"), "ugrad-009-01")
	saveCategoriseSheetToTxt("ugrad-009-01")
