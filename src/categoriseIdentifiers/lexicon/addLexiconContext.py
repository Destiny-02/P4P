from os import path
import json
import nltk
from nltk.corpus import wordnet
from ..typeDefs import LexiconContext

nltk.download("wordnet")

wordnetToQId: dict[str, list[str]] = json.load(
    open(
        path.join(path.dirname(__file__), "../../../data/downloaded/wordnetIDs.json"),
        encoding="utf8",
        mode="r",
    )
)


def addLexiconContext(word: str, shouldRunExpensiveChecks: bool) -> LexiconContext:
    """
    given a word, this function determines its POS and wordnet IDs
    """

    posTypes: set[str] = set()
    wordnetIds: set[str] = set()
    qIds: set[str] = set()

    output: LexiconContext = {
        "posTypes": posTypes,
        "wordnetIds": wordnetIds,
        "qIds": qIds,
    }

    if not shouldRunExpensiveChecks:
        # abort immediately, since we don't need this
        # context info
        return output

    for lemma in wordnet.lemmas(word):
        synset = lemma.synset()
        pos = synset.pos()

        wordnetId = f"{synset.offset()}-{pos}"
        qId = wordnetToQId[wordnetId] if wordnetId in wordnetToQId else None

        posTypes.add(pos)
        wordnetIds.add(wordnetId)
        if qId:
            qIds.update(qId)

    return output
