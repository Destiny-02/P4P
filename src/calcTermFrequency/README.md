# Calculate Term Frequency

This folder contains the `calcTermFrequency.py` script, which can be used to calculate the number of codebases that use a given term in a vocabulary.

## Prerequisites

- Ensure that vocabulary file(s) are in `../vocabularies/your-domain-name"` directory
- Ensure that the codebases are in the `../../data/your-domain-name` directory.
- Ensure that the helper files and functions have not been deleted.

# Usage

- Replace the path in the call(s) to `saveAllRepoTermsToCache` with the path of the codebases
- Replace the second argument in the call(s) to `main` with the path of the vocabulary file
- Run the script with `python calcTermFrequency.py`.
- The results will be written to a csv specified in the call(s) to `csvToSheet`.
