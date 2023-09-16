# Vocabulary Builder

This folder contains a script which can be used to build the context and design (and neither) vocabularies from identifiers in a codebase(s).

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` fold

## Categorising terms from identifiers in a codebase

1. Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveTermsToBeCategorised([getPath("../../data/ugrad-009-01/design1000")], "ugrad-009-01")
```

2. Replace the first argument with the path to the codebase.
3. Replace the second argument with the name of the folder containing the codebases and text files e.g. `your-domain-name`.
4. From the repo root, run the script with `python -m src.vocabularyBuilder`.
5. `to-categorise.csv` will be created in this folder.
6. For the terms that you want to add to the context vocabulary, write a `c` in the 2nd column.
7. For the terms that you want to add to the design vocabulary, write a `d` in the 2nd column.
8. For the remaining terms, write an `n` in the 2nd column.
9. Save the file.
10. Uncomment this line. Ensure only this line in uncommented inside the `if __name__ == "__main__":` block.

```python
saveCategoriseSheetToTxt("ugrad-009-01")
```

11. Replace the first argument with the name of the name of the folder containing the codebases and text files.
12. The terms that you indicated with a `c`, `d` or `n` in `to-categorise.csv` will be added to the context, design and neither vocabulary in `context.txt`, `design.txt` and `neither.txt`, respectively.

## Categorising terms from identifiers in multiple codebases at once

- Similar process to above, except use the path of the folder containing all the codebases e.g.

```python
saveTermsToBeCategorised(findRepoPaths(getPath("../data/ugrad-009-01/")), "ugrad-009-01")
```

## Re-categorising terms from identifiers in codebase(s)

- Similar process to the above, except include the previous categorisations in `context-answers.txt`, `design-answers.txt` and `neither-answers.txt` in the `../vocabularies/your-domain-name"` folder.
- The call to `saveTermsToBeCategorised` will automatically add the terms that are both in the codebase and in the answers to the vocabulary text files.
- Check the last line of the console output to verify whether you need to categorise any further terms.
