# Tutorial - Play with Function Restriction

There are a set of [functions](../features/function_restriction.md) that must be declared to form a valid module to be executed.

## Sample Function - main()

By default, `function main()` must be declared so that the script has a default entry function. If you check a script without `function main()` in `JavetSanitizerModuleChecker` with the default option, you will get an error. You may create your own option with a set of new functions and the same script will pass the check.

```java
String codeString = "function myMain() {}";
// Check the script with the default option.
try {
    new JavetSanitizerModuleChecker().check(codeString);
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
}

System.out.println("----------------------------------------");

// Create a new option with keyword import enabled.
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getReservedFunctionIdentifierSet().remove("main");
option.getReservedFunctionIdentifierSet().add("myMain");
// Check the script with the new option.
try {
    new JavetSanitizerModuleChecker(option).check(codeString);
    System.out.println(codeString + " // Valid");
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
}

/*
Function main is not found.
----------------------------------------
function myMain() {} // Valid
*/
```

The complete code is at [here](../../src/test/java/com/caoccao/javet/sanitizer/tutorials/TutorialPlayWithFunctionRestriction.java).
