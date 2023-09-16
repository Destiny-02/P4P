# Percentage of vocabulary used in codebase

This folder contains a script which can be used to calculate the percentage of vocabulary terms used in each codebase.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

# Usage

- Example usage can be found in `__main__.py`. Replace the parameters with the name of your domain and the desired path of the output file.
- From the repo root, run the script with `python -m src.percentOfVocab`.
- The results will be written to a csv specified in the call(s) to `csvToSheet`.
