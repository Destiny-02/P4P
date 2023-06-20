from typing import TypeVar

T = TypeVar("T")


def flatten(array: list[list[T]]) -> list[T]:
    """flattens a double array e.g. T[][] -> T[] """
    return [item for sublist in array for item in sublist]
