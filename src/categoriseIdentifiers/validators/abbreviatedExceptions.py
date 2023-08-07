from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)


def isAbbreviationOf(identifier: str, typeName: str) -> bool:
    # this is the type name, with it's lowercase characters removed
    abbreviatedType = "".join(char for char in typeName if char.isupper()).lower()

    # there are less than 2 charaters -> definitely not valid
    if len(abbreviatedType) < 2:
        return False

    candidateAbbreviations = [
        abbreviatedType,
        f"{abbreviatedType}x",  # Ex at the end instead of E
        f"{abbreviatedType}rr",  # Err at the end instead of E
    ]

    # if any of the candidates, match, return true
    return any(candidate == identifier.lower() for candidate in candidateAbbreviations)


def isAbbreviatedException(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    """
    In java, it's apparently acceptable use weird abbreviations
    like 'fsoosex' which would mean FileSystemOutOfSpaceException.
    It's only allowed if the abbreviation matches the variables type.
    """

    identifier = options["identifier"]

    fullTypeName = ""
    appliesToTheseLocationsOnly: set[str] = set()

    # loop through everywhere where this identifier is used,
    # because it might be acceptable in some locations but not
    # in others.
    for source in options["sourceLocations"]:
        isJavaFile = source["fileName"].endswith(".java")
        typeInformation = source.get("typeInformation")
        if isJavaFile and typeInformation:
            typeName = typeInformation["typeName"]
            if isAbbreviationOf(identifier, typeName):
                # if we get to here, this is a valid example, so
                # we record where we noticed
                location = f"{source['fileName']}:{source['startOffset']}:{source['endOffset']}"
                appliesToTheseLocationsOnly.add(location)
                fullTypeName = typeName

    # this identifier is not an abbreviated exception
    if not fullTypeName:
        return None

    return {
        "issueType": "abbreviatedException",
        "severity": Severity.INFO,
        "suggestion": f"“{identifier}” appears to be an abbreviation of its type (“{fullTypeName}”).",
        "appliesToTheseLocationsOnly": appliesToTheseLocationsOnly,
    }
