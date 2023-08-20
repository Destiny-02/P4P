import re
from spiral import ronin
from ..typeDefs import (
    AbbreviationsDictionary,
)

# abbreviation then definition:
# looks for a capitalized abbreviation, then any whitespace or ":" or "(",
# then looks for any alphanumeric chars until ")" or EOL or EOF.
regExForAbbrThenDef = "([A-Z-]{2,}) *:? *\\(? *((\\w| |-)+)(\\)|\n|$)"

# definition then abbreviation:
# opposite of the above. Looks for any words that are followed by an
# abbreviation in parathenses.
# This regex itself is too greedy, it grabs the entire paragraph before
# the definition so the python code has to remove surplus words.
regExForDefThenAbbr = "((\\w| |-)+) +\\( *([A-Za-z]{2,}) *(\\)|\n|$)"


def getWordsThatBelongToAbbreviation(abbreviation: str, definition: str) -> str:
    """
    this is O(n), we have two pointers set to the ends of
    both strings, and we work backwards until we find evidence
    that the words we've visited so far could conceivably
    be the defintion of the abbreviation.
    """
    defIndex = len(definition) - 1
    # we don't actually need to store the index of abbr

    # these two variables are mutated
    abbrWorking = abbreviation.lower()
    defWorking = definition.lower()

    while abbrWorking:
        charToFind = abbrWorking[-1]
        isFirstChar = len(abbrWorking) == 1

        defIndex = (
            # if this is the first char of the abbreviation, it
            # must match the first letter of a word
            defWorking.rfind(" " + charToFind) + 1
            if isFirstChar
            # otherwise just seach for the lastIndexOf that char
            else defWorking.rfind(charToFind)
        )

        if defIndex == -1:
            return ""  # abort, we ran out of words that could form the defintion

        # remove the char that we've just checked
        abbrWorking = abbrWorking[:-1]
        # trim the definition down to remove the text we've seen
        defWorking = defWorking[:defIndex]

    # if we get to here, return only the part of the
    # definition that we needed to create the abbreviation.

    # include the entire first and last words. So search for
    # the first space character before defIndex
    startOfFirstWord = definition[:defIndex].rfind(" ") + 1

    return definition[startOfFirstWord:]


def createDomainSpecificAbbreviationDictionary(
    allComments: set[str],
) -> AbbreviationsDictionary:
    """
    loops thru all the comments that were parsed and all
    documentation files, and tries to identifiy abbreviation
    definitions.
    """
    abbreviations: dict[str, list[str]] = {}

    def storeMatch(_abbr: str, _definition: str):
        definition = _definition.strip(" -/_()[]{}")
        abbr = _abbr.lower()

        # use some logic to strip down definition to the actual words
        definition = getWordsThatBelongToAbbreviation(abbr, definition)

        if len(definition) < len(abbr):
            return  # abort, we've extracted it the wrong way around

        if abbr not in abbreviations:
            abbreviations[abbr] = []

        if definition not in abbreviations[abbr]:
            abbreviations[abbr].append(definition)

    for comment in allComments:
        # first try the forward regex
        matches = re.findall(regExForAbbrThenDef, comment, re.DOTALL)
        for match in matches:
            (abbr, definition, _, _) = match
            storeMatch(abbr, definition)

        # then try the backward regex
        matches = re.findall(regExForDefThenAbbr, comment, re.DOTALL)
        for match in matches:
            (definition, _, abbr, _) = match
            storeMatch(abbr, definition)

    # load the default frequency table used by the splitter
    frequencies = ronin.frequencies_from_pickle(ronin._DEFAULT_FREQ_PICKLE)

    # augment it by adding our own domain-specific abbreviations
    for abbr in abbreviations:
        frequencies[abbr] = 100000

    # tell the splitter about our domain-specific abbreviations
    ronin.init(frequencies=frequencies)

    return {
        "abbreviations": abbreviations,
        # TODO: it would be nice if users could define their own skip{Words,Identifiers}
        "skipWords": [],
        "skipIdentifiers": [],
    }
