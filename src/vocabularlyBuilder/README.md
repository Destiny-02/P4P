# Vocabulary Builder

This folder contains the a script, which can be used to build the context and design (and neither) vocabularies from identifiers in codebases.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` directory
- Ensure that the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

## Categorising terms from identifiers in a codebase

- Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveTermsToBeCategorised([getPath("../../data/ugrad-009-01/design1000")], "ugrad-009-01")
```

- Replace the first argument with the path to the codebase.
- Replace the second argument with the name of the folder containing the codebases and text files i.e. `your-domain-name` (referenced above).
- From the repo root, run the script with `python -m src.vocabularlyBuilder`.
- `to-categorise.csv` will be created in this directory
- For the terms that you want to add to the context vocabulary, write a `c` in the 2nd column
- For the terms that you want to add to the design vocabulary, write a `d` in the 2nd column
- For the remaining terms, write an `n` in the 2nd column.
- Save the file.
- Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveCategoriseSheetToTxt("ugrad-009-01")
```

- Replace the first argument with the name of the name of the folder containing the codebases and text files
- The terms that you indicated with a `c`, `d` or `n` in `to-categorise.csv` will be added to the context, design and neither vocabulary in `context.txt`, `design.txt` and `neither.txt`, respectively.

## Categorising terms from identifiers in multiple codebases at once

- Similar process to above, except use the path of the folder containing all the codebases i.e.

```python
saveTermsToBeCategorised(findRepoPaths(getPath("../data/ugrad-009-01/")), "ugrad-009-01")
```

## Re-categorising terms from identifiers in codebase(s)

- Similar process to the above, except include the previous categorisations in `context-answers.txt`, `design-answers.txt` and `neither-answers.txt` in the `../vocabularies/your-domain-name"` directory.
- The call to `saveTermsToBeCategorised` will automatically add the terms that are both in the codebase and in the answers to the vocabulary text files.
- Check the last line of the console output to very whether you need to further categorise any terms.
