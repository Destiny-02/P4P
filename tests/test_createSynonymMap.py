from os import path
import sys

# To fix import errors
sys.path.insert(0, path.abspath(path.join(path.dirname(__file__), "..")))

from src.categoriseIdentifiers.helpers.createSynonymMap import createSynonymMap


def test_createSynonymMap():
    schemaTerms = set(["station", "battery"])
    assert createSynonymMap(schemaTerms) == {
        # synonyms for station:
        "place": "station",
        "post": "station",
        "send": "station",
        # synonyms for battery:
        "assault_and_battery": "battery",
        "barrage": "battery",
        "barrage_fire": "battery",
        "bombardment": "battery",
        "electric_battery": "battery",
        "shelling": "battery",
        "stamp_battery": "battery",
    }
