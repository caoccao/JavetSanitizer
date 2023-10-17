# Feature - Identifier Restriction

JavaScript allows the identifiers to be named as built-in objects. Sometimes that creates some confusions. JavetSanitizer can disallow a set of identifiers to avoid such confusions. The following example shows how to customize the disallowed identifiers.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getDisallowedIdentifierSet().add("prototype");
option.getDisallowedIdentifierSet().remove("Promise");
option.seal();
```

The default disallowed identifier list is as follows:

```js
__proto__
apply
AsyncFunction
AsyncGenerator
AsyncGeneratorFunction
bind
call
clearInterval
clearTimeout
defineProperties
defineProperty
eval
Function
Generator
GeneratorFunction
getPrototypeOf
global
globalThis
Intl
Promise
prototype
Proxy
Reflect
require
setImmediate
setInterval
setPrototypeOf
setTimeout
Symbol
uneval
WebAssembly
window
XMLHttpRequest
```

## Why are global and globalThis disallowed?

It's so easy to access or change `global` or `globalThis` in a script to impact the next script to be executed. In order to fully reuse the V8 runtime, such behavior is addressed as an error in the sanity check.

## Why are apply, bind, call and eval disallowed?

`apply`, `bind`, `call` and `eval` are commonly used in the JS obfuscators. The obfuscated code can bypass the sanity check, is hard to read and maintain.
