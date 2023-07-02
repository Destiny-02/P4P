from ..lexicon.getSynonyms import getSynonyms


def createSynonymMap(schemaTerms: set[str]):
    """
    create a map of all possible synonyms
    """
    synonymMap: dict[str, str] = {}

    for schemaTerm in schemaTerms:
        for synonym in getSynonyms(schemaTerm):
            # if there are two schema terms which are synonyms,
            # don't add them to the list
            if synonym not in schemaTerms:
                synonymMap[synonym] = schemaTerm

    return synonymMap
