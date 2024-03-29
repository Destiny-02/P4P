# 3 Questions

This folder contains a script, which can be used to calculate the answers to the 3 questions asked in the paper [On the Use of Domain Terms in Source Code](https://ieeexplore-ieee-org.ezproxy.auckland.ac.nz/document/4556123).

More specifically,

1. The average percentage of terms in the domain vocabularly that are found in a given codebase.
2. The average percentage of terms in the domain vocabularly that are found in a identifiers only and comments only.
3. The lexical agreement (LA) between any two pairs of codebases.

## Prerequisites

- Ensure that `domain-terms.txt` is in the same folder as `paper3Questions.py`.
  - Each line contains one or two domain terms separated by a space.
  - For lines with two domain terms, the first term is the equivalent of the second term e.g. `curr current`.
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

## Usage

- Modify the call to main with the name of the folder containing the codebases e.g. `main("../../data/your-domain-name/")`
- From the repo root, run the script with `python -m src.paper3Questions`.

## Results

- 4 percentages will be printed to the console
