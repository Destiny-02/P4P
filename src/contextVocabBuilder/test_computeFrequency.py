from .computeFrequency import computeFrequency


def test_computeFrequency():
    assert computeFrequency([]) == {}
    assert computeFrequency(["a"]) == {"a": 1}
    assert computeFrequency(["a", "b", "c"]) == {"a": 1 / 3, "b": 1 / 3, "c": 1 / 3}
    assert computeFrequency(["a", "b", "c", "a"]) == {"a": 0.5, "b": 0.25, "c": 0.25}
    assert computeFrequency(["a", "b", "c", "a", "a", "c"]) == {
        "a": 3 / 6,
        "b": 1 / 6,
        "c": 2 / 6,
    }
