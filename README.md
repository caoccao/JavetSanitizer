# Javet Sanitizer

Javet Sanitizer is a sanitizer for parsing and validating JavaScript code on JVM. It is built on top of [antlr4](https://github.com/antlr/antlr4) and [grammars-v4](https://github.com/antlr/grammars-v4).

Javet Sanitizer provides a set of rich checkers at AST level for [Javet](https://github.com/caoccao/Javet) so that applications can address and eliminate the potential threats before the JavaScript code is executed.

## Why do I need to sanitize the JavaScript code?

A script engine like Javet can be shared by multiple scripts, however one script may tamper the script engine to hack the next script to be executed. For instance, the built-in `JSON` can be hijacked so that `stringify`, `parse` may work improperly during the JSON serialization or deserialization.

Javet Sanitizer is designed to protect the script engine from that kind of attacks.

## Why not use Babel?

- Babel is too slow.
- Babel AST cannot be easily imported to JVM.

## Features

- AST Pattern Matching
- [Built-in Object Protection](docs/feature_built_in_objects.md)
- Keyword Restriction
- Function Restriction
- Identifier Naming Convention
- Complete Customization

## Quick Start

TODO

## Document

- [Development](docs/development.md)
- [Errors](docs/errors.md)

## License

[APACHE LICENSE, VERSION 2.0](blob/main/LICENSE)
