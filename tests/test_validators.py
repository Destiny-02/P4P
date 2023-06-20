import os
import sys

# To fix import errors
project_dir = os.path.abspath(
    os.path.join(os.path.dirname(__file__), "../src/categoriseIdentifiers")
)
sys.path.insert(0, project_dir)

from src.categoriseIdentifiers.categoriseIdentifiers import categoriseIdentifiers
from src.categoriseIdentifiers.typeDefs import Severity
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


def test_validators():
    identifiers: IdentifersWithContext = {
        "getModeOfTransport": [context1],
        "currRouteConnexAct": [context1, context2],
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
                    "metadata": {
                        "posTypes": set(["n", "v"]),
                        "qIds": set(),
                        "wordnetIds": set(
                            [
                                "1026975-v",
                                "1066036-v",
                                "1153947-v",
                                "120316-v",
                                "120796-v",
                                "1215421-v",
                                "1401115-v",
                                "1433294-v",
                                "149583-v",
                                "1505958-v",
                                "1565211-v",
                                "1643297-v",
                                "1738107-v",
                                "1771039-v",
                                "1771194-v",
                                "2005948-v",
                                "2006573-v",
                                "2010864-v",
                                "2108026-v",
                                "2108654-v",
                                "2109645-v",
                                "2189398-v",
                                "2208265-v",
                                "2210855-v",
                                "2355596-v",
                                "2359340-v",
                                "345761-v",
                                "522751-v",
                                "54628-v",
                                "567896-n",
                                "589904-v",
                                "622384-v",
                                "639849-v",
                                "65639-v",
                                "770437-v",
                                "87736-v",
                                "94460-v",
                            ]
                        ),
                    },
                    "relevanceToContext": {
                        "lch": (54.65, "tripper/trip"),
                        "path": (4.55, "tripper/trip"),
                        "wup": (8.7, "tripper/trip"),
                    },
                    "relevanceToDesign": {
                        "lch": (59.31, "tape_drive/tape_transport/transport"),
                        "path": (4.76, "tape_drive/tape_transport/transport"),
                        "wup": (9.09, "tape_drive/tape_transport/transport"),
                    },
                },
                {
                    "category": "neither",
                    "word": "mode",
                    "metadata": {
                        "posTypes": set(["n"]),
                        "qIds": set(),
                        "wordnetIds": set(
                            [
                                "13801424-n",
                                "13802634-n",
                                "13923929-n",
                                "4928903-n",
                                "6023675-n",
                                "6861630-n",
                            ]
                        ),
                    },
                    "relevanceToContext": {
                        "lch": (69.31, "tripper/trip"),
                        "path": (5.26, "tripper/trip"),
                        "wup": (10.0, "tripper/trip"),
                    },
                    "relevanceToDesign": {
                        "lch": (74.72, "tape_drive/tape_transport/transport"),
                        "path": (5.56, "tape_drive/tape_transport/transport"),
                        "wup": (10.53, "tape_drive/tape_transport/transport"),
                    },
                    "diagnostics": [],
                },
                # the word "of" was removed
                {"category": "design", "word": "transport"},
            ],
            "sourceLocations": [context1],
        },
        {
            "identifier": "currRouteConnexAct",
            "components": [
                {
                    "word": "curr",
                    "category": "neither",
                    # wordnet knows nothing about this word
                    "metadata": {"posTypes": set(), "qIds": set(), "wordnetIds": set()},
                    "relevanceToContext": {"lch": None, "path": None, "wup": None},
                    "relevanceToDesign": {"lch": None, "path": None, "wup": None},
                    # but we detecterd that it's an abbreviation
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": Severity.INFO,
                            "suggestion": "“curr” appears to be an abbreviation. Consider using “current” instead.",
                        }
                    ],
                },
                {"category": "context", "word": "route"},
                {
                    "word": "connex",
                    "category": "neither",
                    # wordnet also knows nothing about this word
                    "metadata": {"posTypes": set(), "qIds": set(), "wordnetIds": set()},
                    "relevanceToContext": {"lch": None, "path": None, "wup": None},
                    "relevanceToDesign": {"lch": None, "path": None, "wup": None},
                    # connex is not in the dictionary of abbreviations
                    "diagnostics": [],
                },
                {
                    "word": "act",
                    "category": "neither",
                    "metadata": {
                        "posTypes": set(["n", "v"]),
                        "qIds": set(),
                        "wordnetIds": set(
                            [
                                "10435-v",
                                "1095899-v",
                                "13615-v",
                                "1719302-v",
                                "1719921-v",
                                "1721556-v",
                                "2367363-v",
                                "2419073-v",
                                "2525447-v",
                                "2744977-v",
                                "30358-n",
                                "6532095-n",
                                "6892016-n",
                                "7009640-n",
                                "7014029-n",
                            ]
                        ),
                    },
                    "relevanceToContext": {
                        "lch": (80.44, "tripper/trip"),
                        "path": (5.88, "tripper/trip"),
                        "wup": (11.11, "tripper/trip"),
                    },
                    "relevanceToDesign": {
                        "lch": (86.5, "tape_drive/tape_transport/transport"),
                        "path": (6.25, "tape_drive/tape_transport/transport"),
                        "wup": (11.76, "tape_drive/tape_transport/transport"),
                    },
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": Severity.WARNING,
                            # it really doesn't like this one
                            "suggestion": "“act” is a vauge abbreviation. Consider using “action” or “active” or “actual” instead.",
                        }
                    ],
                },
            ],
            "sourceLocations": [context1, context2],
        },
    ]
