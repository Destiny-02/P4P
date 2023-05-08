from .stats import average
import os.path as path
import os
import json
import csv

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
      filePath = path.join(outPath, filename)
      if path.isfile(filePath):
          os.remove(filePath)

def saveJsonDebugFile(jsonObj: object):
    outputFilePath = path.join(path.dirname(__file__), "../../debug-output.json")
    with open(outputFilePath, 'w', encoding="utf-8") as file:
        file.write(json.dumps(jsonObj, indent=4))


def findRepoPaths(pathToData):
  """
  Finds the paths to all the repos in the data folder
  """
  repoPaths = []
  for folderName in os.listdir(pathToData):
    if path.isdir(path.join(pathToData, folderName)):
      repoPaths.append(path.join(pathToData, folderName))
  return repoPaths

def findJavaFiles(folderPath):
  """
  Finds all the java files in the folder and its subfolders
  """
  javaFiles = set()
  for dirpath, _, filenames in os.walk(folderPath):
    for filename in filenames:
      if filename.endswith('.java'):
        javaFiles.add(path.join(dirpath, filename))
  return javaFiles

def setToSheet(data, sheetName):
  deleteFileIfExists(sheetName)

  with open(sheetName, 'w', newline='') as csvfile:
    writer = csv.writer(csvfile)
    for item in data:
        writer.writerow([item])

def deleteFileIfExists(filePath):
  if path.exists(filePath):
    os.remove(filePath)