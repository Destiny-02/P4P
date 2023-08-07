from .abbreviatedExceptions import isAbbreviationOf


def test_validAbbreviatedExceptions():
    assert isAbbreviationOf("ffcmex", "FormatFlagsConversionMismatchException")
    assert isAbbreviationOf("ffcmEx", "FormatFlagsConversionMismatchException")
    assert isAbbreviationOf("ffcmErr", "FormatFlagsConversionMismatchException")
    assert isAbbreviationOf("kyle", "KernelYieldLossException")
    assert isAbbreviationOf("index", "InvalidNativeDragException")
    assert isAbbreviationOf("iddoex", "InvalidDnDOerationException")


def test_invalidAbbreviatedExceptions():
    assert not isAbbreviationOf("fnfex", "FormatFlagsConversionMismatchException")
    assert not isAbbreviationOf("e", "Exception")
    assert not isAbbreviationOf("err", "Exception")
    assert not isAbbreviationOf("error", "Exception")
    assert not isAbbreviationOf("ex", "Exception")
