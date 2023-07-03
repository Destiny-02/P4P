from typing_extensions import Any
import nltk
from nltk.corpus import wordnet
from ..typeDefs import LexiconContext, Similarity
from ...helper.generic import flatten
from .LowestFinder import LowestFinder


nltk.download("wordnet")


def createDetermineRelevanceToSchema(
    schemaTerms: set[str],
):
    """
    determines how relevant a given word is to the domain

    Checks all words from the domain and finds the word that
    is the most similar to the chosen word. The most similar
    word is returned with a score.

    Three different algorithms are used.
    """
    synsetsForSchema: list[Any] = flatten(
        [wordnet.synsets(term) for term in schemaTerms]
    )

    # for efficiency, this is a higher-order function
    def determineRelevanceToSchema(
        word: str,
        _lexiconContext: LexiconContext,
    ) -> Similarity:
        path = LowestFinder[str]()  # Path Similarity
        lch = LowestFinder[str]()  # Leacock-Chodorow Similarity
        wup = LowestFinder[str]()  # Wu-Palmer Similarity
        # TODO: could also consider Encyclopædic Similarity but this is much more complex

        for targetSynset in wordnet.synsets(word):
            for schemaSynset in synsetsForSchema:
                schemaName = "/".join([n.name() for n in schemaSynset.lemmas()])

                # store the scores for each similarity algoritm

                path.add(schemaName, targetSynset.path_similarity(schemaSynset))

                # the LCH algorithm only works if the two words have the same POS
                if schemaSynset.pos() == targetSynset.pos():
                    lch.add(schemaName, targetSynset.lch_similarity(schemaSynset))
                wup.add(schemaName, targetSynset.wup_similarity(schemaSynset))

        return {
            "path": path.get(),
            "lch": lch.get(),
            "wup": wup.get(),
        }

    return determineRelevanceToSchema
