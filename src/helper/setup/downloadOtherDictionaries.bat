cd ../../../data
mkdir downloaded
cd downloaded

curl -LO https://github.com/hyperreality/American-British-English-Translator/raw/master/data/american_spellings.json
curl -LO https://github.com/casics/spiral/raw/master/spiral/data/frequencies-full.csv.gz
gzip -fd frequencies-full.csv.gz
curl -LO https://github.com/IlyaSemenov/wikipedia-word-frequency/raw/798ea90/results/enwiki-2023-04-13.txt
