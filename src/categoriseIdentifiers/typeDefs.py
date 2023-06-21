from enum import Enum
from typing import TypedDict
from typing_extensions import NotRequired
from helper.parserTypes import ParsedEntityContext


class Severity(str, Enum):
    """an enum to describe the severity of an issue"""

    # use the standard levels used by VSCode & GitHub.
    ERROR = "ERROR"
    WARNING = "WARNING"
    INFO = "INFO"


class Diagonstics(TypedDict):
    """the output from a validator"""

    issueType: str

    severity: Severity

    suggestion: str


class CategorisedWord(TypedDict):
    """an interface"""

    # the original word
    word: str

    # design | context | neither
    category: str

    diagnostics: NotRequired[list[Diagonstics]]


class CategorisedIdentifier(TypedDict):
    """an interface"""

    # the original identifier
    identifier: str

    # location in the source code
    sourceLocations: list[ParsedEntityContext]

    # an array of each word and it's category+diagnostics
    components: list[CategorisedWord]


class AbbreviationsDictionary(TypedDict):
    """
    defines the JSON structure of the file `abbreviations-dict.json`
    """

    abbreviations: dict[str, list[str]]
    skipIdentifiers: list[str]
    skipWords: list[str]
