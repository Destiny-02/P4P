# Machine Learning Experiment

This folder contains scripts for seeing how accurate our tool is when "trained" on a number of codebases. The script uses 2 sets of vocabularies - one that is built from a number of codebases, one that is built from additional codebases.

## Prerequisites

- Two versions of `context.txt`, `design,txt` and `neither.txt` - one that was built from a small subset of codebases and one that serves as the "answer" of the correct term categorisation of all the codebases.
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

# Usage

- Example usage can be found in `__main__.py`. Replace the parameters with the name of your domain and the locations of the two vocabulary versions.
- From the repo root, run the script with `python -m src.machineLearningExperiment`.
- The accuracy percentages will be written to `tool-results.csv`.
