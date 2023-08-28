from typing_extensions import Unpack
from ..typeDefs import (
    Diagonstics,
    Severity,
    ValidatorArguments,
)


def isMisused(
    **options: Unpack[ValidatorArguments],
) -> Diagonstics | None:
    word = options["word"]
    identifier = options["identifier"]

    # false if the identifier only has one word
    isFirstWord = identifier.startswith(word) and word != identifier

    # 1.
    # trivial example to show that it is possible to create
    # validators based on parts-of-speech and type information.
    if word == "is" and isFirstWord:
        # this identifier starts with "is". Many naming
        # guidelines say that only booleans should start
        # with "is"

        # error if this identifier is not a boolean

        appliesToTheseLocationsOnly: set[str] = set()

        # loop through everywhere where this identifier is used,
        # because it will have a different type in each location.
        for source in options["sourceLocations"]:
            typeInformation = source.get("typeInformation")
            if (
                # if we have type information…
                typeInformation
                # …and this identifier's type is known, but not a boolean
                and typeInformation["typeName"] not in ("boolean", "any", "unknown")
            ):
                location = f"{source['fileName']}:{source['startOffset']}:{source['endOffset']}"
                appliesToTheseLocationsOnly.add(location)

        # this is at least 1 dodgy case, so generate a diagnostic
        if appliesToTheseLocationsOnly:
            return {
                "issueType": "misused",
                "severity": Severity.WARNING,
                "suggestion": f"“{identifier}” is not a boolean, but its name suggests that it is.",
                "appliesToTheseLocationsOnly": appliesToTheseLocationsOnly,
            }

    # 2.
    # If this identifier appears often in the codebase, look at the most common type
    # If this type is different to the usual type, flag it.
    if isFirstWord:
        # count how often this identifier appears, and what type it usually has
        typeFrequencies: dict[str, int] = {}
        for source in options["sourceLocations"]:
            typeInformation = source.get("typeInformation")
            if typeInformation:
                typeName = typeInformation["typeName"]
                if typeName in typeFrequencies:
                    typeFrequencies[typeName] += 1
                else:
                    typeFrequencies[typeName] = 0

        totalOccurances = len(options["sourceLocations"])

        if not typeFrequencies.keys():
            return None  # abort, no type info

        mostCommonType = max(
            typeFrequencies.keys(),
            key=lambda typeName: typeFrequencies[typeName],
        )
        mostCommonTypePercentage = typeFrequencies[mostCommonType] / totalOccurances

        if mostCommonTypePercentage in ("any", "unknown"):
            return None  # abort, this identifier is usually untyped

        # now loop through every location, if it has an unusual type,
        # flag it.
        for source in options["sourceLocations"]:
            typeInformation = source.get("typeInformation")
            if typeInformation:
                typeName = typeInformation["typeName"]
                thisTypesFrequency = typeFrequencies[typeName]
                if (
                    # this type is not the typical type
                    typeName != mostCommonTypePercentage
                    # and this type appears in less than 5% of identifiers with this name
                    and thisTypesFrequency < 0.05 * totalOccurances
                ):
                    location = f"{source['fileName']}:{source['startOffset']}:{source['endOffset']}"
                    return {
                        "issueType": "misused",
                        "severity": Severity.WARNING,
                        "suggestion": f"“{identifier}” is almost always a “{mostCommonType}”, but here it is a “{typeName}”.",
                        "appliesToTheseLocationsOnly": set([location]),
                    }

    return None
