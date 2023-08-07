from typing import TypedDict
from typing_extensions import NotRequired


class TypeInformation(TypedDict):
    """
    interface representing type information for an indentifier
    """

    typeName: str
    argumentTypes: NotRequired[list[str]]
    returnType: NotRequired[str]
    modifiers: NotRequired[list[str]]
    classification: NotRequired[str]


class ParsedEntityContext(TypedDict):
    """
    interface representing the context of an extracted identifier or comment
    """

    startOffset: int
    endOffset: int
    fileName: str
    typeInformation: NotRequired[TypeInformation]
