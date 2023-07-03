from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)
from ...helper.english import fixSpelling


def isMisspelling(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]

    correctedSpelling = fixSpelling(word)

    if correctedSpelling != word:
        # if the spellchecker gave us a suggestion, show it to the user
        return {
            "issueType": "misspelling",
            "severity": Severity.WARNING,
            "suggestion": f"“{word}” appears to be misspelt, did you mean “{correctedSpelling}”?",
        }

    return None
