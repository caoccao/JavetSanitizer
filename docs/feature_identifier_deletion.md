# Feature - Identifier Deletion

Identifiers can be deleted from V8.

```js
eval(''); // eval() is deleted.
Function(''); // Function() is deleted.
WebAssemble(''); // WebAssemble() is deleted.
```

The following example shows how to update the to be deleted identifier list.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
option.getToBeDeletedIdentifierSet().remove("WebAssemble");
option.getToBeDeletedIdentifierSet().add("Promise");
option.seal();
```

The default to be deleted identifier list is as follows:

```js
eval
Function
WebAssembly
```
