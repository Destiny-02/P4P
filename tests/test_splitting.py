import os
import sys
import pytest

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
sys.path.insert(0, project_dir)

from src.helper.splitting import splitIdentifier, isLowerCase, isUpperCase


def test_splitIdentifier():
    assert splitIdentifier("camelCase") == ["camel", "Case"]
    assert splitIdentifier("PascalCase") == ["Pascal", "Case"]
    assert splitIdentifier("snake_case") == ["snake", "case"]
    assert splitIdentifier("SCREAMING_SNAKE_CASE") == ["SCREAMING", "SNAKE", "CASE"]

    assert splitIdentifier("UNSAFE_componentWillReceiveProps") == [
        "UNSAFE",
        "component",
        "Will",
        "Receive",
        "Props",
    ]
    assert splitIdentifier("MIX_OfStyles-isWeird") == [
        "MIX",
        "Of",
        "Styles",
        "-",
        "is",
        "Weird",
    ]
    assert splitIdentifier("getPAYE") == ["get", "PAYE"]
    assert splitIdentifier("when2meet") == ["when", "2", "meet"]
    assert splitIdentifier("person2") == ["person", "2"]
    assert splitIdentifier("awsRoute53Gateway") == ["aws", "Route", "53", "Gateway"]
    assert splitIdentifier("awsRoute53Gateway") == ["aws", "Route", "53", "Gateway"]
    assert splitIdentifier("teReoMāori") == ["te", "Reo", "Maori"]
    assert splitIdentifier("macronsÄndÜmlautsĀreŌkay") == [
        "macrons",
        "And",
        "Umlauts",
        "Are",
        "Okay",
    ]


def test_allLowerCase():
    assert splitIdentifier("derivedcourseoverground") == [
        "derived",
        "course",
        "overground",  # suboptimal, this should be two words
    ]
    assert splitIdentifier("derivedcog") == ["derived", "cog"]


def test_unexpectedAbbreviations():
    assert splitIdentifier("getpayeforpayslip") == [
        "getpayeforpayslip",
    ]
    assert splitIdentifier("getPAYEforpayslip") == ["get", "PAY", "Eforpayslip"]
    assert splitIdentifier("getPAYEForpayslip") == ["get", "PAYE", "For", "payslip"]


def test_unicode():
    # these are genuinely valid in some languages
    # from https://kyle.kiwi/var-names
    assert splitIdentifier("p͢e͢r͢s͢o͢n͢N͢a͢m͢e") == ["person", "Name"]
    assert splitIdentifier("p͢e͢r͢s͢o͢n͢N͢a͢m͢e", False) == ["p͢e͢r͢s͢o͢n͢N͢a͢m͢e"]
    assert splitIdentifier("Δt") == ["Dt"]  # some meaning is lost here
    assert splitIdentifier("Δt", False) == ["Δt"]
    assert splitIdentifier("中国变量") == ["Zhong", "Guo", "Bian", "Liang"]


def test_kebab_case():
    # this library does some wizardry to split kebab case, sometimes
    # it splits, sometimes it doesn't 😅
    assert splitIdentifier("kebab-case") == ["kebab-case"]
    assert splitIdentifier("fast-moving") == ["fast", "-", "moving"]

    # this is a bit inconsistent...
    assert splitIdentifier("connection-to") == ["connection", "-", "to"]
    assert splitIdentifier("connection-to-the") == ["connection-to-the"]

    assert splitIdentifier("coordinate") == ["coordinate"]
    assert splitIdentifier("co-ordinate") == ["co-ordinate"]
    assert splitIdentifier("coördinate") == ["coordinate"]


@pytest.mark.skip(reason="bug in the spiral library...")
def test_complex_examples():
    # ideally the lib would support this without normalizing diacritics
    assert splitIdentifier("macronsÄndÜmlautsĀreŌkay", False) == [
        "macrons",
        "änd",
        "ümlauts",
        "āre",
        "ōkay",
    ]
