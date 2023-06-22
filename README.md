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

# Updating the tool

When updating the tool, the vocabularies and results in the `results/` folder need to be updated.
The steps for doing so are as follows:

`x` represents the domain folder name
`n` represents the number of codebases that was used

- Paper 3 Questions
  - Rerun the `paper3Questions` tool
  - Move the printed results to the 3 questions into `results/x/x-paper3Questions.txt`
- Vocabulary Builder
  - Rename the vocabulary files from `context.txt`, `design.txt` and `neither.txt` to `context-answers-txt`, `design-answers.txt` and `neither-answers.txt` respectively.
  - Use the `vocabularyBuilder` tool to rebuild the vocabularies using the domain description.
  - Upate the last 2 lines in `results/x/x-description.txt`
  - Use the `vocabularyBuilder` tool to rebuild the vocabularies codebase by codebase.
- Analyse codebases
  - Rerun the `analyseCodebases` tool.
  - Replace the results in `results/x/x-stats-n.csv` with the tool output csv.
  - Update the workbook version
  - Move the printed results for the median and 9 LA measures into `results/x/x-la-n.txt`
- Machine learning experiment
  - Replace the vocabularies in the `results/x/vocabularies` folder with the small and large vocabularies.
    - For the small vocabulary files, you will need to checkout the right commit that represents the point where the small vocabularies were completed.
  - Rerun the `machineLearningExperiment` tool.
  - Replace the results in `results/x/x-ml-n.csv`with the tool output csv.
  - Update the workbook version
- Vocabulary size with each codebase
  - Look through the commit history from when you re-categorised the terms using the `vocabularyBuilder` tool.
  - Note down how many lines each of the 3 vocabularies increased with each codebase.
  - Replace the results in `results/x/x-vocab-n.csv`
  - Update the workbook version
- Term frequency
  - Rerun the `calcTermFrequency` tool.
  - Replace the results in `results/x/x-term-freq-y-n.csv` with the 2 tool output csvs.
  - Update the workbook versions
- Percent of vocabulary used
  - Rerun the `percentOfVocab` tool.
  - Replace the results in `results/x/x-term-percentVocab-y-n.csv` with the tool output csv.
  - Update the workbook version
- Identifier categorisation/validation
  - No manual updates required, the whole pipeline is automated.

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
