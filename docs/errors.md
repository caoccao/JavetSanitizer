# Javet Sanitizer Errors

| Error                 | Code | Message                                                                                | 
|-----------------------|-----:|----------------------------------------------------------------------------------------|
| UnknownError          |    1 | Unknown error: ${message}                                                              |
| EmptyCodeString       |    2 | The JavaScript code is empty.                                                          |
| IdentifierNotAllowed  |  100 | Identifier ${identifier} is not allowed.                                               |
| KeywordNotAllowed     |  101 | Keyword ${keyword} is not allowed.                                                     |
| InvalidToken          |  200 | Token ${actualToken} is invalid. Expecting ${expectedToken}.                           |
| ArgumentCountMismatch |  210 | Argument count ${actualCount} mismatches the expected argument count ${expectedCount}. |
| SyntaxCountMismatch   |  220 | Syntax count ${actualCount} mismatches the expected syntax count ${expectedCount}.     |
| SyntaxCountTooSmall   |  221 | Syntax count ${actualCount} is less than the minimal syntax count ${minCount}.         |
| SyntaxCountTooLarger  |  222 | Syntax count ${actualCount} is greater than the maximal syntax count ${maxCount}.      |
