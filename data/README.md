# The data folder

The data folder will contain all the data files that is used by `tool.py`. If this repo gets to large, we can update the `.gitignore`.

# Folder structure

At the root, there is one folder for each domain. Each domain folder contains the following:

- A list of domain terms called `domain-terms.txt`
  - Each line contains one or two domain terms separated by a space
  - For lines with two domain terms, the first term is the equivalent of the second term e.g. `curr current`
- A number of folders, one for each codebase
  - The nested nature of folders should not matter
