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
option.getToBeDeletedIdentifierList().remove("WebAssemble");
option.getToBeDeletedIdentifierList().add("Promise");
option.seal();
```

The default to be deleted identifier list is as follows:

```js
eval
Function
WebAssembly
```

Please refer to the [tutorial](../tutorials/play_with_identifier_deletion.md) for more details.
