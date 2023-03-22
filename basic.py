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


# Main
domain_terms = txtToSet('domain_terms.txt')
identifiers = txtToSet('submission.txt')

count = 0
numDomainTerms = len(domain_terms)
for term in domain_terms:
    if term in identifiers:
      count += 1
print(count/numDomainTerms)
