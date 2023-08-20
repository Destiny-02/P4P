import os

outputFolderPath = os.path.join(os.path.dirname(__file__), "../../../data")

# list of folder names in the "data" folder
domainList = [folder.name for folder in os.scandir(outputFolderPath) if folder.is_dir()]


# list of folder names in the "data/{domain}" folder
def getReposForDomain(domain: str):
    domainFolder = os.scandir(os.path.join(outputFolderPath, domain))
    return [folder.name for folder in domainFolder if folder.is_dir()]
