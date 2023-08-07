from .abbreviations import isAbbreviated
from .abbreviatedExceptions import isAbbreviatedException
from .misspellings import isMisspelling
from .synonyms import isSynonym

# reëxport all validators
# this works since they all have the same method signature
validators = [
    isAbbreviated,
    isAbbreviatedException,
    isMisspelling,
    isSynonym,
]
