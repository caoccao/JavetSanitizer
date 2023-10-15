# Feature - Identifier Naming Convention

Javet Sanitizer has built-in support for enforcing the naming convention of reserved identifiers so that the application can embed some reserved identifiers invisible or read-only to the guest scripts, this feature is disabled. The rules are as follows.

- A lambda expression can define the reserved identifier matcher.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
        .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"))
        .seal();
new JavetSanitizerModuleChecker(option).check("function main() { $a = 1; }"); // Invalid
```

- Some pre-defined reserved identifiers are allowed.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
        .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"));
option.getReservedIdentifierSet().add("$a");
option.seal();
new JavetSanitizerStatementListChecker(option).check("x = $a;"); // Valid
new JavetSanitizerStatementListChecker(option).check("$a = 1;"); // Invalid
new JavetSanitizerStatementListChecker(option).check("$b = 1;"); // Invalid
```

- Some pre-defined reserved identifiers are now allowed to be at left hand side so that these reserved identifiers remain immutable.

```java
JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
        .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"));
option.getReservedIdentifierSet().add("$a");
option.getReservedMutableIdentifierSet().add("$a");
option.seal();
new JavetSanitizerStatementListChecker(option).check("x = $a;"); // Valid
new JavetSanitizerStatementListChecker(option).check("$a = 1;"); // Valid
new JavetSanitizerStatementListChecker(option).check("$b = 1;"); // Invalid
```
