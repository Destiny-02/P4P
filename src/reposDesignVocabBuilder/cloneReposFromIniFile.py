import os
from argparse import ArgumentParser
from .repoCloner import clone_repos


#
# To run this script:
# `python -m src.reposDesignVocabBuilder.cloneReposFromIniFile [domainName]`
# where [domainName] is the name of a domain (e.g. chess)
#

outputFolderPath = os.path.join(os.path.dirname(__file__), "../../data")

choices = [folder.name for folder in os.scandir(outputFolderPath) if folder.is_dir()]

parser = ArgumentParser()
parser.add_argument("domainName", choices=choices)

args = parser.parse_args()

print(args.domainName)

try:
    with open(
        os.path.join(outputFolderPath, args.domainName, "repos.ini"),
        "r",
        encoding="utf-8",
    ) as repoFile:
        repoList: list[str] = []

        for line in repoFile:
            link = line.split("#")[0].strip()
            if link:
                repoList.append(link.replace("https://github.com/", ""))

        clone_repos(repoList, os.path.join(outputFolderPath, args.domainName))

except FileNotFoundError:
    print("repos.ini is not defined")


print(args)
