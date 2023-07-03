# Setup

- Install python 3.10
- Install NodeJS v18 or newer
- run `python --version` to confirm that the python version in PATH is v3
- run `python -m pip install --user pipenv` to install [the package manager](https://packaging.python.org/en/latest/tutorials/managing-dependencies/)
- run `pipenv install` to install [the dependencies listed in Pipfile](./Pipfile)
- run `npm run downloadDictionaries` and `npm run downloadAbbrs` to download some required JSON files

# Run Python Scripts

`python <filename.py>`

# Run The Tools

The tools are located in:

- `/src/paper3Questions/`
- `/src/vocabularyBuilder/`
- `/src/analyseCodebases/`
- `/src/machineLearningExperiment/`
- `src/calcTermFrequency/`
- `src/percentOfVocab/`
- `src/categoriseIdentifiers/`

Instructions for running the tools are in the `README.md` of the respective folder.

# Run Tests

`pip install pytest` # if not already installed
`pytest`

# Other folders

- `data/` contains the codebases
- `results/` contains the (manually) saved results of the tools

# Speeding up the tool

If you find it is taking a long time to run, it is because it is parsing a lot of codebases, transforming a lot of strings and looking up words in large dictionaries.

If you are running the tool multiple times, or are running multiple tools, you can speed up the tool by using the cached results.

To do this:

- Ensure that you have run the tool at least once and that the cached terms have been stored in a json file
- Use `python --cache ...` instead of `python ...` (works in all scripts). Alternatively:
- Comment out the following line

```python
saveAllRepoTermsToCache(findRepoPaths(getPath(DATA_FOLDER + "ugrad-009-01")), CACHED_TERMS)
```

This currently works for `calcTermFrequency` and `percentOfVocab`.

# References

[American to British English Dictionary](https://raw.githubusercontent.com/hyperreality/American-British-English-Translator/master/data/american_spellings.json)
