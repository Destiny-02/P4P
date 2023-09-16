# Introduction

This repository contains a number of tools which were used in Project #42, "Evaluating Identifier Meaningfulness".

Authors: Destiny Li, Kyle Hensel

Supervised by: Prof. Ewan Tempero

# Setup

- Install python 3.10
- Install NodeJS v18 or newer
- run `python --version` to confirm that the python version in PATH is v3
- run `python -m pip install --user pipenv` to install [the package manager](https://packaging.python.org/en/latest/tutorials/managing-dependencies/)
- run `pipenv install` to install [the dependencies listed in Pipfile](./Pipfile)
- run `npm run downloadDictionaries` to download some required JSON files

# Run The Tools

Instructions for running the tools are in the `README.md` of the respective folder.

The ugrad-009-01 domain and 20 implementations of ugrad-009-01 is included as part of this repository.

The tools are located in:

- [/src/paper3Questions/](/src/paper3Questions/)
- [/src/vocabularyBuilder/](/src/vocabularyBuilder/)
- [/src/analyseCodebases/](/src/analyseCodebases/)
- [/src/machineLearningExperiment/](/src/machineLearningExperiment/)
- [/src/calcTermFrequency/](/src/calcTermFrequency/)
- [/src/percentOfVocab/](/src/percentOfVocab/)
- [/src/categoriseIdentifiers/](/src/categoriseIdentifiers/)
- [/src/contextVocabBuilder/](/src/contextVocabBuilder/)

# Run The Tests

```bash
pip install pytest # if not already installed
pytest
```

# Other folders

- `data/`: Contains the codebases. Each folder nested in `data/` is a domain. Each folder nested in a domain folder is a codebase.
- `data/downloaded`: Contains JSON and CSV files that are downloaded when the repository is set up.
- `src/vocabularies/`: Contains the vocabulary files. Each folder nested in `src/vocabularies/` is a domain. Each file nested in a domain folder is a vocabulary file (`context.txt`, `design.txt` or `neither.txt`).
- `src/helper`: Contains helper functions and scripts used by multiple tools.

# Acknowledgements

[American to British English Dictionary](https://raw.githubusercontent.com/hyperreality/American-British-English-Translator/master/data/american_spellings.json)

[Wikipedia Word Frequency Data](https://github.com/IlyaSemenov/wikipedia-word-frequency/)

[Identifier Term Frequency Data](https://github.com/casics/spiral/tree/master/spiral/data)

[Common Code Abbreviations](https://github.com/sindresorhus/eslint-plugin-unicorn)

[More Code Abbreviations](https://github.com/abbrcode/abbreviations-in-code)

[Porter2 Stemmer](https://github.com/evandempsey/porter2-stemmer)

[NLTK](https://www.nltk.org/)

[JavaParser](https://www.npmjs.com/package/java-parser)
