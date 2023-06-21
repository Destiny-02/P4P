/* eslint-disable */
// @ts-nocheck -- this is a sample file, not part of the sourcecode

import { named1, named2 } from "./someFile";
import { named3, named4 as renamed4 } from "./someFile.ts";
import defaultImport from "./someFile.js";
import defaultImport2, { named5 } from "./someFile";
import { default as renamedDefault } from "./someFile";
import { default as renamedDefault2, named5 as renamed5 } from "./someFile";

import json from "./foo.json" assert { type: "json" };

/**
 * comments yay
 * @param {number} a
 * @param {number} b
 */
function add(a, b) {
  // an inline comment

  return a + b;
}

const x = 1;
let y = 2;
var z = 3;

window.myCustomAttribute = "";

class SomeClass {}

// these are all globals that should not be considered
console.log(document.getElementById(navigator.userAgent));
