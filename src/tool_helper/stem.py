from porter2stemmer import Porter2Stemmer
import argparse

def stem_set(input):
  """
  Given a set of strings, returns a set of stemmed strings.
  Each item in the set can be a single word or multiple words.
  # TODO: Add support for punctuation, numbers and special characters
  """
  stemmer = Porter2Stemmer()

  input_terms = set()
  for piece in input:
    words = piece.strip().lower().split()
    for word in words:
      if word:
        input_terms.add(word)

  # Stem input terms
  stemmed_terms = set(stemmer.stem(term) for term in input_terms)

  # Remove duplicates from stemmed terms
  output_terms = set(stemmed_terms)
  
  return output_terms

def stem_file(input_file, output_file):
  """
  Given the name of an input file and an output file, stems the terms in the input file and writes them to the output file.
  Each line in the input file can have multiple terms or be empty.
  Terms can have trailing/leading whitespace, mixed capitalisation and be duplicates. 
  """
  stemmer = Porter2Stemmer()
  with open(input_file, 'r') as input_file:
    input_terms = set()
    for line in input_file:
      words = line.strip().lower().split()
      for word in words:
        if word:
          input_terms.add(word)

  # Stem input terms
  stemmed_terms = set(stemmer.stem(term) for term in input_terms)

  # Remove duplicates from stemmed terms
  output_terms = set(stemmed_terms)

  # Sort output terms in alphabetical order
  output_terms = sorted(output_terms)

  # Write to output file
  with open(output_file, 'w') as output_file:
      for term in output_terms:
          output_file.write(term + '\n')

  # Print number of terms in input and output files
  print('Number of terms in input file:', len(input_terms))
  print('Number of terms in output file:', len(output_terms))

if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Stem terms in input file and write to output file')
  parser.add_argument('input_file', help='name of input file')
  parser.add_argument('output_file', help='name of output file')
  args = parser.parse_args()
  stem_file(args.input_file, args.output_file)