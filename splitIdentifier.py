
"""
Splits an identifier into terms. Handles
camelCase, PascalCase, kebab-case, snake_case, and SCREAMING_SNAKE_CASE,
or a mix of styles
but not all complex situations
"""
def splitIdentifier(_identifier: str) -> list[str]:
  # my first attempt was these regexes:
  #   PascalCase: /[A-Z][a-z]+/g
  #   camelCase: /(^[a-z]|[A-Z])[a-z]+/g
  # which works for simple examples, but it's probably easier to
  # do this in python, not regexes.
  identifier = _identifier


  # start by adding underscores before isolated capital letters,
  # and then lowercasing those letters
  # e.g. partFour -> part_Four -> part_four
  i = 0
  while i < len(identifier):
    char = identifier[i]
    prevIsLowerCase = i > 0 and isLowerCase(identifier[i-1])
    nextIsLowerCase = i + 1 < len(identifier) and isLowerCase(identifier[i+1])
    thisIsUpperCase = isUpperCase(char)

    if thisIsUpperCase and prevIsLowerCase and nextIsLowerCase:
      before = identifier[:i]
      after = identifier[i+1:]
      identifier = before + "_" + char.lower() + after
      # i = 0 # start again from the beginning

    i = i + 1

  return identifier.replace('-', '_').split("_")

# utils go at the bottom of the file in python ? seems kinda weird
# python doesn't care about no-use-before-define

def isLowerCase(char: str) -> bool:
  return char == char.lower() and char != char.upper()

def isUpperCase(char: str) -> bool:
  return char == char.upper() and char != char.lower()
