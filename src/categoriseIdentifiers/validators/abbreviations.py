import json
from os import path
from typing_extensions import Unpack
from typeDefs import (
    Diagonstics,
    AbbreviationsDictionary,
    Severity,
    ValidatorArguments,
)


wellKnownDictionary: AbbreviationsDictionary = json.load(
    open(
        path.join(
            path.dirname(__file__), "../../../data/downloaded/abbreviations-dict.json"
        ),
        encoding="utf8",
    )
)


def isAbbreviated(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]
    identifier = options["identifier"]
    context = options["context"]

    domainSpecificDictionary = context.get("domainSpecificAbbreviationDictionary")

    # check if this specific word should be skipped
    if (word in wellKnownDictionary.get("skipWords")) or (
        word in domainSpecificDictionary.get("skipWords")
    ):
        return None

    # check if this whole identifier should be skipped
    if (identifier in wellKnownDictionary.get("skipIdentifiers")) or (
        identifier in domainSpecificDictionary.get("skipIdentifiers")
    ):
        return None

    suggestedWellKnownReplacement = wellKnownDictionary.get("abbreviations").get(word)
    suggestedDomainSpecificReplacement = domainSpecificDictionary.get(
        "abbreviations"
    ).get(word)

    # merge arrays in case there are suggestions from both dictionarys
    suggestedReplacements = (suggestedWellKnownReplacement or []) + (
        suggestedDomainSpecificReplacement or []
    )

    # check if the word is a known abbreviation
    if suggestedReplacements:
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
            "suggestion": f"“{word}” appears to be an abbreviation for “{suggestedReplacements[0]}”.",
        }

    return None
