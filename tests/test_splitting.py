import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.helper.splitting import splitIdentifier, isLowerCase, isUpperCase

def test_splitIdentifier():
    assert isLowerCase('a') is True
    assert isLowerCase('ā') is True
    assert isLowerCase('A') is False
    assert isLowerCase('Ā') is False
    assert isLowerCase('_') is False

    assert isUpperCase('a') is False
    assert isUpperCase('ā') is False
    assert isUpperCase('A') is True
    assert isUpperCase('Ā') is True
    assert isUpperCase('_') is False

    assert splitIdentifier("camelCase") == ["camel", "case"]
    assert splitIdentifier("PascalCase") == ["Pascal", "case"]
    assert splitIdentifier("snake_case") == ["snake", "case"]
    assert splitIdentifier("SCREAMING_SNAKE_CASE") == ["SCREAMING", "SNAKE", "CASE"]
    assert splitIdentifier("kebab-case") == ["kebab", "case"]
    assert splitIdentifier("UNSAFE_componentWillReceiveProps") == ['UNSAFE', 'component', 'will', 'receive', 'props']
    assert splitIdentifier("MIX_OfStyles-isWeird") == ["MIX", "Of", "styles", "is", "weird"]
    assert splitIdentifier("macronsÄndÜmlautsĀreŌkay") == ["macrons", "änd", "ümlauts", "āre", "ōkay"]
    # assert splitIdentifier("getPAYE") == ["get", "PAYE"]
    # assert splitIdentifier("when2meet") == ["when", "2", "meet"]
    # assert splitIdentifier("person2") == ["person", "2"]
    