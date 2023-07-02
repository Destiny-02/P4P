import os
import sys

# To fix import errors
project_dir = os.path.abspath(
    os.path.join(os.path.dirname(__file__), "../src/categoriseIdentifiers")
)
sys.path.insert(0, project_dir)

import src.helper.invokeParser  # required because imports are broken
from src.categoriseIdentifiers.helpers.createDomainSpecificAbbreviationDictionary import (
    createDomainSpecificAbbreviationDictionary,
)


def test_createDomainSpecificAbbreviationDictionary():
    sampleFilePath = os.path.join(project_dir, "../parsers/tests/sample.md")
    with open(sampleFilePath, "r", encoding="utf8") as file:
        assert createDomainSpecificAbbreviationDictionary(set([file.read()])) == {
            "abbreviations": {
                "ais": ["Automatic Identification System "],
                "com": ["communication port"],
                "cospas-sarsat": [
                    "Cosmicheskaya Sistema Poiska Avariynyh Sudov - Search And Rescue Satellite-Aided Tracking"
                ],
                "etc": ["S"],  # TODO: this is unexpected
                "etcs": [
                    "- European Train Control System "
                ],  # TODO: should have been trimmed
                "saaap": ["Sample Acronym and Abbreviation Parser"],
                "stb": ["D"],  # TODO: this is unexpected
                "stbd": [" or starboard "],  # TODO: this backtracked too greedily
                "utc": ["Universal Coordinated Time"],
                "vts": ["Vessel Traffic Services"],
            },
            "skipIdentifiers": [],
            "skipWords": [],
        }
