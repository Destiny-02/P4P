from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet, stemTerm, setToTxtNoDuplicates, stringsToProcessable, setIntersectionStemmed, setToStemmedSet
)
from helper.io import findJavaFiles, setToSheet, deleteFileIfExists
from os import path
import csv

def saveTermsToBeCategorised(pathToDataList, domainFolderName):
	"""
	Saves the terms to be categorised as context/design/neither from the source code to a spreadsheet
	"""
	# Get the terms we have already seen and categorised
	(_, _, _, combinedTerms, contextTermsAnswers, designTermsAnswers, neitherTermsAnswers, combinedTermsAnswers) = getVocabularies(domainFolderName, includeAnswers=True)

	# Clear the to-categorise.csv spreadsheet if we are processing multiple codebases because we will be appending to it
	multipleCodebases = len(pathToDataList) > 1
	if multipleCodebases:
		deleteFileIfExists("to-categorise.csv")

	for pathToData in pathToDataList:
		# Parse the identifiers
		(identifiers, _) = invokeParser(findJavaFiles(pathToData))

		# Split the identifiers into standardised terms suitable for a human to categorise manually
		terms = stringsToProcessable(identifiers, combinedTerms)

		# Do the same but as if the seen terms included the answers
		termsAfterAnswers = stringsToProcessable(terms, combinedTermsAnswers.union(combinedTerms))

		# The terms we can automatically determine are the terms that are in the answers and in the identifiers of the current codebase
		# Write the terms to context.txt, design.txt and neither.txt
		knownContextTerms = setIntersectionStemmed(terms, contextTermsAnswers)
		knownDesignTerms = setIntersectionStemmed(terms, designTermsAnswers)
		knownNeitherTerms = setIntersectionStemmed(terms, neitherTermsAnswers)
		setToTxtNoDuplicates(setToStemmedSet(knownContextTerms), getPath("vocabularies/" + domainFolderName + "/context.txt"))
		setToTxtNoDuplicates(setToStemmedSet(knownDesignTerms), getPath("vocabularies/" + domainFolderName + "/design.txt"))
		setToTxtNoDuplicates(setToStemmedSet(knownNeitherTerms), getPath("vocabularies/" + domainFolderName + "/neither.txt"))

		# Write the terms to be categorised to a spreadsheet
		setToSheet(termsAfterAnswers, "to-categorise.csv", append=multipleCodebases)
		print(str(len(termsAfterAnswers)) + " terms to categorise in " + pathToData)

def saveTermsToBeDetermined(pathToDomainDescription, domainFolderName):
	"""
	Saves the terms to be determined as from the domain or not from the descriptor to a spreadsheet
	"""
	# Get the terms in the domain description
	with open(pathToDomainDescription, 'r') as file:
		domainDescription = file.read()

	# Split the domain description into terms by whitespace
	terms = domainDescription.split()

	# Get the answers to the context terms we have already seen and categorised
	(_, _, _, combinedTerms, contextTermsAnswers, _, _, combinedTermsAnswers) = getVocabularies(domainFolderName, includeAnswers=True)
	
	# Split the words into standardised terms suitable for a human to manually look through
	terms = stringsToProcessable(terms, combinedTerms)
	
	# Do the same but as if the seen terms included the answers
	termsAfterAnswers = stringsToProcessable(terms, combinedTermsAnswers.union(combinedTerms))

	# The terms we can automatically determine are the terms that are in context-answer.txt and in the domain description
	# Write the terms to context.txt
	knownTerms = setIntersectionStemmed(terms, contextTermsAnswers)
	setToTxtNoDuplicates(setToStemmedSet(knownTerms), getPath("vocabularies/" + domainFolderName + "/context.txt"))

	# Write the terms to be determined to a spreadsheet
	setToSheet(termsAfterAnswers, "to-determine.csv")

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

def getVocabularies(domainFolderName, includeAnswers=False):
	contextTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/context.txt"))
	designTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/design.txt"))
	neitherTerms = txtToSet(getPath("vocabularies/" + domainFolderName + "/neither.txt"))
	combinedTerms = contextTerms.union(designTerms).union(neitherTerms)

	if not includeAnswers:
		return (contextTerms, designTerms, neitherTerms, combinedTerms)

	contextTermsAnswers = txtToSet(getPath("vocabularies/" + domainFolderName + "/context-answers.txt"))
	designTermsAnswers = txtToSet(getPath("vocabularies/" + domainFolderName + "/design-answers.txt"))
	neitherTermsAnswers = txtToSet(getPath("vocabularies/" + domainFolderName + "/neither-answers.txt"))
	combinedTermsAnswers = contextTermsAnswers.union(designTermsAnswers).union(neitherTermsAnswers)
	
	return (contextTerms, designTerms, neitherTerms, combinedTerms, contextTermsAnswers, designTermsAnswers, neitherTermsAnswers, combinedTermsAnswers)

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
	"""
	Note: recommend extracting domain terms from the domain description first, 
	then categorising the terms from the identifiers in the data folder.
	"""

	"""
	Instructions for extracting domain terms from a piece of text: 
	1. Run this file with only the call to saveTermsToBeDetermined uncommented
	2. Open the to-determine.csv spreadsheet
	3. For each term, decide whether it is a domain term (from the context schema)
	4. Enter the letter d in the second column of the spreadsheet for these terms
	5. Save the spreadsheet
	6. Run this file with only the call to saveDomainSheetToTxt uncommented
	7. context.txt will be updated with the terms you classified as domain terms
	"""
	# saveTermsToBeDetermined(getPath("../data/ugrad-009-01/domain-description.txt"), "ugrad-009-01")
	# saveDomainSheetToTxt("ugrad-009-01")

	"""
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
	# saveTermsToBeCategorised([getPath("../data/ugrad-009-01/design1000")], "ugrad-009-01")
	# saveCategoriseSheetToTxt("ugrad-009-01")

	"""
	Instructions for categorising terms in multiple codebases at once:
	1. Similar process to above, but use the path of the folder containing all the codebases
	"""
	# saveTermsToBeCategorised(findRepoPaths(getPath("../data/ugrad-009-01/")), "ugrad-009-01")
	# saveCategoriseSheetToTxt("ugrad-009-01")