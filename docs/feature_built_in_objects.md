# Feature - Built-in Objects

## Rules

The default built-in objects are protected by JavetSanitizer under the following rules:

- Built-in objects cannot be in the left-hand side in assignment.

```js
Object = 1; // Invalid
Object.a = 1; // Invalid
const object = Object; // Valid
const a = Object.a; // Valid
```

- Built-in objects cannot be declared again.

```js
(Object) => {} // Invalid
function Object() => {} // Invalid
class Object {} // Invalid
try {} catch (Object) {} // Invalid
[Object, JSON] = [1, 2]; // Invalid
```

## List

The default built-in objects are as follows:

```js
_
_error
AbortController
AbortSignal
AggregateError
Array
ArrayBuffer
assert
async_hooks
atob
Atomics
BigInt
BigInt64Array
BigUint64Array
Boolean
btoa
buffer
Buffer
child_process
clearImmediate
clearInterval
clearTimeout
cluster
console
constants
crypto
DataView
Date
Decimal
decodeURI
decodeURIComponent
dgram
diagnostics_channel
dns
domain
encodeURI
encodeURIComponent
Error
escape
eval
EvalError
Event
events
EventTarget
FinalizationRegistry
Float32Array
Float64Array
fs
Function
global
globalThis
http
http2
https
Infinity
inspector
Int16Array
Int32Array
Int8Array
Intl
isFinite
isNaN
JSON
Map
Math
MessageChannel
MessageEvent
MessagePort
module
NaN
net
Number
Object
os
parseFloat
parseInt
path
perf_hooks
performance
process
Promise
Proxy
punycode
querystring
queueMicrotask
RangeError
readline
ReferenceError
Reflect
RegExp
repl
require
Set
setImmediate
setInterval
setTimeout
SharedArrayBuffer
stream
String
string_decoder
Symbol
SyntaxError
sys
TextDecoder
TextEncoder
timers
tls
trace_events
tty
TypeError
Uint16Array
Uint32Array
Uint8Array
Uint8ClampedArray
undefined
unescape
URIError
url
URL
URLSearchParams
util
v8
vm
wasi
WeakMap
WeakRef
WeakSet
WebAssembly
worker_threads
zlib
```
