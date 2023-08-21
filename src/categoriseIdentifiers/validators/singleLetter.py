from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)

neutralDiagnostic: Diagonstics = {
    "issueType": "singleLetter",
    "severity": Severity.INFO,
    "suggestion": "",
}


def isSingleLetter(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]
    identifier = options["identifier"]

    if len(word) == 1:
        # this word is a single letter
        if identifier == word:
            # the entire identifier is a single letter
            appliesToTheseLocationsOnly: set[str] = set()

            # loop through everywhere where this identifier is used,
            # because it might be acceptable in some locations but not
            # in others.
            for source in options["sourceLocations"]:
                typeInformation = source.get("typeInformation")
                if (
                    # if we have type information…
                    typeInformation
                    # …and this could be an index…
                    and word in ("i", "j")
                    # …but it is not actually an index
                    and typeInformation.get("classification") != "index"
                ):
                    location = f"{source['fileName']}:{source['startOffset']}:{source['endOffset']}"
                    appliesToTheseLocationsOnly.add(location)

            # no dodgy cases, so nothing to report
            if not appliesToTheseLocationsOnly:
                return neutralDiagnostic

            return {
                "issueType": "singleLetter",
                "severity": Severity.WARNING,
                "suggestion": f"“{word}” does not appear to be an index. Consider using a more explicit name.",
                "appliesToTheseLocationsOnly": appliesToTheseLocationsOnly,
            }

        # this word is a single letter, within a larger identifier
        # This is totally fine.
        return neutralDiagnostic

    return None
