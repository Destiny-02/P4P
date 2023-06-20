from os import path
import sys

# To fix import errors
sys.path.insert(0, path.abspath(path.join(path.dirname(__file__), "..")))

from src.categoriseIdentifiers.lexicon.getSynonyms import getSynonyms


def test_synonyms():
    assert getSynonyms("route") == ["path", "route", "itinerary", "road"]
