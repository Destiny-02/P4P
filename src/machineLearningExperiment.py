from os import path
from analyseCodebases import main as analyseCodebases
from helper.io import deleteFileIfExists
import csv

def main(pathToSmallVocabulary, pathToBigVocabulary, pathToRepos):
  (smallDesignCounts, smallContextCounts, smallNeitherCounts, smallTotalCounts) = analyseCodebases(pathToRepos, pathToSmallVocabulary)
  (bigDesignCounts, bigContextCounts, bigNeitherCounts, bigTotalCounts) = analyseCodebases(pathToRepos, pathToBigVocabulary)

  # Calculate the percentage of terms that are correctly classified
  designPercentagesCorrect = []
  contextPercentagesCorrect = []
  neitherPercentagesCorrect = []
  totalPercentagesCorrect = []

  for i in range(len(smallDesignCounts)):
    designPercentagesCorrect.append(smallDesignCounts[i] / bigDesignCounts[i])
    contextPercentagesCorrect.append(smallContextCounts[i] / bigContextCounts[i])
    neitherPercentagesCorrect.append(smallNeitherCounts[i] / bigNeitherCounts[i])
    totalPercentagesCorrect.append(smallTotalCounts[i] / bigTotalCounts[i])

  return (designPercentagesCorrect, contextPercentagesCorrect, neitherPercentagesCorrect, totalPercentagesCorrect)

def writeResultsToCsv(designPercentagesCorrect, contextPercentagesCorrect, neitherPercentagesCorrect, totalPercentagesCorrect, csvName):
  deleteFileIfExists(csvName)
  with open(csvName, 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Design", "Context", "Neither", "Total"])
    for i in range(len(designPercentagesCorrect)):
      writer.writerow([designPercentagesCorrect[i], contextPercentagesCorrect[i], neitherPercentagesCorrect[i], totalPercentagesCorrect[i]])

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
  """
  A basic "machine learning" experiment. 
  The goal is to determine how accurate our classifications are for unclassified repos. 
  We will measure this as the percentage of terms that are correctly classified.
  """
  (designPercentagesCorrect, contextPercentagesCorrect, neitherPercentagesCorrect, totalPercentagesCorrect) = main(getPath("../results/ugrad-009-01/vocabularies/10/"), getPath("../results/ugrad-009-01/vocabularies/20/"), getPath("../data/ugrad-009-01/"))
  writeResultsToCsv(designPercentagesCorrect, contextPercentagesCorrect, neitherPercentagesCorrect, totalPercentagesCorrect, getPath("../results/ugrad-009-01/ugrad-009-01-ml-10-20.csv"))