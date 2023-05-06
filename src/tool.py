from helper.conversion import txtToSetWithEquivalents, convertEquivalents, setToStemmedSet, extractWordsFromSet, cleanSetOfTerms
from helper.stats import findLA, getDVInSet
from helper.io import printStats, cleanOutFolder, findRepoPaths, findJavaFiles
from helper.splitting import splitIdentifiers

def main(pathToData):
    domainTerms, equivalents = txtToSetWithEquivalents(pathToData + 'domain_terms.txt')

    # To be used for averages
    inEither = set()
    inOnlyIdentifiers = set() 
    inOnlyComments = set() 
    vocabs = list()

    for repoPath in findRepoPaths(pathToData):
        print("Processing " + repoPath)

        identifiers = set()
        comments = set()
        
        for filePath in findJavaFiles(repoPath):
            print(filePath)
            # TODO: parse the codebase in this file
            # TODO: add to the identifiers
            # TODO: add to the set of the comments
        identifiers = {"i", "current", "money", "payslip", "employeePay"}
        comments = {"the payroll system will", "process", "each", "salaried", "employees", "payroll"}

        # Split identifiers and comments into words
        identifiers = splitIdentifiers(identifiers)
        comments = extractWordsFromSet(comments)

        # "Clean" the identifiers and comments
        identifiers = cleanSetOfTerms(identifiers)
        comments = cleanSetOfTerms(comments)
        # Note: all comments and terms should be single, lowercase, stripped, non-empty words at this point

        # Stem identifiers and comments
        identifiers = setToStemmedSet(identifiers)
        comments = setToStemmedSet(comments)

        # Convert equivalents
        identifiers = convertEquivalents(identifiers, equivalents)
        comments = convertEquivalents(comments, equivalents)

        # Count the number of domain terms in the identifiers and comments
        dvIdentifiers = getDVInSet(domainTerms, identifiers)
        dvComments = getDVInSet(domainTerms, comments)
        dv = dvIdentifiers.union(dvComments)
        numDT = len(domainTerms)

        # Add to the sets for average stats later
        inEither.add(len(dv)/numDT)
        inOnlyIdentifiers.add(len(dvIdentifiers.difference(dvComments))/numDT)
        inOnlyComments.add(len(dvComments.difference(dvIdentifiers))/numDT)
        vocabs.append(dv)

    printStats(inEither, inOnlyIdentifiers, inOnlyComments, findLA(vocabs))

if __name__ == '__main__':
    # TODO: get this from the command line
    main('../data/ugrad-009-01/')

    # Use this if we are writing results to the out folder
    # cleanOutFolder('../out/')
