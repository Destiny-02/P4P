import json
import sys
from os import path


# for some ridiculous reason you can't have double nested folders
if "pytest" in sys.modules:
    from categoriseIdentifiers.typeDefs import (
        Diagonstics,
        AbbreviationsDictionary,
        Severity,
    )
else:
    from typeDefs import (
        Diagonstics,
        AbbreviationsDictionary,
        Severity,
    )


dictionary: AbbreviationsDictionary = json.load(
    open(
        path.join(
            path.dirname(__file__), "../../../data/downloaded/abbreviations-dict.json"
        )
    )
)


def isAbbreviated(word: str, identifier: str) -> Diagonstics | None:
    # check if this specific word should be skipped
    if word in dictionary.get("skipWords"):
        return None

    # check if this whole identifier should be skipped
    if identifier in dictionary.get("skipIdentifiers"):
        return None

    # check if the word is a known abbreviation
    if word in dictionary.get("abbreviations"):
        suggestedReplacements = dictionary.get("abbreviations")[word]

        if len(suggestedReplacements) > 1:
            # this is a known vague abbreviation

            suggestionsStr = " or ".join(
                [f"“{long}”" for long in suggestedReplacements]
            )

            return {
                "issueType": "abbreviation",
                "severity": Severity.WARNING,
                "suggestion": f"“{word}” is a vauge abbreviation. Consider using {suggestionsStr} instead.",
            }

        # there is only one known meaning of this abbreviation, so it's not so bad
        return {
            "issueType": "abbreviation",
            "severity": Severity.INFO,
            "suggestion": f"“{word}” appears to be an abbreviation. Consider using “{suggestedReplacements[0]}” instead.",
        }

    return None
