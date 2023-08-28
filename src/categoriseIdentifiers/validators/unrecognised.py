from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)
from ...helper.conversion import stopWords


def isUnrecognised(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]

    if word in stopWords:
        return {
            "issueType": "stopWord",
            "severity": Severity.INFO,
            "suggestion": f"“{word}” is a stop-word",
        }

    return {
        "issueType": "unrecognised",
        "severity": Severity.INFO,
        "suggestion": f"“{word}” was not recognised",
    }
