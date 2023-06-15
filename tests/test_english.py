import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.helper.english import fixSpelling

def test_fixSpelling():
    assert fixSpelling("abc") == "abc" # Less than 5 characters
    assert fixSpelling("chicken") == "chicken" # Correct spelling
    assert fixSpelling("fosjepfosjeopfjse") == "fosjepfosjeopfjse" # No suggestions
    assert fixSpelling("chickennuggets") == "chickennuggets" # Suggestion is two words
    assert fixSpelling("fnfex") == "fnfex" # Suggestion has capital letters (FedEx)
    assert fixSpelling("color") == "colour" # US spelling