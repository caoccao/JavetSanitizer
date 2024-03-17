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

public class TestJavetSanitizerSingleExpressionChecker extends BaseTestJavetSanitizerChecker {
    @Test
    public void testInvalidStatements() {
        SimpleList.of("", "   ", null).forEach(statement ->
                assertException(
                        () -> new JavetSanitizerSingleExpressionChecker().check(statement),
                        2, "The JavaScript code is empty.",
                        null));
        assertException(
                () -> new JavetSanitizerSingleExpressionChecker().check(";;;"),
                200, "Token ; is invalid. Expecting {RegularExpressionLiteral, '[', '(', '{', '++', '--', '+', '-', '~', '!', 'null', BooleanLiteral, DecimalLiteral, HexIntegerLiteral, OctalIntegerLiteral, OctalIntegerLiteral2, BinaryIntegerLiteral, BigHexIntegerLiteral, BigOctalIntegerLiteral, BigBinaryIntegerLiteral, BigDecimalIntegerLiteral, 'break', 'do', 'instanceof', 'typeof', 'case', 'else', 'new', 'var', 'catch', 'finally', 'return', 'void', 'continue', 'for', 'switch', 'while', 'debugger', 'function', 'this', 'with', 'default', 'if', 'throw', 'delete', 'in', 'try', 'as', 'from', 'of', 'yield', 'yield*', 'class', 'enum', 'extends', 'super', 'const', 'export', 'import', 'async', 'await', 'implements', StrictLet, NonStrictLet, 'private', 'public', 'interface', 'package', 'protected', 'static', Identifier, StringLiteral, BackTick}.",
                "Source Code: ;\n" +
                        "Line Number: 1, 1\n" +
                        "Column: 0, 1\n" +
                        "Position: 0, 1");
    }

    @Test
    public void testValidStatements() throws JavetSanitizerException {
        List<String> statements = SimpleList.of(
                "() => 1", "() => {}", "(a, b) => {}", "1 +",
                "function() {}", "function(a, b) {}", "function a() {}",
                "1", "'a'", "1 + 1", "a == b", "[1,2,3]", "{ a: 1, b: 2, c: 3}",
                "a?.b.?.c", "a?.['b']", "a?.b()");
        for (String statement : statements) {
            assertTrue(
                    new JavetSanitizerSingleExpressionChecker().check(statement),
                    statement + " should pass.");
        }
    }
}
