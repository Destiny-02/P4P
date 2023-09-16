# Repositories Design Vocabulary Builder

This folder contains the scripts for automatically creating an initial design vocabularly. The design vocab is created by analysing common terms in code and common acronyms/abbreviations from a dictionary (the dictionary JSON file should be downloaded).

# Prerequisites

- Download a number of repositories from which the common terms will be extracted. These should be placed in the `repos` folder.
- For a more efficient way of querying for and batch downloading GitHub repositories, use the `repoCloner.py` file. You will need to rename it to `__main__.py` for it to work. Then, run `python -m src.reposDesignVocabBuilder` in the repo root.

# Usage

- From the repo root, run the script with `python -m src.reposDesignVocabBuilder`.
- The results will be written to a text file called `design-terms.txt`.
