from .__main__ import categoriseIdentifiers
from .typeDefs import Severity
from ..helper.invokeParser import IdentifersWithContext
from ..helper.parserTypes import ParsedEntityContext

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
allComments = set(
    # the system should parse the abbreviation definition from this comment
    ["This function parses GTFS (Google Transit Feed Specification) data"]
)


def test_validators():
    identifiers: IdentifersWithContext = {
        "getGtfsModeOfTransport": [context1],
        "currRouteConnexAct": [context1, context2],
        "postPlan": [context2],
    }
    x = categoriseIdentifiers(identifiers, contextWords, designWords, allComments, True)
    assert x == [
        {
            "identifier": "getGtfsModeOfTransport",
            "components": [
                {
                    "category": "neither",
                    "word": "get",
                    "diagnostics": [
                        {
                            "issueType": "unrecognised",
                            "severity": Severity.INFO,
                            "suggestion": "“get” was not recognised",
                        }
                    ],
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
                    "word": "gtfs",
                    "diagnostics": [
                        {
                            "issueType": "abbreviation",
                            "severity": Severity.INFO,
                            "suggestion": "“gtfs” appears to be an abbreviation for “Google Transit Feed Specification”.",
                        }
                    ],
                    "metadata": {"posTypes": set(), "qIds": set(), "wordnetIds": set()},
                    "relevanceToContext": {"lch": None, "path": None, "wup": None},
                    "relevanceToDesign": {"lch": None, "path": None, "wup": None},
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
                    "diagnostics": [
                        {
                            "issueType": "unrecognised",
                            "severity": Severity.INFO,
                            "suggestion": "“mode” was not recognised",
                        }
                    ],
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
                            "suggestion": "“curr” appears to be an abbreviation for “current”.",
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
                    "diagnostics": [
                        {
                            "issueType": "misspelling",
                            "severity": Severity.WARNING,
                            "suggestion": "“connex” appears to be misspelt, did you mean “conned”?",
                        }
                    ],
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
        {
            "identifier": "postPlan",
            "components": [
                {
                    "word": "post",
                    "category": "neither",
                    "metadata": {
                        "posTypes": set(["v", "n"]),
                        "qIds": set(),
                        "wordnetIds": set(
                            [
                                "1031109-v",
                                "1031256-v",
                                "1088923-v",
                                "11243562-n",
                                "11243720-n",
                                "11243907-n",
                                "1570403-v",
                                "1591476-v",
                                "1591621-v",
                                "1960105-v",
                                "2233195-v",
                                "2385634-v",
                                "2473331-v",
                                "318186-n",
                                "3763403-n",
                                "3988170-n",
                                "586262-n",
                                "6264398-n",
                                "7257815-n",
                                "8463063-n",
                                "8624385-n",
                                "991683-v",
                                "999715-v",
                            ]
                        ),
                    },
                    "relevanceToContext": {
                        "lch": (59.31, "trip"),
                        "path": (4.76, "trip"),
                        "wup": (9.09, "trip"),
                    },
                    "relevanceToDesign": {
                        "lch": (69.31, "transportation/shipping/transport"),
                        "path": (5.26, "transportation/shipping/transport"),
                        "wup": (10.0, "transportation/shipping/transport"),
                    },
                    "diagnostics": [
                        {
                            "issueType": "synonym",
                            "severity": Severity.WARNING,
                            "suggestion": "“post” appears to be a synonym for the more well known term “station”.",
                        }
                    ],
                },
                {
                    "word": "plan",
                    "category": "neither",
                    "metadata": {
                        "posTypes": set(["n", "v"]),
                        "qIds": set(),
                        "wordnetIds": set(
                            [
                                "1638368-v",
                                "1639714-v",
                                "3954199-n",
                                "5728678-n",
                                "5898568-n",
                                "704690-v",
                                "705227-v",
                            ]
                        ),
                    },
                    "relevanceToContext": {
                        "lch": (69.31, "trip"),
                        "path": (5.26, "trip"),
                        "wup": (10.0, "trip"),
                    },
                    "relevanceToDesign": {
                        "lch": (80.44, "transportation/shipping/transport"),
                        "path": (5.88, "transportation/shipping/transport"),
                        "wup": (11.11, "transportation/shipping/transport"),
                    },
                    "diagnostics": [
                        {
                            "issueType": "unrecognised",
                            "severity": Severity.INFO,
                            "suggestion": "“plan” was not recognised",
                        }
                    ],
                },
            ],
            "sourceLocations": [context2],
        },
    ]


def test_abbreviatedExceptions():
    identifiers: IdentifersWithContext = {
        "nase": [
            {
                "fileName": "someFile.java",
                "startOffset": 1,
                "endOffset": 2,
                "typeInformation": {"typeName": "NegativeArraySizeException"},
            }
        ],
        "sioobex": [
            {
                "fileName": "anotherFile.java",
                "startOffset": 1,
                "endOffset": 2,
                "typeInformation": {"typeName": "StringIndexOutOfBoundsException"},
            }
        ],
    }
    result = categoriseIdentifiers(identifiers, set(), set(), set(), True)
    assert len(result) == 2  # two identifiers

    assert len(result[0]["components"]) == 1  # first identifier comprises of 1 word
    assert result[0]["components"][0].get("diagnostics") == [
        {
            "issueType": "abbreviatedException",
            "severity": Severity.INFO,
            "appliesToTheseLocationsOnly": set(["someFile.java:1:2"]),
            "suggestion": "“nase” appears to be an abbreviation of its type (“NegativeArraySizeException”).",
        }
    ]

    assert len(result[1]["components"]) == 1  # second identifier comprises of 1 word
    assert result[1]["components"][0].get("diagnostics") == [
        {
            "issueType": "abbreviatedException",
            "severity": Severity.INFO,
            "appliesToTheseLocationsOnly": set(["anotherFile.java:1:2"]),
            "suggestion": "“sioobex” appears to be an abbreviation of its type (“StringIndexOutOfBoundsException”).",
        },
    ]
