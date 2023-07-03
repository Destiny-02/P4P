import os
from .stem import stem_file, stem_set


def test_stem_set():
    # Test set with single word items
    input = {"cats", "dogs", "houses"}
    expected_output = {"cat", "dog", "hous"}
    assert stem_set(input) == expected_output

    # Test set with multi-word items
    input = {"black cats", "brown dogs", "red houses"}
    expected_output = {"black", "cat", "brown", "dog", "red", "hous"}
    assert stem_set(input) == expected_output


def test_stem_file():
    # Test with a file containing 2/3 terms that are not stemmed
    input_file = "test_input.txt"
    output_file = "test_output.txt"
    with open(input_file, "w") as f:
        f.write("run\nswimming\njumping\n")
    stem_file(input_file, output_file)
    with open(output_file, "r") as f:
        assert f.read() == "jump\nrun\nswim\n"

    # Test with a file containing multiple occurrences of the same term
    with open(input_file, "w") as f:
        f.write("running\nrunning\nrunning\n")
    stem_file(input_file, output_file)
    with open(output_file, "r") as f:
        assert f.read() == "run\n"

    # Test with a file containing terms with different cases, leading/trailing spaces and multiple words on the same line
    with open(input_file, "w") as f:
        f.write("  Running  \n  Swimming jumping  \n")
    stem_file(input_file, output_file)
    with open(output_file, "r") as f:
        assert f.read() == "jump\nrun\nswim\n"

    # Clean up the test files
    os.remove(input_file)
    os.remove(output_file)
