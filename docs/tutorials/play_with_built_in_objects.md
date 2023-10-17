# Tutorial - Play with Built-in Objects

There are a set of [built-in objects](../features/built_in_object_protection.md) that cannot be tampered, in other words, remain immutable to prevent malicious script execution.

## Sample Built-in Object - Date

By default, built-in object `Date` is immutable so that the script cannot hijack `Date`. If you check a script with built-in object `Date` in `JavetSanitizerModuleChecker` with the default option, you will get an error. You may create your own option with built-in object `Date` allowed and the same script will pass the check.

```java
String codeString = "function main() { Date.parse = () => {}; }";
// Check the script with the default option.
try {
    new JavetSanitizerModuleChecker().check(codeString);
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getContext());
}

System.out.println("----------------------------------------");

// Create a new option with built-in object Data allowed.
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getBuiltInObjectSet().remove("Date");
option.seal();
// Check the script with the new option.
try {
    new JavetSanitizerModuleChecker(option).check(codeString);
    System.out.println(codeString + " // Valid");
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getContext());
}

/*
Identifier Date is not allowed.
Source Code: Date.parse = () => {}
Line Number: 1, 1
Column: 18, 39
Position: 18, 39
----------------------------------------
function main() { Date.parse = () => {}; } // Valid
*/
```

The complete code is at [here](../../src/test/java/com/caoccao/javet/sanitizer/tutorials/TutorialPlayWithBuiltInObjects.java).
