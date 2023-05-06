import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.tool_helper.stats import findLA, getDVInSet

def test_no_matching_terms():
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "world", "foo", "bar"}
    assert getDVInSet(domain_terms, words) == set()

def test_some_matching_terms():
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "apple", "world", "foo", "bar", "banana"}
    assert getDVInSet(domain_terms, words) == {"apple", "banana"}