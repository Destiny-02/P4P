import os
from .createDomainSpecificAbbreviationDictionary import (
    createDomainSpecificAbbreviationDictionary,
    getWordsThatBelongToAbbreviation,
)


def test_createDomainSpecificAbbreviationDictionary():
    sampleFilePath = os.path.join(
        os.path.dirname(__file__), "../../parsers/tests/sample.md"
    )

    with open(sampleFilePath, "r", encoding="utf8") as file:
        assert createDomainSpecificAbbreviationDictionary(set([file.read()])) == {
            "abbreviations": {
                "ais": ["Automatic Identification System"],
                "chatgpt": ["Chat Generative Pre-Trained Transformer"],
                "com": ["Communication port"],
                "cospas-sarsat": [
                    "Cosmicheskaya Sistema Poiska Avariynyh Sudov - Search And Rescue Satellite-Aided Tracking"
                ],
                "etcs": ["European Train Control System"],
                "saaap": ["Sample Acronym and Abbreviation Parser"],
                "softeng": ["Software Engineering"],
                "stbd": ["Starboard"],
                # It did not accept UTC beacuse the words are the wrong way around
                "vts": ["Vessel Traffic Services"],
            },
            "skipIdentifiers": [],
            "skipWords": [],
        }


def test_getWordsThatBelongToAbbreviation():
    assert (
        getWordsThatBelongToAbbreviation("SE", "the words Software Engineering")
        == "Software Engineering"
    )
    assert (
        getWordsThatBelongToAbbreviation("SoftENG", "the words Software Engineering")
        == "Software Engineering"
    )
    assert not getWordsThatBelongToAbbreviation(
        "stbd", "steer to starboard"  # fails because it's lowercase
    )
    assert getWordsThatBelongToAbbreviation("stbd", "steer to Starboard") == "Starboard"

    # it works for numbers too
    assert (
        getWordsThatBelongToAbbreviation(
            "B2C", "easiest question was Business 2 Customer"
        )
        == "Business 2 Customer"
    )

    # no possible solution because the order is wrong
    assert getWordsThatBelongToAbbreviation("ABC", "Charlie Alpha Bravo") == ""
