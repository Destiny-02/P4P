from spiral import ronin
from unidecode import unidecode


def splitIdentifier(_identifier: str, stripDiacritics=True) -> list[str]:
    """
    Splits an identifier into terms. Handles
    camelCase, PascalCase, kebab-case, snake_case, and SCREAMING_SNAKE_CASE,
    or a mix of styles
    but not all complex situations
    """
    identifier = unidecode(_identifier) if stripDiacritics else _identifier
    return ronin.split(identifier)


def splitIdentifiers(identifiers: set[str]) -> set[str]:
    """
    Splits a set of identifiers into terms
    """
    terms = set()
    for identifier in identifiers:
        terms.update(splitIdentifier(identifier))
    return terms
