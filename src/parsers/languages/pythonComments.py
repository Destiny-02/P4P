import io
import sys
import tokenize

# we have to write this part in python

file = ''
with open(sys.argv[1], 'r') as fileToParse:
    file += fileToParse.read() + "\n"

comments = []
tokens = tokenize.tokenize(io.BytesIO(file.encode()).readline)
for type, value, _, _, _ in tokens:
  if type is tokenize.COMMENT:
    print(value)
