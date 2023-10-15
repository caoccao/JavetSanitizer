# Feature - Keyword Restriction

JavaScript keywords can be restricted. The following example shows how to turn off the keyword restriction.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
        .setKeywordAsyncEnabled(true)
        .setKeywordAwaitEnabled(true)
        .setKeywordDebuggerEnabled(true)
        .setKeywordExportEnabled(true)
        .setKeywordImportEnabled(true)
        .setKeywordVarEnabled(true)
        .setKeywordWithEnabled(true)
        .setKeywordYieldEnabled(true)
        .seal();
```

The default restricted keyword list is as follows:

```js
async
await
debugger
export
import
var
with
yield
```
