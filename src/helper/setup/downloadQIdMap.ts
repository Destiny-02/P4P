import { promises as fs } from "node:fs";
import { join } from "node:path";
import { format } from "prettier";

// written in JS because API requests suck in python

export async function downloadQIdMap() {
  const originalQuery = await fs.readFile(
    join(__dirname, "./queryWordnetQIds.sparql"),
    "utf8"
  );

  const finalQuery = originalQuery.replace("LIMIT 50", "LIMIT 30000");

  const apiResp = await fetch(
    `https://query.wikidata.org/sparql?query=${encodeURIComponent(finalQuery)}`,
    { headers: { Accept: "application/sparql-results+json" } }
  ).then((r) => r.json());

  // map of synsetIDs to qIDs
  const output: Record<string, string[]> = {};

  // transform API result
  for (const item of apiResp.results.bindings) {
    const qId = item.item.value.split("/").at(-1);
    output[item.wordnetId.value] ||= [];
    output[item.wordnetId.value].push(qId);
  }

  await fs.writeFile(
    join(__dirname, "../../../data/downloaded/wordnetIDs.json"),
    format(JSON.stringify(output), { parser: "json" })
  );
}
