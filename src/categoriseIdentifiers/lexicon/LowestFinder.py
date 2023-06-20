import math
from typing import Generic, TypeVar

T = TypeVar("T")


class LowestFinder(Generic[T]):
    """a OOP-style helper to find the lowest ranked item of type T"""

    lowestScore = math.inf
    lowestValue: T | None = None

    def add(self, item: T, score: int):
        if score < self.lowestScore:
            self.lowestScore = score
            self.lowestValue = item

    def get(self):
        if not self.lowestValue:
            return None

        scoreAsPercentage = round(100 * self.lowestScore, 2)
        return (scoreAsPercentage, self.lowestValue)
