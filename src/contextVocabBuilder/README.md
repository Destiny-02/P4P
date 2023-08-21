# Vocabulary Builder

This folder contains a script, which can be used to help build the context vocabularies from a piece of descriptive text about the software.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` directory
- Ensure that `context.txt` is empty unless you want to add to the existing context vocabulary.
- Ensure that the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

## Build the context terms from a piece of descriptive text

- Create a folder in `../data` for your domain
- Within that folder, create a file called `domain-description.md`
- From the repo root, run `python -m src.contextVocabBuilder your-domain-name -c createToDetermine`, replacing `your-domain-name` with the chosen name (referenced above).
- (Optional) If you want to only consider the terms that are also in a spreadsheet, then append `-o` or `--onlyToCategorise` to the previous command
- `to-determine.csv` will be created in this directory
- For the terms that you want to add to the context vocabulary, write a `c` in the 2nd column and save the file.
- The terms are sorted by how likely we think the term is a context term.
- From the repo root, run `python -m src.contextVocabBuilder your-domain-name -c readToDetermine`, replacing `your-domain-name` with the chosen name (referenced above).
- The terms that you indicated with a `c` in `to-determine.csv` will be added to the context vocabulary in `context.txt`.
