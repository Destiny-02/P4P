import os
from tool_helper.conversion import txtToSet, jsonToSet, txtToSetWithEquivalents, convertEquivalents, setToStemmedSet
from tool_helper.stats import findLA, getDVInSet

# TODO: get this from the command line
pathToData = '../data/ugrad-009-01/'

domainTerms, equivalents = txtToSetWithEquivalents(pathToData + 'domain_terms.txt')

# Loop through all the codebases
for folderName in os.listdir(pathToData):
    if os.path.isdir(os.path.join(pathToData, folderName)):
      repoPath = os.path.join(pathToData, folderName)
      print("Processing " + repoPath)
      
      # TODO: parse the codebase in this folder
      # TODO: get a set of the identifiers
      # TODO: get a set of the comments
      identifiers = jsonToSet('../out/java.json')
      comments = {"the", "payroll", "system", "will", "process", "each", "salaried", "employee's", "payroll"}

      identifiers = setToStemmedSet(identifiers)
      comments = setToStemmedSet(comments)

      identifiers = convertEquivalents(identifiers, equivalents)
      comments = convertEquivalents(comments, equivalents)

      dvIdentifiers = getDVInSet(domainTerms, identifiers)
      dvComments = getDVInSet(domainTerms, comments)
      dv = dvIdentifiers.union(dvComments)
      numDT = len(domainTerms)

      # print("Terms in identifiers but not in domainTerms:")
      # print(identifiers - domainTerms) 
      # print("Terms in domainTerms but not in identifiers:")
      # print(domainTerms - identifiers)
      
      # % domain terms in source code
      print("1: {:.2%}".format(len(dv)/numDT))

      # % domain terms in identifiers
      print("2.1: {:.2%}".format(len(dvIdentifiers.difference(dvComments))/numDT))
      # % domain terms in comments
      print("2.2: {:.2%}".format(len(dvComments.difference(dvIdentifiers))/numDT))

# LA between any 2 pairs of domain vocabs
print("3: {:.2%}".format(findLA(domainTerms)))

# Clean up the out folder
outPath = '../out/'
outFiles = os.listdir(outPath)
for filename in outFiles:
    filePath = os.path.join(outPath, filename)
    if os.path.isfile(filePath):
        os.remove(filePath)
