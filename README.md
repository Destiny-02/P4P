# Setup

- Install python3 or later
- run `python --version` to confirm that the python version in PATH is v3
- run `python -m pip install --user pipenv` to install [the package manager](https://packaging.python.org/en/latest/tutorials/managing-dependencies/)
- run `pipenv install` to install [the dependencies listed in Pipfile](./Pipfile)

# Run Python Scripts

`python <filename.py>`

# Run The Tools

The tools are located in:

- `/src/paper3Questions/`
- `/src/vocabularyBuilder/`
- `/src/analyseCodebases/`
- `/src/machineLearningExperiment/`
- `src/calcTermFrequency/`
- `src/percentageOfVocab/`

Instructions for running the tools are in the `README.md` of the respective folder.

# Run Tests

`pip install pytest` # if not already installed
`pytest`

# Other folders

- `data/` contains the codebases
- `results/` contains the (manually) saved results of the tools

# Updating the tool

When updating the tool, the vocabularies and results in the `results/` folder need to be updated.
The steps for doing so are as follows:

- TODO
