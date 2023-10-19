/*
 * Copyright (c) 2023. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caoccao.javet.sanitizer.checkers;

import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import com.caoccao.javet.sanitizer.utils.SimpleList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJavetSanitizerModuleChecker extends BaseTestJavetSanitizerChecker {
    @Test
    public void testInvalidIdentifiers() {
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"))
                .seal();
        invalidIdentifierCodeStringMap.forEach((key, value) -> {
            String statement = "function main() { " + key + " }";
            assertException(
                    () -> new JavetSanitizerModuleChecker(option).check(statement),
                    100, "Identifier " + value + " is not allowed.",
                    null);
        });
    }

    @Test
    public void testInvalidStatements() {
        SimpleList.of("", "   ", null).forEach(statement ->
                assertException(
                        () -> new JavetSanitizerModuleChecker().check(statement),
                        2, "The JavaScript code is empty.",
                        null));
        assertException(
                () -> new JavetSanitizerModuleChecker().check("function a() {}"),
                300, "Function main is not found.",
                null);
        assertException(
                () -> new JavetSanitizerModuleChecker().check("import { x } from 'x.mjs'; function main() {}"),
                101, "Keyword import is not allowed.",
                "Source Code: import { x } from 'x.mjs';\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 26\n" +
                        "Position: 0, 26");
        assertException(
                () -> new JavetSanitizerModuleChecker().check("const a; const b;"),
                200, "Token VariableStatementContext is invalid. Expecting FunctionDeclarationContext.",
                "Source Code: const a;\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 8\n" +
                        "Position: 0, 8");
        assertException(
                () -> new JavetSanitizerModuleChecker().check("function a() {}\nconst a"),
                200, "Token VariableStatementContext is invalid. Expecting FunctionDeclarationContext.",
                "Source Code: const a\n" +
                        "Line Number: 2, 2\n" +
                        "Column: 0, 7\n" +
                        "Position: 16, 23");
        assertException(
                () -> new JavetSanitizerModuleChecker().check("function main() { eval('1'); }"),
                100, "Identifier eval is not allowed.",
                "Source Code: eval\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 18, 22\n" +
                        "Position: 18, 22");
    }

    @Test
    public void testValidStatements() throws JavetSanitizerException {
        assertTrue(
                new JavetSanitizerModuleChecker().check("function main() {}"),
                "Function main() should pass.");
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
        option.getReservedFunctionIdentifierSet().clear();
        option.getReservedFunctionIdentifierSet().add("a");
        option.seal();
        JavetSanitizerModuleChecker checker = new JavetSanitizerModuleChecker(option);
        assertTrue(
                checker.check("function a() {} function $b($c) {}"),
                "Function a() and $b($c) should pass.");
        assertEquals(2, checker.getFunctionParserMap().size(), "There should be 2 functions.");
        assertTrue(checker.getFunctionParserMap().containsKey("a"), "a() should be found.");
        assertTrue(checker.getFunctionParserMap().containsKey("$b"), "$b() should be found.");
        option = JavetSanitizerOption.Default.toClone()
                .setKeywordImportEnabled(true)
                .seal();
        checker = new JavetSanitizerModuleChecker(option);
        assertTrue(
                checker.check("import { x } from 'x.mjs'; function main() {}"),
                "Dynamic import should pass.");
        assertEquals(1, checker.getFunctionParserMap().size(), "There should be 1 functions.");
        assertTrue(checker.getFunctionParserMap().containsKey("main"), "main() should be found.");
    }
}
