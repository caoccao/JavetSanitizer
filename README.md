# JavetSanitizer

Javet Sanitizer is a sanitizer for parsing and validating JavaScript code on JVM. It is built on top of [antlr4](https://github.com/antlr/antlr4) and [grammars-v4](https://github.com/antlr/grammars-v4).

## Quick Start

TODO

## Development

- Follow this [guide](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md) to install antlr4. Please choose the right version of antlr4 wisely because there are backward / forward compatibility issues in antlr4.
- Clone [grammars-v4](https://github.com/antlr/grammars-v4/).

```sh
git clone https://github.com/antlr/grammars-v4.git
```

- Generate the Java files.

```sh
cd grammars-v4/javascript/javascript
antlr4 -package com.caoccao.javet.sanitizer.antlr -o gen *.g4
cd ../../../
```

- Copy the Java files.

```sh
cd JavetSanitizer
# TODO
```

## License

[APACHE LICENSE, VERSION 2.0](blob/main/LICENSE)
