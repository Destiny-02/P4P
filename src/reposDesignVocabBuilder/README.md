# Repositories Design Vocabulary Builder

This folder contains the scripts for creating the initial design vocabularly without any manual intervention. The design vocab is created by analysing common terms in code and common acronyms/abbreviations from a dictionary (the dictionary JSON file should be downloaded).

# Usage

- From the repo root, run the script with `python -m src.reposDesignVocabBuilder`.
- The results will be written to a text file called `design-terms.txt`.

## Repository cloner

- For a more efficient way of querying for and batch downloading GitHub repositories.
- You will need to rename this file to `__main__.py` for it to work. Then, run `python -m src.reposDesignVocabBuilder` in the repo root.
