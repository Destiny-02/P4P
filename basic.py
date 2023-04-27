import json
from porter2stemmer import Porter2Stemmer
import splitIdentifier as s

stemmer = Porter2Stemmer()

def txtToSet(filename):
    data = set()
    isFirst = True

    with open(filename, 'r', newline='') as txtfile:
      for line in txtfile:
        # Remove the BOM from the first term
        if isFirst:
            line = line.strip('ï»¿')
            isFirst = False

        words = line.lower().split()
        for word in words:
            data.add(word)
    return data

def jsonToSet(filename: str) -> dict:
  data = {}
  with open(filename, encoding="utf-8") as jsonFile:
    parserOutputJson = json.load(jsonFile)

    for item in parserOutputJson['identifiers']:
      words: list[str] = s.splitIdentifier(item['name'])
      for word in words:
        stemmedWord = stemmer.stem(word)
        if stemmedWord in data:
          data[stemmedWord] += 1
        else:
          data[stemmedWord] = 1

  return data


# Main
domain_terms = txtToSet('domain_terms.txt')


# uncomment these lines to use the output from the parser
# identifiers = txtToSet('submission.txt')
identifiers = jsonToSet('./parsers/output/java.json')

print(identifiers)

count = 0
numDomainTerms = len(domain_terms)
for term in domain_terms:
  if term in identifiers:
    count += 1
print(count/numDomainTerms)
