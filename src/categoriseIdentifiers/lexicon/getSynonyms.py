import nltk
from nltk.corpus import wordnet

nltk.download("wordnet")


def getSynonyms(word: str) -> list[str]:
    """
    given a word, this function returns its synnonyms
    """
    synonyms: list[str] = []
    for synonym in wordnet.synsets(word):
        for lemma in synonym.lemmas():  # type: ignore
            name = lemma.name()
            if name not in synonyms:
                synonyms.append(name)

    return synonyms
