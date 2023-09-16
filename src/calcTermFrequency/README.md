# Calculate Term Frequency

This folder contains functions which can be used to calculate the number of codebases that use a given term in a vocabulary.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

# Usage

- Example usage can be found in `__main__.py`. Replace the parameters with the name of your domain.
- From the repo root, run the script with `python -m src.calcTermFrequency`.
- The results will be written to a csv specified in the call(s) to `csvToSheet`.
