# Tutorial - Play with Identifier Deletion

There are a set of [identifiers](../features/identifier_deletion.md) that will be deleted in V8.

## Sample Identifier - WebAssembly

By default, `WebAssembly` is deleted so that there is not way of accessing `WebAssembly` in V8. If you check a script with `WebAssembly` with the default option, you will get an error. You may create your own option with a set of new identifiers and the same script will pass the check.

```java
try (V8Runtime v8Runtime = V8Host.getV8Instance().createV8Runtime()) {
    // Initialize V8 with the default option.
    String codeString = JavetSanitizerFridge.generate(JavetSanitizerOption.Default);
    v8Runtime.getExecutor(codeString).executeVoid();
    codeString = "const a = WebAssembly;";
    v8Runtime.getExecutor(codeString).setResourceName("test.js").executeVoid();
} catch (JavetExecutionException e) {
    System.out.println(e.getScriptingError());
} catch (JavetException ignored) {
}

System.out.println("----------------------------------------");

// Create a new option with WebAssembly allowed.
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getToBeDeletedIdentifierList().remove("WebAssembly");
option.seal();
try (V8Runtime v8Runtime = V8Host.getV8Instance().createV8Runtime()) {
    // Initialize V8 with the default option.
    String codeString = JavetSanitizerFridge.generate(option);
    v8Runtime.getExecutor(codeString).executeVoid();
    codeString = "const a = WebAssembly;";
    v8Runtime.getExecutor(codeString).setResourceName("test.js").executeVoid();
    System.out.println(codeString + " // Valid");
} catch (JavetExecutionException e) {
    System.out.println(e.getScriptingError());
} catch (JavetException ignored) {
}

/*
ReferenceError: WebAssembly is not defined
Resource: test.js
Source Code: const a = WebAssembly;
Line Number: 1
Column: 10, 11
Position: 10, 11
----------------------------------------
const a = WebAssembly; // Valid
*/
```

The complete code is at [here](../../src/test/java/com/caoccao/javet/sanitizer/tutorials/TutorialPlayWithIdentifierDeletion.java).
