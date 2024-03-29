import os
import pytest
from .invokeParser import invokeParserWithMetadata


# this is by far the slowest test, since it invokes the parser to
# test that the whole pipeline actually works
#
# this test should be skipped when running locally, it only needs to
# run in the CI.
@pytest.mark.skipif("CI" not in os.environ, reason="slow test, CI-only")
def test_invokeParser():
    currentFolder = os.path.dirname(os.path.abspath(__file__))
    sampleFilesFolder = os.path.join(currentFolder, "../parsers/tests")

    # too keep this test simple, only look at a few files from the sample folder.
    # the rest are covered by other tests
    sampleFiles = ["Sample.java", "sample.ts"]

    sampleFilesWithAbsolutePath = [
        os.path.join(sampleFilesFolder, fileName) for fileName in sampleFiles
    ]

    print(sampleFiles)
    (identifiers, comments) = invokeParserWithMetadata(set(sampleFilesWithAbsolutePath))

    assert set(identifiers.keys()) == set(
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
            "SomeClass",
            "MyEnum",
            "paye_tax_bracket",
            "MyIFace",
            "MyType",
            "employees",
            "get_payslip_for_paye",
            "get_annual_income_deduction",
            "arg1",
            "arg2",
            "spreadArguments",
            "add",
            "i",
            "myFirstArg",
            "myOtherFunction",
            "secondArg",
            "spread",
            "threadId",
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
            "// these are all globals that should not be considered",
            " these are all globals that should not be considered",
            " no type argument for the first parameter",
        ]
    )
