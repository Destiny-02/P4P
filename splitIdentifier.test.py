import splitIdentifier as s

# sanity check test that the util functions work
assert s.isLowerCase('a') is True
assert s.isLowerCase('ā') is True
assert s.isLowerCase('A') is False
assert s.isLowerCase('Ā') is False
assert s.isLowerCase('_') is False

assert s.isUpperCase('a') is False
assert s.isUpperCase('ā') is False
assert s.isUpperCase('A') is True
assert s.isUpperCase('Ā') is True
assert s.isUpperCase('_') is False

assert s.splitIdentifier("camelCase") == ["camel", "case"]
assert s.splitIdentifier("PascalCase") == ["Pascal", "case"]
assert s.splitIdentifier("snake_case") == ["snake", "case"]
assert s.splitIdentifier("SCREAMING_SNAKE_CASE") == ["SCREAMING", "SNAKE", "CASE"]
assert s.splitIdentifier("kebab-case") == ["kebab", "case"]
assert s.splitIdentifier("UNSAFE_componentWillReceiveProps") == ['UNSAFE', 'component', 'will', 'receive', 'props']
assert s.splitIdentifier("MIX_OfStyles-isWeird") == ["MIX", "Of", "styles", "is", "weird"]
assert s.splitIdentifier("macronsÄndÜmlautsĀreŌkay") == ["macrons", "änd", "ümlauts", "āre", "ōkay"]


# there's probably a smart way of writing unit tests in python..
print("All tests passed")
