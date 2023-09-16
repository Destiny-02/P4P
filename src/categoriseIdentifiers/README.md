# Categorise Identifiers

This folder contains scripts to process identifiers, and categorise them based on the design, context and neither vocabulary files.

For the neither terms, the script runs various validators on each suspicious word in the identifier. These validators live in the [./validators](./validators) folder.

The validators detect obvious issues and can suggest improvements.

## Prerequisites

- Ensure that `design.txt`, `context.txt` and `neither.txt` is in the `../vocabularies/your-domain-name"` folder
- Ensure that the codebases are in the `../../data/your-domain-name` folder.

## Usage

- Run `python -m src.categoriseIdentifiers DOMAIN_NAME"`, where `DOMAIN_NAME` is the domain name.
- If you only want to analyse one repository from the domain, add the CLI flag `-r REPO_NAME`
- If you want the list of meaningful and non-meaningful identifier classifications, add the CLI flag `--list`
- Like all scripts, the `--cache` flag is optional and will bypass the slow parser
- The slowest checks are not run by default, you need to add `--slow` to generate these diagnostics.

Results are saved to the `output/` folder.
