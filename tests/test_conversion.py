import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.helper.conversion import txtToSet, jsonToSet, txtToSetWithEquivalents, convertEquivalents, setToStemmedSet

def test_txtToSet():
    # Test with a file containing three terms
    filename = "test_terms.txt"
    with open(filename, "w") as f:
        f.write("apple\nbanana\ncherry\n")
    assert txtToSet(filename) == set(["apple", "banana", "cherry"])
    
    # Test with a file containing multiple occurrences of the same term
    filename = "test_repeated_terms.txt"
    with open(filename, "w") as f:
        f.write("apple\nbanana\ncherry\napple\n")
    assert txtToSet(filename) == set(["apple", "banana", "cherry"])
    
    # Test with a file containing no terms
    filename = "test_empty_file.txt"
    with open(filename, "w") as f:
        pass
    assert txtToSet(filename) == set()
    
    # Test with a file containing terms with different cases and leading/trailing spaces
    filename = "test_case_space_terms.txt"
    with open(filename, "w") as f:
        f.write("  Apple  \n  Banana  \n cherry  \n")
    assert txtToSet(filename) == set(["apple", "banana", "cherry"])
    
    # Clean up the test files
    os.remove("test_terms.txt")
    os.remove("test_repeated_terms.txt")
    os.remove("test_empty_file.txt")
    os.remove("test_case_space_terms.txt")

def test_jsonToSet():
  # Test with a file containing one identifier with one term
  filename = "test_single_identifier.json"
  with open(filename, "w") as f:
    f.write('{"identifiers": [{"name": "apple"}]}')
  assert jsonToSet(filename) == set(["apple"])

  # Test with a file containing multiple identifiers with different terms
  filename = "test_multiple_identifiers.json"
  with open(filename, "w") as f:
    f.write('{"identifiers": [{"name": "banana"}, {"name": "cherry_juice"}]}')
  assert jsonToSet(filename) == set(["banana", "cherry", "juice"])

  # Test with a file containing no identifiers
  filename = "test_empty_file.json"
  with open(filename, "w") as f:
    f.write('{"identifiers": []}')
  assert jsonToSet(filename) == set()

  os.remove("test_single_identifier.json")
  os.remove("test_multiple_identifiers.json")
  os.remove("test_empty_file.json")

def test_txtToSetWithEquivalents():
  # Test with a file containing one term
  filename = "test_single_term.txt"
  with open(filename, "w") as f:
    f.write("apple\n")
  data, equivalents = txtToSetWithEquivalents(filename)
  assert data == set(["apple"])
  assert equivalents == {}

  # Test with a file containing multiple terms and equivalents
  filename = "test_multiple_terms.txt"
  with open(filename, "w") as f:
    f.write("apple\nbanana\ncherry\nred apple\nyellow banana\n")
  data, equivalents = txtToSetWithEquivalents(filename)
  assert data == set(["apple", "banana", "cherry"])
  assert equivalents == {"red": "apple", "yellow": "banana"}

  # Test with a file containing no terms
  filename = "test_empty_file.txt"
  with open(filename, "w") as f:
    pass
  data, equivalents = txtToSetWithEquivalents(filename)
  assert data == set()
  assert equivalents == {}

  # Clean up the test files
  os.remove("test_single_term.txt")
  os.remove("test_multiple_terms.txt")
  os.remove("test_empty_file.txt")

def test_convertEquivalents():
    # Test with a set of terms and equivalents
    data = set(["app", "ft", "pear"])
    equivalents = {"app": "apple", "ft": "fruit"}
    expected_output = set(["apple", "fruit", "pear"])
    assert convertEquivalents(data, equivalents) == expected_output
    
    # Test with a set of terms and empty equivalents
    data = set(["apple", "fruit", "pear"])
    equivalents = {}
    expected_output = set(["apple", "fruit", "pear"])
    assert convertEquivalents(data, equivalents) == expected_output
    
    # Test with a set of terms that have no equivalents
    data = set(["apple", "fruit", "pear"])
    equivalents = {"mandarin": "orange"}
    expected_output = set(["apple", "fruit", "pear"])
    assert convertEquivalents(data, equivalents) == expected_output

def test_setToStemmedSet():
    # Test with a set of terms that are not stemmed
    data = set(["apple", "banana", "cherry"])
    expected_output = set(["appl", "banana", "cherri"])
    assert setToStemmedSet(data) == expected_output
    
    # Test with a set of terms that are already stemmed
    data = set(["appl", "banana", "cherri"])
    expected_output = set(["appl", "banana", "cherri"])
    assert setToStemmedSet(data) == expected_output
    
    # Test with an empty set
    data = set()
    expected_output = set()
    assert setToStemmedSet(data) == expected_output
