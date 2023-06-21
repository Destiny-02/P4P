import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
sys.path.insert(0, project_dir)

from src.categoriseIdentifiers.categoriseIdentifiers import categoriseIdentifiers
from src.helper.invokeParser import IdentifersWithContext
from src.helper.parserTypes import ParsedEntityContext

# The example in this file uses Google GTFS as the domain
contextWords = set(["rout", "trip", "station"])
designWords = set(["vehicl", "transport"])


# don't really care about this so use the same examples for all identifiers
context1: ParsedEntityContext = {
    "fileName": "example.rb",
    "startOffset": 107,
    "endOffset": 123,
}
context2: ParsedEntityContext = {
    "fileName": "anotherFile.rb",
    "startOffset": 456,
    "endOffset": 469,
}


def test_stem_set():
    identifiers: IdentifersWithContext = {
        "getModeOfTransport": [context1],
        "currDBConnexAct": [context1],
        "setRouteShortName": [context1, context2],
    }
    x = categoriseIdentifiers(identifiers, contextWords, designWords)
    assert x == [
        {
            "identifier": "getModeOfTransport",
            "components": [
                {
                    "category": "neither",
                    "word": "get",
                    "diagnostics": [],
                },
                {"category": "neither", "word": "mode", "diagnostics": []},
                # the word "of" was removed
                {"category": "design", "word": "transport"},
            ],
            "sourceLocations": [context1],
        },
        {
            "identifier": "currDBConnexAct",
            "components": [
                {
                    "word": "curr",
                    "category": "neither",
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": "INFO",
                            "suggestion": "“curr” appears to be an abbreviation. Consider using “current” instead.",
                        }
                    ],
                },
                {
                    "word": "db",
                    "category": "neither",
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": "INFO",
                            "suggestion": "“db” appears to be an abbreviation. Consider using “database” instead.",
                        }
                    ],
                },
                {
                    "word": "connex",
                    "category": "neither",
                    # connex is not in the dictionary of abbreviations
                    "diagnostics": [],
                },
                {
                    "word": "act",
                    "category": "neither",
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": "WARNING",
                            # it really doesn't like this one
                            "suggestion": "“act” is a vauge abbreviation. Consider using “action” or “active” or “actual” instead.",
                        }
                    ],
                },
            ],
            "sourceLocations": [context1],
        },
        {
            "identifier": "setRouteShortName",
            "components": [
                {"category": "neither", "word": "set", "diagnostics": []},
                {"category": "context", "word": "route"},
                {"category": "neither", "word": "short", "diagnostics": []},
                {"category": "neither", "word": "name", "diagnostics": []},
            ],
            "sourceLocations": [
                context1,
                context2,
            ],
        },
    ]
