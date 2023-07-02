from .abbreviations import isAbbreviated
from .misspellings import isMisspelling
from .synonyms import isSynonym

# reëxport all validators
# this works since they all have the same method signature
validators = [
    isAbbreviated,
    isMisspelling,
    isSynonym,
]
