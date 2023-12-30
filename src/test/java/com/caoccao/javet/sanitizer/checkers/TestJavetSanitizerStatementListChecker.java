/*
 * Copyright (c) 2023-2024. caoccao.com Sam Cao
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJavetSanitizerStatementListChecker extends BaseTestJavetSanitizerChecker {
    @Test
    public void testInvalidIdentifiers() {
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"))
                .seal();
        invalidIdentifierCodeStringMap.forEach((key, value) -> {
            String statement = "function main() { " + key + " }";
            assertException(
                    () -> new JavetSanitizerStatementListChecker(option).check(statement),
                    100, "Identifier " + value + " is not allowed.",
                    null);
        });
    }

    @Test
    public void testInvalidStatements() {
        SimpleList.of("", "   ", null).forEach(statement ->
                assertException(
                        () -> new JavetSanitizerStatementListChecker().check(statement),
                        2, "The JavaScript code is empty.",
                        null));
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("(async function() {})()"),
                101, "Keyword async is not allowed.",
                "Source Code: async function() {}\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 1, 20\n" +
                        "Position: 1, 20");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("async () => {}"),
                101, "Keyword async is not allowed.",
                "Source Code: async () => {}\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 14\n" +
                        "Position: 0, 14");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("function a() { await 1; }"),
                101, "Keyword await is not allowed.",
                "Source Code: await 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 15, 22\n" +
                        "Position: 15, 22");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("function a() { debugger }"),
                101, "Keyword debugger is not allowed.",
                "Source Code: debugger\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 15, 23\n" +
                        "Position: 15, 23");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("function a() {\n" +
                        "  import { x } from 'x.mjs';\n" +
                        "  return 1;\n" +
                        "}"),
                101, "Keyword import is not allowed.",
                "Source Code: import { x } from 'x.mjs';\n" +
                        "Line Number: 2, 2\n" +
                        "Column: 2, 28\n" +
                        "Position: 17, 43");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("var a = 1;"),
                101, "Keyword var is not allowed.",
                "Source Code: var\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 3\n" +
                        "Position: 0, 3");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("let Object = 1;"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object = 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 4, 14\n" +
                        "Position: 4, 14");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("Object = 1;"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object = 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 10\n" +
                        "Position: 0, 10");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("Object += 1;"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object += 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 11\n" +
                        "Position: 0, 11");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("[Object, JSON] = [1, 2];"),
                100, "Identifier Object is not allowed.",
                "Source Code: [Object, JSON] = [1, 2]\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 23\n" +
                        "Position: 0, 23");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("try {} catch (Object) {}"),
                100, "Identifier Object is not allowed.",
                "Source Code: catch (Object) {}\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 7, 24\n" +
                        "Position: 7, 24");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("(Object) => {}"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 1, 7\n" +
                        "Position: 1, 7");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("function Object() {}"),
                100, "Identifier Object is not allowed.",
                "Source Code: function Object() {}\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 20\n" +
                        "Position: 0, 20");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("class Object {}"),
                100, "Identifier Object is not allowed.",
                "Source Code: class Object {}\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 15\n" +
                        "Position: 0, 15");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("Object.a = 1;"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object.a = 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 12\n" +
                        "Position: 0, 12");
        assertException(
                () -> new JavetSanitizerStatementListChecker().check("Object['a'] = 1;"),
                100, "Identifier Object is not allowed.",
                "Source Code: Object['a'] = 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 15\n" +
                        "Position: 0, 15");
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"));
        option.getReservedIdentifierSet().add("$c");
        option.getReservedMutableIdentifierSet().add("$c");
        option.seal();
        assertException(
                () -> new JavetSanitizerStatementListChecker(option).check("$abc.a = 1;"),
                100, "Identifier $abc is not allowed.",
                "Source Code: $abc.a = 1\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 10\n" +
                        "Position: 0, 10");
        assertException(
                () -> new JavetSanitizerStatementListChecker(option).check("a.$abc = 1;"),
                100, "Identifier $abc is not allowed.",
                "Source Code: $abc\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 2, 6\n" +
                        "Position: 2, 6");
    }

    @Test
    public void testValidStatements() throws JavetSanitizerException {
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setReservedIdentifierMatcher(identifier -> identifier.startsWith("$"));
        option.getReservedIdentifierSet().add("$c");
        option.getReservedMutableIdentifierSet().add("$c");
        option.seal();
        List<String> statements = SimpleList.of(
                "function a() {}",
                "const a = 1;",
                "const a = Object;",
                "class A { constructor() { /* constructor */ } }",
                "test(Object)",
                "$c.a = 1",
                "$c['a'] = 1",
                "const a = b?.c?.d?.e?.f;",
                "const a = b?.['c']?.d?.['e']?.f;",
                "x?.['abc'];",
                "x.map(y => { return y?.['abc']; });");
        for (String statement : statements) {
            assertTrue(
                    new JavetSanitizerStatementListChecker(option).check(statement),
                    statement + " should pass.");
        }
    }
}
