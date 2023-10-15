# Tutorial - Play with Keyword Restriction

There are a set of [keywords](../features/keyword_restriction.md) that cannot be used to prevent unexpected script execution behavior. E.g. async, await, Promise.

## Sample Keyword - import

By default, keyword `import` is disallowed so that the script cannot reference any modules. If you check a script with keyword `import` in `JavetSanitizerModuleChecker` with the default option, you will get an error. You may create your own option with keyword `import` enabled and the same script will pass the check.

```java
String codeString = "import { x } from 'x.mjs'; function main() {}";
// Check the script with the default option.
try {
    new JavetSanitizerModuleChecker().check(codeString);
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getContext());
}

System.out.println("----------------------------------------");

// Create a new option with keyword import enabled.
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
        .setKeywordImportEnabled(true)
        .seal();
// Check the script with the new option.
try {
    new JavetSanitizerModuleChecker(option).check(codeString);
    System.out.println(codeString + " // Valid");
} catch (JavetSanitizerException e) {
    System.out.println(e.getMessage());
    System.out.println(e.getContext());
}

/*
Keyword import is not allowed.
Source Code: import { x } from 'x.mjs';
Line Number: 1, 1
Column: 0, 26
Position: 0, 26
----------------------------------------
import { x } from 'x.mjs'; function main() {} // Valid
*/
```

The complete code is at [here](../../src/test/java/com/caoccao/javet/sanitizer/tutorials/TutorialPlayWithKeywordRestriction.java).
