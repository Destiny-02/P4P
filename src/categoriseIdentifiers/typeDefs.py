from enum import Enum
from typing import TypedDict
from typing_extensions import NotRequired
from ..helper.parserTypes import ParsedEntityContext


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


class LexiconContext(TypedDict):
    """
    lexicographical context about a given word
    """

    # every possible part-of-speech that the word could belong to
    posTypes: set[str]
    # every wordnet ID for that exact word
    wordnetIds: set[str]
    # every QID for that exact word
    qIds: set[str]


class Similarity(TypedDict):
    """similarity scores from various algorithms"""

    path: tuple[float, str] | None
    lch: tuple[float, str] | None
    wup: tuple[float, str] | None


class CategorisedWord(TypedDict):
    """an interface"""

    # the original word
    word: str

    # design | context | neither
    category: str

    # the remaining properties are only included for neither-terms
    metadata: NotRequired[LexiconContext]
    relevanceToDesign: NotRequired[Similarity]
    relevanceToContext: NotRequired[Similarity]

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


class GlobalValidatorContext(TypedDict):
    """
    an object that's passed to each validator and shared globally.
    Each validator can store arbitrary data.
    """

    domainSpecificAbbreviationDictionary: AbbreviationsDictionary
    synonymMap: dict[str, str]


class ValidatorArguments(TypedDict):
    """
    the keyword arguments (kwargs) passed to each validator function
    """

    word: str
    identifier: str
    context: GlobalValidatorContext
    allComments: set[str]
