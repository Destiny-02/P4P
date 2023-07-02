from typing_extensions import Unpack
from typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)


def isSynonym(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]

    synonymMap = options["context"]["synonymMap"]

    moreCommonTerm = synonymMap.get(word)
    if moreCommonTerm:
        return {
            "issueType": "synonym",
            "severity": Severity.WARNING,
            "suggestion": f"“{word}” appears to be a synonym for the more well known term “{moreCommonTerm}”.",
        }

    return None
