from .stats import findLA, getDVInSet


def test_getDVInSet():
    # Test with no matching terms
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "world", "foo", "bar"}
    assert getDVInSet(domain_terms, words) == set()

    # Test with some matching terms
    domain_terms = {"apple", "banana", "cherry"}
    words = {"hello", "apple", "world", "foo", "bar", "banana"}
    assert getDVInSet(domain_terms, words) == {"apple", "banana"}


def test_findLA():
    # Test with two identical domain vectors
    dv = set(["apple", "banana", "cherry"])
    assert findLA([dv, dv]) == 1.0

    # Test with two different domain vectors
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["orange", "pear", "grape"])
    assert findLA([dv1, dv2]) == 0.0

    # Test with two overlapping domain vectors
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["banana", "cherry", "durian"])
    assert round(findLA([dv1, dv2]), 2) == 0.67

    # Test with three domain vectors
    dv1 = set(["apple", "banana", "cherry"])
    dv2 = set(["banana", "cherry", "durian"])
    dv3 = set(["cherry", "durian", "elderberry"])
    assert round(findLA([dv1, dv2, dv3]), 2) == 0.56
