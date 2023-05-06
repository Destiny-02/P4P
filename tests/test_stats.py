import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.tool_helper.stats import findLA, getDVInSet

# Tests for getDVInSet
def test_no_matching_terms():
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "world", "foo", "bar"}
    assert getDVInSet(domain_terms, words) == set()

def test_some_matching_terms():
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "apple", "world", "foo", "bar", "banana"}
    assert getDVInSet(domain_terms, words) == {"apple", "banana"}

# Tests for findLA
def test_findLA_twoIdenticalDVs():
    dv = set(["apple", "banana", "cherry"])
    assert findLA([dv, dv]) == 1.0

def test_findLA_twoDifferentDVs():
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["orange", "pear", "grape"])
    assert findLA([dv1, dv2]) == 0.0

def test_findLA_twoOverlappingDVs():
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["banana", "cherry", "durian"])
    assert round(findLA([dv1, dv2]), 2) == 0.67

def test_findLA_threeDVs():
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["banana", "cherry", "durian"])
    dv3 = set(["cherry", "durian", "elderberry"])
    assert round(findLA([dv1, dv2, dv3]), 2) == 0.56
