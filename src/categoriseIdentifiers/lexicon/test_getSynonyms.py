from .getSynonyms import getSynonyms


def test_synonyms():
    assert getSynonyms("route") == ["path", "route", "itinerary", "road"]
