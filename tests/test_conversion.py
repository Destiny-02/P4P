import os
import sys

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.insert(0, project_dir)

from src.helper.conversion import txtToSet, jsonToSet, txtToSetWithEquivalents, convertEquivalents, setToStemmedSet, extractWords, extractWordsFromSet, cleanSetOfTerms

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

def test_extractWords():
  assert extractWords("Hello, World!") == ["Hello", "World"]
  assert extractWords("This is a sentence.") == ["This", "is", "a", "sentence"]
  assert extractWords("    Extra spaces     ") == ["Extra", "spaces"]
  assert extractWords("Ça va bien?") == ["Ça", "va", "bien"]
  assert extractWords("La vie est belle.") == ["La", "vie", "est", "belle"]
  assert extractWords("C'est l'été.") == ["C", "est", "l", "été"]
  assert extractWords("¿Qué tal?") == ["Qué", "tal"]
  assert extractWords("Le café coûte 2,50€.") == ["Le", "café", "coûte"]

def test_extractWordsFromSet():
  # Test with empty set
  assert extractWordsFromSet(set()) == set()

  # Test with single-word strings
  assert extractWordsFromSet({"hello", "world"}) == {"hello", "world"}
  assert extractWordsFromSet({"foo", "bar", "baz"}) == {"foo", "bar", "baz"}

  # Test with multi-word strings
  assert extractWordsFromSet({"hello, world", "foo bar baz"}) == {"hello", "world", "foo", "bar", "baz"}
  assert extractWordsFromSet({"This is a sentence.", "La vie est belle."}) == {"This", "is", "a", "sentence", "La", "vie", "est", "belle"}

  # Test with strings containing numbers and special characters
  assert extractWordsFromSet({"abc123", "foo.bar.baz"}) == {"abc", "foo", "bar", "baz"}
  assert extractWordsFromSet({"extra!@#spaces$%^&*"}) == {"extra", "spaces"}

def test_cleanSetOfTerms():
  data = set(['apple', 'banana', 'Cherry', 'DURIAN', ''])
  expected = set(['apple', 'banana', 'cherry', 'durian'])
  assert cleanSetOfTerms(data) == expected

  data = set(['Spam', 'spam', 'SPAM', '   Eggs   ', '  ', 'BACON'])
  expected = set(['spam', 'eggs', 'bacon'])
  assert cleanSetOfTerms(data) == expected

  data = set(['flower', 'FLOWER', '   ', 'Leaf', 'lEAF'])
  expected = set(['flower', 'leaf'])
  assert cleanSetOfTerms(data) == expected

  data = set(['Hello', 'there', 'MY', 'name', 'is', 'Iñtërnâtiônàlizætiøn'])
  expected = set(['hello', 'there', 'my', 'name', 'is', 'iñtërnâtiônàlizætiøn'])
  assert cleanSetOfTerms(data) == expected
