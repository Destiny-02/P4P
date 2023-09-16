# Analyse Codebases

This folder contains functions which can be used to record or calculate different things about the codebases.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

## Find the number of identifiers that are design, context or neither

1. Uncomment these 2 lines. Ensure only these lines are uncommented inside the `if __name__ == "__main__":` block.

```python
(designCounts, contextCounts, neitherCounts, totalCounts) = dcnCountsIdentifiers("your-domain-name")
writeResultsToCsv(designCounts, contextCounts, neitherCounts, getPath("tool-results.csv"))
```

2. From the repo root, run the script with `python -m src.analyseCodebases`.
3. The results will be written to `tool-results.csv`.

## Find the number of terms that are design, context or neither

1. Uncomment these 2 lines. Ensure only these lines are uncommented inside the `if __name__ == "__main__":` block.

```python
(designCounts, contextCounts, neitherCounts, totalCounts) = dcnCountsTerms("your-domain-name")
writeResultsToCsv(designCounts, contextCounts, neitherCounts, getPath("tool-results.csv"))
```

2. From the repo root, run the script with `python -m src.analyseCodebases`.
3. The results will be written to `tool-results.csv`.

## Find the lexical agreement of the codebases

1. Uncomment these lines. Ensure only these lines are uncommented inside the `if __name__ == "__main__":` block.

```python
"""
Find the LA (takes a while to run)
"""
# LA for small and large codebases
(smallRepoPaths, largeRepoPaths, threshold) = splitRepoPathsByNumIdentifiers(findRepoPaths(getPath(DATA_FOLDER + "your-domain-name")))
(smallDesignVocabs, smallContextVocabs, smallNeitherVocabs) = findVocabsForLA(smallRepoPaths, "your-domain-name")
(largeDesignVocabs, largeContextVocabs, largeNeitherVocabs) = findVocabsForLA(largeRepoPaths, "your-domain-name")

print("Median number of identifiers: {}".format(threshold))

print("Small Design LA: {:.0%}".format(findLA(smallDesignVocabs)))
print("Small Context LA: {:.0%}".format(findLA(smallContextVocabs)))
print("Small Neither LA: {:.0%}".format(findLA(smallNeitherVocabs)))

print("Large Design LA: {:.0%}".format(findLA(largeDesignVocabs)))
print("Large Context LA: {:.0%}".format(findLA(largeContextVocabs)))
print("Large Neither LA: {:.0%}".format(findLA(largeNeitherVocabs)))

# LA for all codebases
(designVocabs, contextVocabs, neitherVocabs) = findVocabsForLA(findRepoPaths(getPath(DATA_FOLDER + "your-domain-name")), "your-domain-name")
print("Design LA: {:.0%}".format(findLA(designVocabs)))
print("Context LA: {:.0%}".format(findLA(contextVocabs)))
print("Neither LA: {:.0%}".format(findLA(neitherVocabs)))
```

2. From the repo root, run the script with `python -m src.analyseCodebases`.
3. The results will be printed to the console.
