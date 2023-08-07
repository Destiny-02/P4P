# Vocabulary Builder

This folder contains a script, which can be used to help build the context vocabularies from a piece of descriptive text about the software.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` directory
- Ensure that `context.txt` is empty unless you want to add to the existing context vocabulary.
- Ensure that the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

## Build the context terms from a piece of descriptive text

- Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveTermsToBeDetermined(getPath("../../data/chess/domain-description.md"), "chess")
```

- Replace the first argument with the path to the descriptive text.
- Replace the second argument with the name of the folder containing the codebases and text files i.e. `your-domain-name` (referenced above).
- From the repo root, run the script with `python -m src.contextVocabBuilder`.
- `to-determine.csv` will be created in this directory
- For the terms that you want to add to the context vocabulary, write a `c` in the 2nd column and save the file.
- The terms are sorted by the number of occurrences in the descriptive text. This number is shown in the 3rd column. We recommend that you start with the most frequent terms and stop when you notice that the terms start to become less relevant to the domain.
- Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveDomainSheetToTxt("chess")
```

- Replace the first argument with the name of the name of the folder containing the codebases and text files
- From the repo root, run the script with `python -m src.contextVocabBuilder`.
- The terms that you indicated with a `c` in `to-determine.csv` will be added to the context vocabulary in `context.txt`.
