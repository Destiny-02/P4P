from .abbreviations import isAbbreviated

# reëxport all validators
# this works since they all have the same method signature
validators = [isAbbreviated]
