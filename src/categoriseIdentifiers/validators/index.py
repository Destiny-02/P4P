from .abbreviations import isAbbreviated
from .synonyms import isSynonym

# reëxport all validators
# this works since they all have the same method signature
validators = [isAbbreviated, isSynonym]
