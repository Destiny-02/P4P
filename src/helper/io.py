from .stats import average
import os

def printStats(isEitherSet, isOnlyIdentifiersSet, isOnlyCommentsSet, la):
    """
    Prints the stats
    """
    # Average % domain terms in source code
    print("1: {:.2%}".format(average(isEitherSet)))

    # Average % domain terms only in identifiers
    print("2.1: {:.2%}".format(average(isOnlyIdentifiersSet)))

    # Average % domain terms only in comments
    print("2.2: {:.2%}".format(average(isOnlyCommentsSet)))

    # LA between any 2 pairs of domain vocabs
    print("3: {:.2%}".format(la))

def cleanOutFolder(outPath):
  outFiles = os.listdir(outPath)
  for filename in outFiles:
      filePath = os.path.join(outPath, filename)
      if os.path.isfile(filePath):
          os.remove(filePath)

def findRepoPaths(pathToData):
  """
  Finds the paths to all the repos in the data folder
  """
  repoPaths = []
  for folderName in os.listdir(pathToData):
    if os.path.isdir(os.path.join(pathToData, folderName)):
      repoPaths.append(os.path.join(pathToData, folderName))
  return repoPaths

def findJavaFiles(folderPath):
  """
  Finds all the java files in the folder and its subfolders
  """
  javaFiles = set()
  for dirpath, _, filenames in os.walk(folderPath):
    for filename in filenames:
      if filename.endswith('.java'):
        javaFiles.add(os.path.join(dirpath, filename))
  return javaFiles
