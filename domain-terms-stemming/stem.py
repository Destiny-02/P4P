from porter2stemmer import Porter2Stemmer

stemmer = Porter2Stemmer()

# Read in input file
# Strip terms of whitespace and convert to lowercase
# Also ignore empty lines
with open('input.txt', 'r') as input_file:
    input_terms = set(line.strip().lower() for line in input_file if line.strip())

# Stem input terms
stemmed_terms = set(stemmer.stem(term) for term in input_terms)

# Remove duplicates from stemmed terms
output_terms = set(stemmed_terms)

# Sort output terms in alphabetical order
output_terms = sorted(output_terms)

# Write to output file
with open('output.txt', 'w') as output_file:
    for term in output_terms:
        output_file.write(term + '\n')

# Print number of terms in input and output files
print('Number of terms in input file:', len(input_terms))
print('Number of terms in output file:', len(output_terms))
