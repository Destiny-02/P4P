import { promises as fs } from "node:fs";
import { join } from "node:path";
import { format } from "prettier";
import { downloadQIdMap } from "./downloadQIdMap";

//
// this file has to be JS/TS, because some of the source data is eval'ed from JS files on GitHub.
//
// run this script using `npm run downloadAbbrs` from the repo root
//

/** we can define our own entries here */
const CUSTOM_OVERRIDES: AbbreviationFile = {
  abbreviations: {
    sys: ["system"],
    ex: ["exception"],
  },
  skipIdentifiers: [],
  skipWords: [],
};

// there's some junk in the databases
const ABBREVIATIONS_TO_SKIP = [
  "$...",
  "to",
  // these terms have become so well established that they're
  // not really abbreviations anymore
  "regex",
  "auth",
  "temp",
];

export interface AbbreviationFile {
  abbreviations: {
    [abbreviation: string]: string[];
  };
  skipIdentifiers: string[];
  skipWords: string[];
}

/** downloads the list used by eslint-plugin-unicorn */
async function getUnicornData(baseData: AbbreviationFile) {
  const jsCode = await fetch(
    "https://github.com/sindresorhus/eslint-plugin-unicorn/raw/main/rules/shared/abbreviations.js"
  ).then((r) => r.text());

  const fetchedData: {
    defaultReplacements: {
      [abbreviation: string]: { [fullWord: string]: true };
    };
    defaultAllowList: { [identifier: string]: true };
    defaultIgnore: string[];
    // eslint-disable-next-line no-eval -- no other way, the library defines the logic in a JS file.
  } = eval(
    `((module) => { ;${jsCode}; return module.exports; })({ exports: {} })`
  );

  for (const [abbreviation, fullWords] of Object.entries(
    fetchedData.defaultReplacements
  )) {
    baseData.abbreviations[abbreviation] ||= [];
    baseData.abbreviations[abbreviation].push(...Object.keys(fullWords));
  }

  baseData.skipIdentifiers.push(...Object.keys(fetchedData.defaultAllowList));
  baseData.skipWords.push(...fetchedData.defaultIgnore);
}

/** downloads the JSON file from "abbrcode" on GitHub */
async function downloadAbbrData(baseData: AbbreviationFile) {
  const json: {
    abbrs: { abbr: string; degree: string }[];
    translations: { lang: string; word: string }[];
    word: string;
  }[] = await fetch("https://github.com/abbrcode/db/raw/main/abbrs/.json").then(
    (r) => r.json()
  );

  for (const item of json) {
    for (const { abbr } of item.abbrs) {
      baseData.abbreviations[abbr] ||= [];
      baseData.abbreviations[abbr].push(item.word);
    }
  }
}

async function main() {
  const baseData = structuredClone(CUSTOM_OVERRIDES);

  // (!) these two functions mutate their argument
  console.log("Downloading from unicorn…");
  await getUnicornData(baseData);
  console.log("Downloading from abbrcode…");
  await downloadAbbrData(baseData);

  // remove duplicates and sort keys alphabetically
  baseData.abbreviations = Object.keys(baseData.abbreviations)
    .sort()
    .reduce<AbbreviationFile["abbreviations"]>((sorted, abbr) => {
      sorted[abbr] = [...new Set(baseData.abbreviations[abbr])];
      return sorted;
    }, {});

  // delete items that we want to skip
  for (const abbrToSkip of ABBREVIATIONS_TO_SKIP) {
    delete baseData.abbreviations[abbrToSkip];
  }

  const folder = join(__dirname, "../../../data/downloaded");
  await fs.mkdir(folder, { recursive: true });

  await fs.writeFile(
    join(folder, "abbreviations-dict.json"),
    format(JSON.stringify(baseData), { parser: "json" })
  );

  console.log("Downloading wordnet-QIDs…");
  await downloadQIdMap();
}

main();
