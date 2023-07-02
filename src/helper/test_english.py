import os
import pytest
from .english import fixUSSpelling, fixSpelling

def test_fixUSSpelling():
    assert fixUSSpelling("abc") == "abc" # Less than 5 characters
    assert fixUSSpelling("chicken") == "chicken" # Correct spelling
    assert fixUSSpelling("fosjepfosjeopfjse") == "fosjepfosjeopfjse" # Not a word
    assert fixUSSpelling("chickennuggets") == "chickennuggets" # Not a word (is two words)
    assert fixUSSpelling("fnfex") == "fnfex" # Not a word
    assert fixUSSpelling("color") == "colour" # US spelling
    assert fixUSSpelling("traveling") == "travelling" # US spelling
    assert fixUSSpelling("travelling") == "travelling" # UK spelling

def test_fixSpelling():
    assert fixSpelling("thr") == "thr" # Less than 5 characters
    assert fixSpelling("chicken") == "chicken" # Correct spelling
    assert fixSpelling("fosjepfosjeopfjse") == "fosjepfosjeopfjse" # Not a word
    assert fixSpelling("chickennuggets") == "chicken nuggets" # Misspelt word (is two words)
    assert fixSpelling("fnfex") == "fedex" # Detected as misspeling, converts to lowercase

@pytest.mark.skipif("CI" in os.environ, reason="fails in the CI's linux env")
def test_fixSpellingIntermittent():
    # This test case is for words that hunspell (on windows)
    # treats differently to libenchant (on *nix systems).
    assert fixSpelling("color") == "colour" # US spelling