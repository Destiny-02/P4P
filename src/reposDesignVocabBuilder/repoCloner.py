import os
import subprocess
from os import path
import requests


def clone_repos(repoList: list[str], destinationFolder: str):
    os.makedirs(destinationFolder, exist_ok=True)
    os.chdir(destinationFolder)

    for repoName in repoList:
        repoUrl = f"https://github.com/{repoName}.git"
        subprocess.call(['git', 'clone', repoUrl])

    print("Cloning completed.")

def getPath(relativePath):
    return path.join(path.dirname(__file__), relativePath)

if __name__ == "__main__":
    baseApiUrl = "https://api.github.com/search/repositories?q="
    query = ("is:public%20size:50..250%20stars:%3E=20%20pushed:%3E2018-06-01%20language:java%20"
             "license:mit%20license:apache-2.0%20license:gpl&sort=stars&per_page=100")
    queryUrl = baseApiUrl+query

    destinationFolder = "repos/"

    response = requests.get(queryUrl)
    jsonData = response.json()
    repoList = [item["full_name"] for item in jsonData["items"]]

    clone_repos(repoList, destinationFolder)
