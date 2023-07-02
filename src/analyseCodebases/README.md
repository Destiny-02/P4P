# Analyse Codebases

This folder contains the `analyseCodebases.py` script, which can be used to measure different things about the codebases.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` directory
- Ensure that the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

## Find the percentage of terms that are design, context or neither

- Uncomment these 2 lines. Ensure only these lines are uncommented inside the `if __name__ == "__main__":` block.

```python
(designCounts, contextCounts, neitherCounts, totalCounts) = main("ugrad-009-01")
  writeResultsToCsv(designCounts, contextCounts, neitherCounts, getPath("tool-results.csv"))
```

- Replace the argument in the call to `main` with the name of the folder containing the codebases and text files i.e. `your-domain-name` (referenced above).
- From the repo root, run the script with `python -m src.analyseCodebases`.
- The results will be written to `tool-results.csv`.

## Find the lexical agreement of the codebases

- Uncomment everything inside the `if __name__ == "__main__":` block except for the first 2 non-comment lines.
- Replace all instances of `ugrad-009-01` with the name of the folder containing the codebases and text files i.e. `your-domain-name` (referenced above).
- From the repo root, run the script with `python -m src.analyseCodebases`.
- The results will be printed to the console.
