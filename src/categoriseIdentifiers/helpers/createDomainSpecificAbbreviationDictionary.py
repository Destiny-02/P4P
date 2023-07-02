import re
from spiral import ronin
from typeDefs import (
    AbbreviationsDictionary,
)

# abbreviation then definition:
# looks for a capitalized abbreviation, then any whitespace or ":" or "(",
# then looks for any alphanumeric chars until ")" or EOL or EOF.
regExForAbbrThenDef = "([A-Z-]{2,}) *:? *\\(? *((\\w| |-)+)(\\)|\n|$)"

# definition then abbreviation:
# opposite of the above. Looks for any words that are followed by an
# abbreviation in parathenses.
# TODO: this is too greedy, it grabs the entire paragraph before the definition
regExForDefThenAbbr = "((\\w| |-)+) *\\( *([A-Z]{2,}) *(\\)|\n|$)"


def createDomainSpecificAbbreviationDictionary(
    allComments: set[str],
) -> AbbreviationsDictionary:
    """
    loops thru all the comments that were parsed and all
    documentation files, and tries to identifiy abbreviation
    definitions.
    """
    abbreviations: dict[str, list[str]] = {}

    def storeMatch(_abbr: str, definition: str):
        abbr = _abbr.lower()
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
