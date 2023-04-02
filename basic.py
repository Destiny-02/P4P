import json

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

def jsonToSet(filename: str) -> set:
  data = set()
  with open(filename, encoding="utf-8") as jsonFile:
    parserOutputJson = json.load(jsonFile)

    for item in parserOutputJson['identifiers']:
      words: list[str] = item['name'].split("_") # we only support snake_case for this basic PoC
      for word in words:
        data.add(word)

  return data


# Main
domain_terms = txtToSet('domain_terms.txt')


# uncomment these lines to use the output from the parser
identifiers = txtToSet('submission.txt')
# identifiers = jsonToSet('./parsers/output/java.json')


count = 0
numDomainTerms = len(domain_terms)
for term in domain_terms:
  if term in identifiers:
    count += 1
print(count/numDomainTerms)
