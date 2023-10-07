# JavetSanitizer

Javet Sanitizer is a sanitizer for parsing and validating JavaScript code on JVM. It is built on top of [antlr4](https://github.com/antlr/antlr4) and [grammars-v4](https://github.com/antlr/grammars-v4).

## Quick Start

TODO

## Development

- Prepare the environment.
  - JDK 1.8+
- Follow this [guide](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md) to install antlr4. Please choose the right version of antlr4 wisely because there are backward / forward compatibility issues in antlr4. The main branch references v4.13.1.
- Clone JavetSanitizer.

```sh
git clone https://github.com/caoccao/JavetSanitizer.git
```

- Clone grammars-v4.

```sh
git clone https://github.com/antlr/grammars-v4.git
```

- Generate the Java files.

```sh
cd grammars-v4/javascript/javascript
antlr4 -package com.caoccao.javet.sanitizer.antlr -o gen *.g4
cd ../../../
```

- Copy and update the Java files.

```sh
cd JavetSanitizer
cp -f ../grammars-v4/javascript/javascript/gen/*.java src/main/java/com/caoccao/javet/sanitizer/antlr
cp -f ../grammars-v4/javascript/javascript/Java/*.java src/main/java/com/caoccao/javet/sanitizer/antlr
sed -i 's/@link JavaScriptParser#singleExpression/@link JavaScriptParser#singleExpression()/g' src/main/java/com/caoccao/javet/sanitizer/antlr/JavaScriptParserListener.java
sed -i '1 i package com.caoccao.javet.sanitizer.antlr;\n' src/main/java/com/caoccao/javet/sanitizer/antlr/JavaScriptLexerBase.java
sed -i '1 i package com.caoccao.javet.sanitizer.antlr;\n' src/main/java/com/caoccao/javet/sanitizer/antlr/JavaScriptParserBase.java
```

- Build and test.

## License

[APACHE LICENSE, VERSION 2.0](blob/main/LICENSE)
