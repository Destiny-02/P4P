from typing import TypedDict


class ParsedEntityContext(TypedDict):
    """
    interface representing the context of an extracted identifier or comment
    """

    startOffset: int
    endOffset: int
    fileName: str
