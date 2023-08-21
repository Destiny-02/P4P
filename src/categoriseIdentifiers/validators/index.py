from .abbreviations import isAbbreviated
from .abbreviatedExceptions import isAbbreviatedException
from .misspellings import isMisspelling
from .synonyms import isSynonym
from .unrecognised import isUnrecognised

# reëxport all validators
# this works since they all have the same method signature
#
# the order matters, once a validator matches the word, no
# more validators will run.
validators = [
    isAbbreviated,
    isAbbreviatedException,
    isMisspelling,
    isSynonym,
    isUnrecognised,  # must be the last
]
