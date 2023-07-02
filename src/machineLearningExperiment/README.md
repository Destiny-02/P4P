# Machine Learning Experiment

This folder contains the `machineLearningExperiment.py` script, which can be used to see how accurate our tool is when "trained" on a number of codebases. The script uses 2 sets of vocabularies - one that is built from a number of codebases, one that is built from additional codebases.

## Prerequisites

- Two versions of `context.txt`, `design,txt` and `neither.txt` - one that was built from a small subset of codebases and one that serves as the "answer" of the correct term categorisation of all the codebases.
- Ensure that all the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

# Calculate tool accuracy for each codebase

- Replace the first argument in the call to `main` with the path to the vocabularies text files that were created using the smaller subset of codebases.
- Replace the second argument in the call to `main` with the path to the vocabularies text files that were created using all the codebases.
- Replace the third argument in the call to `main` with the name of the folder containing the codebases i.e. `your-domain-name` (referenced above).
- From the repo root, run the script with `python -m src.machineLearningExperiment`.
- The accuracy percentages will be written to `tool-results.csv`.
