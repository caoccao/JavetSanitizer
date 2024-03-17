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
import com.caoccao.javet.sanitizer.utils.SimpleList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJavetSanitizerAnonymousFunctionChecker extends BaseTestJavetSanitizerChecker {
    @Test
    public void testInvalidStatements() {
        SimpleList.of("", "   ", null).forEach(statement ->
                assertException(
                        () -> new JavetSanitizerAnonymousFunctionChecker().check(statement),
                        2, "The JavaScript code is empty.",
                        null));
        assertException(
                () -> new JavetSanitizerAnonymousFunctionChecker().check("const a;"),
                200, "Token a is invalid. Expecting '=>'.",
                "Source Code: a\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 6, 7\n" +
                        "Position: 6, 7");
        assertException(
                () -> new JavetSanitizerAnonymousFunctionChecker().check("function() => {}"),
                200, "Token => is invalid. Expecting '{'.",
                "Source Code: =>\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 11, 13\n" +
                        "Position: 11, 13");
        assertException(
                () -> new JavetSanitizerAnonymousFunctionChecker().check("function abc() => {}"),
                200, "Token => is invalid. Expecting '{'.",
                "Source Code: =>\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 15, 17\n" +
                        "Position: 15, 17");
    }

    @Test
    public void testValidStatements() throws JavetSanitizerException {
        List<String> statements = SimpleList.of(
                "() => 1", "() => {}", "(a, b) => {}",
                "function() {}", "function(a, b) {}",
                // The following cases are invalid. They are grammars v4 bugs.
                "function x() {}", "function x(a, b) {}");
        for (String statement : statements) {
            assertTrue(
                    new JavetSanitizerAnonymousFunctionChecker().check(statement),
                    statement + " should pass.");
        }
    }
}
