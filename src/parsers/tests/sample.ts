/* eslint-disable */
// @ts-nocheck -- this is a sample file, not part of the sourcecode

import { named1, named2 } from "./someFile";
import { named3, named4 as renamed4 } from "./someFile.ts";
import defaultImport from "./someFile.js";
import defaultImport2, { named5 } from "./someFile";
import { default as renamedDefault } from "./someFile";
import { default as renamedDefault2, named5 as renamed5 } from "./someFile";

import json from "./foo.json" assert { type: "json" };

// extra: TS only
import type { Type1 } from "./someFile";
import defaultWithType, { type Type2 } from "./someFile";
import { default as defaultWithType2, type Type3, named6 } from "./someFile";

/** a comment  */
export function add(a: number, b: number) {
  // another comment
}

const x = 1;
let y = 2;
var z = 3;

window.myCustomAttribute = "";

class SomeClass {}

interface MyIFace {}

type MyType = {};

enum MyEnum {
  ENUM_MEMBER_1,
  ENUM_MEMBER_2,
}

// these are all globals that should not be considered
console.log(document.getElementById(navigator.userAgent));
