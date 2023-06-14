import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from helper.invokeParser import invokeParser
from helper.conversion import (
	txtToSet, stemTerm, setToTxtNoDuplicates, stringsToProcessable, setIntersectionStemmed, setToStemmedSet
)
from helper.io import findJavaFiles, setToSheet, deleteFileIfExists
from os import path
import csv

VOCAB_FOLDER = "../vocabularies/"
TO_CATEGORISE_FILE = "to-categorise.csv"
TO_DETERMINE_FILE = "to-determine.csv"

def saveTermsToBeCategorised(pathToDataList, domainFolderName):
	"""
	Saves the terms to be categorised as context/design/neither from the source code to a spreadsheet
	"""
	# Get the terms we have already seen and categorised
	(_, _, _, combinedTerms, contextTermsAnswers, designTermsAnswers, neitherTermsAnswers, combinedTermsAnswers) = getVocabularies(domainFolderName, includeAnswers=True)

	# Clear the to-categorise.csv spreadsheet if we are processing multiple codebases because we will be appending to it
	multipleCodebases = len(pathToDataList) > 1
	if multipleCodebases:
		deleteFileIfExists(getPath(TO_CATEGORISE_FILE))

	for pathToData in pathToDataList:
		# Parse the identifiers
		(identifiers, _) = invokeParser(findJavaFiles(pathToData), getPath("parser-output.json"))

		# Split the identifiers into standardised terms suitable for a human to categorise manually
		terms = stringsToProcessable(identifiers, combinedTerms)

		# Do the same but as if the seen terms included the answers
		termsAfterAnswers = stringsToProcessable(terms, combinedTermsAnswers.union(combinedTerms))

		# The terms we can automatically determine are the terms that are in the answers and in the identifiers of the current codebase
		# Write the terms to context.txt, design.txt and neither.txt
		knownContextTerms = setIntersectionStemmed(terms, contextTermsAnswers)
		knownDesignTerms = setIntersectionStemmed(terms, designTermsAnswers)
		knownNeitherTerms = setIntersectionStemmed(terms, neitherTermsAnswers)
		setToTxtNoDuplicates(setToStemmedSet(knownContextTerms), getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))
		setToTxtNoDuplicates(setToStemmedSet(knownDesignTerms), getPath(VOCAB_FOLDER + domainFolderName + "/design.txt"))
		setToTxtNoDuplicates(setToStemmedSet(knownNeitherTerms), getPath(VOCAB_FOLDER + domainFolderName + "/neither.txt"))

		# Write the terms to be categorised to a spreadsheet
		setToSheet(termsAfterAnswers, getPath(TO_CATEGORISE_FILE), append=multipleCodebases)
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
	setToTxtNoDuplicates(setToStemmedSet(knownTerms), getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))

	# Write the terms to be determined to a spreadsheet
	setToSheet(termsAfterAnswers, getPath(TO_DETERMINE_FILE))

def saveCategoriseSheetToTxt(domainFolderName):
	"""
	Saves the to-categorise.csv spreadsheet containing terms that have been categorised as 
	context (c), design (d) or neither (n) to the context.txt, design.txt and neither.txt text files
	"""
	dSet = set()
	cSet = set()
	nSet = set()
	undefinedSet = set()

	with open(getPath(TO_CATEGORISE_FILE), 'r') as file:
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

	setToTxtNoDuplicates(dSet, getPath(VOCAB_FOLDER + domainFolderName + "/design.txt"))
	setToTxtNoDuplicates(cSet, getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))
	setToTxtNoDuplicates(nSet, getPath(VOCAB_FOLDER + domainFolderName + "/neither.txt"))

	if (len(undefinedSet) > 0):
		print("The following terms have not been categorised:")
		print(undefinedSet)

def saveDomainSheetToTxt(domainFolderName):
	"""
	Saves the to-determine.csv spreadsheet containing terms that have been categorised as 
	domain terms / context schema (c) to the context.txt text file
	"""
	dSet = set()

	with open(getPath(TO_DETERMINE_FILE), 'r') as file:
		reader = csv.reader(file)
		for row in reader:
			word, letter = row
			if letter == 'c':
				dSet.add(stemTerm(word))

	setToTxtNoDuplicates(dSet, getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))

def getVocabularies(domainFolderName, includeAnswers=False):
	contextTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/context.txt"))
	designTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/design.txt"))
	neitherTerms = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/neither.txt"))
	combinedTerms = contextTerms.union(designTerms).union(neitherTerms)

	if not includeAnswers:
		return (contextTerms, designTerms, neitherTerms, combinedTerms)

	contextTermsAnswers = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/context-answers.txt"))
	designTermsAnswers = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/design-answers.txt"))
	neitherTermsAnswers = txtToSet(getPath(VOCAB_FOLDER + domainFolderName + "/neither-answers.txt"))
	combinedTermsAnswers = contextTermsAnswers.union(designTermsAnswers).union(neitherTermsAnswers)
	
	return (contextTerms, designTerms, neitherTerms, combinedTerms, contextTermsAnswers, designTermsAnswers, neitherTermsAnswers, combinedTermsAnswers)

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
	# Build the context terms from a piece of descriptive text
	saveTermsToBeDetermined(getPath("../../data/ugrad-009-01/domain-description.txt"), "ugrad-009-01")
	# saveDomainSheetToTxt("ugrad-009-01")

	# Categorising terms from identifiers in a codebase
	# saveTermsToBeCategorised([getPath("../../data/ugrad-009-01/design1000")], "ugrad-009-01")
	# saveCategoriseSheetToTxt("ugrad-009-01")

	# Categorising terms from identifiers in multiple codebases at once
	# saveTermsToBeCategorised(findRepoPaths(getPath("../data/ugrad-009-01/")), "ugrad-009-01")
	# saveCategoriseSheetToTxt("ugrad-009-01")