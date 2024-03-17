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

public class TestJavetSanitizerExpressionSequenceChecker extends BaseTestJavetSanitizerChecker {
    @Test
    public void testInvalidStatements() {
        SimpleList.of("", "   ", null).forEach(statement ->
                assertException(
                        () -> new JavetSanitizerExpressionSequenceChecker().check(statement),
                        2, "The JavaScript code is empty.",
                        null));
        assertException(
                () -> new JavetSanitizerExpressionSequenceChecker().check("1 +"),
                200, "Token + is invalid. Expecting {<EOF>, ','}.",
                "Source Code: +\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 2, 3\n" +
                        "Position: 2, 3");
    }

    @Test
    public void testValidStatements() throws JavetSanitizerException {
        List<String> statements = SimpleList.of(
                "1", "'a'", "1 + 1", "a == b", "[1,2,3]", "{ a: 1, b: 2, c: 3}",
                "() => 1", "() => {}", "(a, b) => {}",
                "function() {}", "function(a, b) {}", "function x() {}", "function x(a, b) {}");
        for (String statement : statements) {
            assertTrue(
                    new JavetSanitizerExpressionSequenceChecker().check(statement),
                    statement + " should pass.");
        }
    }
}
