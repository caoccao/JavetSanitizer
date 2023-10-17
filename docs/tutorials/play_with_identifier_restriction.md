# Tutorial - Play with Identifier Restriction

There are a set of [identifiers](../features/identifier_restriction.md) that cannot be referenced to prevent malicious script execution.

## Sample Identifier - eval

By default, identifier `eval` is disallowed so that the script cannot reference `eval`. If you check a script with identifier `eval` in `JavetSanitizerModuleChecker` with the default option, you will get an error. You may create your own option with identifier `eval` allowed and the same script will pass the check.

```java
String codeString = "function main() { eval('1'); }";
// Check the script with the default option.
try {
    new JavetSanitizerModuleChecker().check(codeString);
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getContext());
}

System.out.println("----------------------------------------");

// Create a new option with identifier eval allowed.
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
option.getDisallowedIdentifierSet().remove("eval");
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
Identifier eval is not allowed.
Source Code: eval
Line Number: 1, 1
Column: 18, 22
Position: 18, 22
----------------------------------------
function main() { eval('1'); } // Valid
*/
```

The complete code is at [here](../../src/test/java/com/caoccao/javet/sanitizer/tutorials/TutorialPlayWithIdentifierRestriction.java).
