# Feature Identifier Restriction

JavaScript allows the identifiers to be named as built-in objects. Sometimes that creates some confusions. JavetSanitizer can disallow a set of identifiers to avoid such confusions. The following example shows how to customize the disallowed identifiers.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getDisallowedIdentifierSet().add("prototype");
option.getDisallowedIdentifierSet().remove("Promise");
option.seal();
```

## List

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
