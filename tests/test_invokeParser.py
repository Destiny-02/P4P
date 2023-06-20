import os
import sys
import pytest

# To fix import errors
project_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
sys.path.insert(0, project_dir)

from src.helper.invokeParser import invokeParser


# flattens a double array
def flat(array: list[list[str]]) -> list[str]:
    return [item for sublist in array for item in sublist]


def conditionalDecorator(decorator, condition: bool):
    """
    If `condition` is true, the decorator is applied.
    This allows us to conditionally invoke `pytest.mark.skip`
    """
    return lambda f: decorator(f) if condition else f


# this is by far the slowest test, since it invokes the parser to
# test that the whole pipeline actually works
#
# this test should be skipped when running locally, it only needs to
# run in the CI.
@conditionalDecorator(pytest.mark.skip, "CI" not in os.environ)
def test_invokeParser():
    currentFolder = os.path.dirname(os.path.abspath(__file__))
    sampleFilesFolder = os.path.join(currentFolder, "../src/parsers/tests")

    # too keep this test simple, only look at a few files from the sample folder.
    # the rest are covered by other tests
    sampleFiles = ["Sample.java", "sample.ts"]

    sampleFilesWithAbsolutePath = [
        os.path.join(sampleFilesFolder, fileName) for fileName in sampleFiles
    ]

    print(sampleFiles)
    (identifiers, comments) = invokeParser(set(sampleFilesWithAbsolutePath))

    assert identifiers == set(
        [
            "ENUM_MEMBER_1",
            "ENUM_MEMBER_2",
            "Sample",
            "a",
            "b",
            "x",
            "y",
            "z",
            "myInterfaceProperty",
            "myCustomAttribute",
            "SomeClass",
            "MyEnum",
            "paye_tax_bracket",
            "MyIFace",
            "MyType",
            "employees",
            "get_payslip_for_paye",
            "get_annual_income_deduction",
        ]
    )
    assert comments == set(
        [
            " @ts-nocheck -- this is a sample file, not part of the sourcecode",
            " another comment",
            " eslint-disable ",
            " extra: TS only",
            "* a comment  ",
            "/**\n   * Sample is a class\n   */",
            "// import from another package",
            "// import from our own package",
            "// this is a comment",
        ]
    )
