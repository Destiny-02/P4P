from typing import TypedDict, Literal


class TermToDetermine(TypedDict):
    """
    represents what's saved as a CSV row
    """

    term: str
    userClassification: Literal[""] | Literal["c"]
    frequencyInDocument: int
    tfidf: float
    freqScore: float
    tfidfScore: float
    isCapitalised: bool
    isAbbrevOrAcronym: bool
    contextLikelihoodScore: float
