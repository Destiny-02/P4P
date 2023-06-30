# Repositories Design Vocabulary Builder

This folder contains the `saveTermsFromRepo.py` script, which can be used to extract terms from repositories and record the frequency of the terms being used in a json file. Each repository can increment the frequency by a maximum of 1 (i.e. if a term is used 10 times in a repository, it will only increment the frequency by 1).

# Usage

- Replace the path in the call to `main` with the path containing the repositories you want to process.
- Run the script with `python saveTermsFromRepo.py`.
- The results will be written to a json file called `design-terms.json`.

## Saving frequent terms

- Once you have processed enough repositories, you may want to keep only the terms that are used in at least x repositories.
- To do this, ensure that only the call to is uncommented `saveDesignTermsAsVocabFile` and replace the argument with the minimum number of repositories that a term must be used in to be saved.
- Run the script with `python saveTermsFromRepo.py`.
- The results will be written to a text file called `design-terms.txt`.

## Repository cloner

- For a more efficient way of querying for and batch downloading GitHub repositories.
- Replace the `query` and `destinationFolder` variables with the query you want to use and the path to the folder you want to save the repositories to.
- Run the script with `python saveTermsFromRepo.py`.
