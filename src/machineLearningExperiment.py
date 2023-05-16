from os import path

def main(pathToSmallVocabulary, pathToBigVocabularly, pathToRepos):
  print("TODO")

def getPath(relativePath):
  return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
  """
  A basic "machine learning" experiment. 
  The goal is to determine how accurate our classifications are for unclassified repos. 
  We will measure this as the percentage of terms that are correctly classified.
  """
  main(getPath("../results/ugrad-009-01/vocabularies/10/"), getPath("../results/ugrad-009-01/vocabularies/20/"), getPath("../data/ugrad-009-01/"))