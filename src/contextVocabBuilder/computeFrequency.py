def computeFrequency(listOfTerms: list[str]) -> dict[str, float]:
    """
    given a list of terms, this will return a dict containing
    the frequency of each term, as a percentage (0 to 1)

    The dict is not ordered.
    """
    frequencies: dict[str, float] = {}

    # start by storing the totals
    for term in listOfTerms:
        if term not in frequencies:
            frequencies[term] = 1
        else:
            frequencies[term] += 1

    # now convert the totals to a percentage
    for term in frequencies:
        frequencies[term] /= len(listOfTerms)

    return frequencies
