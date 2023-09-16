# Context Vocabulary Builder

This folder contains a script which can be used to help build the initial context vocabulary from a piece of descriptive text about the software or domain.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` folder.
- Ensure that `domain-description.md` is in the `../../data/your-domain-name` folder.

## Usage

1. From the repo root, run `python -m src.contextVocabBuilder your-domain-name -c createToDetermine`, where `your-domain-name` is the name of the chosen domain.
2. (Optional) If you want to only consider the terms that are also in a spreadsheet, then append `-o` or `--onlyToCategorise` to the previous command
3. `to-determine.csv` will be created in this folder. The terms are sorted by how likely we think the term is a context term.
4. For the terms that you want to add to the context vocabulary, write a `c` in the 2nd column and save the file.
5. From the repo root, run `python -m src.contextVocabBuilder your-domain-name -c readToDetermine`, replacing `your-domain-name`, where `your-domain-name` is the name of the chosen domain.
6. The terms that you indicated with a `c` in `to-determine.csv` will be added to the context vocabulary in `context.txt`.
