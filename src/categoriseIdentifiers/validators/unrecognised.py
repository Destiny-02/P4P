from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)


def isUnrecognised(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]

    return {
        "issueType": "unrecognised",
        "severity": Severity.INFO,
        "suggestion": f"“{word}” was not recognised",
    }
