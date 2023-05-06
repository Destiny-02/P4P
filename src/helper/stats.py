def getDVInSet(domainTerms: set, words: set) -> set:
  """
  Finds the domain terms in a set of words
  """
  dv = set()
  for term in domainTerms:
    if term in words:
      dv.add(term)
  return dv

def findLA(domainVocabs):
    """
    Finds the lexical agreement (LA) between a list of domain vocabularies
    """
    n = len(domainVocabs)
    if n <=1:
      return 0

    # 1. Find the average number of terms shared between 2 DV pairs
    totalSharedTerms = 0 # the total number of shared terms between all pairs of domain vocabs
    for i in range(n):
      for j in range(n):
        if i < j:
          sharedTerms = len(domainVocabs[i].intersection(domainVocabs[j]))
          totalSharedTerms += sharedTerms

    # 2!(n-2)!/n! = 2/(n(n-1)) 
    # This is the probability of choosing 2 items from a group of n items, regardless of order
    # AKA n(n-1)/2 possible unique pairs
    # AKA the number of times the code inside the if i < j statement is executed
    p = 2/(n*(n-1))

    averageSharedTerms = totalSharedTerms * p
    
    # 2. Find the average number of terms in a DV
    totalNumTerms = 0 # the total number of terms in all domain vocabs
    for i in range(n):
      totalNumTerms += len(domainVocabs[i])
    averageNumTerms = totalNumTerms / n
    
    # Calculate the level of lexical agreement (LA)
    if averageNumTerms == 0:
      return 0
    else:
      return averageSharedTerms / averageNumTerms