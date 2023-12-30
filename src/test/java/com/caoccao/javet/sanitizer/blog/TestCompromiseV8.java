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

package com.caoccao.javet.sanitizer.blog;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.exceptions.JavetExecutionException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.sanitizer.checkers.JavetSanitizerModuleChecker;
import com.caoccao.javet.sanitizer.checkers.JavetSanitizerSingleExpressionChecker;
import com.caoccao.javet.sanitizer.checkers.JavetSanitizerStatementListChecker;
import com.caoccao.javet.sanitizer.codegen.JavetSanitizerFridge;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * https://sjtucaocao.medium.com/how-to-compromise-v8-on-jvm-ceb385572461
 */
public class TestCompromiseV8 {
    protected V8Runtime getV8Runtime(FridgeStatus fridgeStatus) throws JavetException {
        V8Runtime v8Runtime = V8Host.getV8Instance().createV8Runtime();
        if (fridgeStatus == FridgeStatus.Enabled) {
            v8Runtime.getExecutor(JavetSanitizerFridge.generate(JavetSanitizerOption.Default)).executeVoid();
        }
        return v8Runtime;
    }

    @Test
    public void testCallApplyBindCall() {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier apply is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("a.apply(b);"),
                        "apply should not pass the check.")
                        .getMessage());
        assertEquals(
                "Identifier bind is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("a.bind(b);"),
                        "bind should not pass the check.")
                        .getMessage());
        assertEquals(
                "Identifier call is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("a.call(b);"),
                        "call should not pass the check.")
                        .getMessage());
    }

    @Test
    public void testCallEval() throws JavetException {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier eval is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("eval('1');"),
                        "eval should not pass the check.")
                        .getMessage());
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Enabled)) {
            assertEquals(
                    "ReferenceError: eval is not defined",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor("eval('1');").executeVoid(),
                            "eval should be inaccessible in V8.")
                            .getMessage());
        }
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Disabled)) {
            assertEquals(
                    1,
                    v8Runtime.getExecutor("eval('1');").executeInteger(),
                    "eval should be executed successfully in V8.");
            v8Runtime.allowEval(false);
            assertEquals(
                    "EvalError: Code generation from strings disallowed for this context",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor("eval('1');").executeVoid(),
                            "eval should be disallowed.")
                            .getMessage());
        }
    }

    @Test
    public void testJSFxxk() throws JavetException {
        // Object = 1; Object;
        String codeString = "[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]][([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]]((!![]+[])[+!+[]]+(!![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+([][[]]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+!+[]]+(+[![]]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+!+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(+(!+[]+!+[]+!+[]+[+!+[]]))[(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([]+[])[([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]][([][[]]+[])[+!+[]]+(![]+[])[+!+[]]+((+[])[([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]]+[])[+!+[]+[+!+[]]]+(!![]+[])[!+[]+!+[]+!+[]]]](!+[]+!+[]+!+[]+[!+[]+!+[]])+(![]+[])[+!+[]]+(![]+[])[!+[]+!+[]])()((+[]+[][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()[([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]])[+!+[]+[+[]]]+([][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()+[])[!+[]+!+[]]+([][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()+[])[!+[]+!+[]+!+[]]+(!![]+[])[!+[]+!+[]+!+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(+[![]]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+!+[]]]+([]+[])[(![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(![]+[])[!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]]()[+!+[]+[+!+[]]]+(+[![]]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+!+[]]]+[+!+[]]+([]+[])[(![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(![]+[])[!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]](+[![]]+([]+[])[(![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(![]+[])[!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]]()[+!+[]+[!+[]+!+[]]])[!+[]+!+[]+[+!+[]]]+(+[![]]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+!+[]]]+(+[]+[][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()[([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(![]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([][[]]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]])[+!+[]+[+[]]]+([][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()+[])[!+[]+!+[]]+([][(!![]+[])[!+[]+!+[]+!+[]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+(!![]+[])[+!+[]]+([![]]+[][[]])[+!+[]+[+[]]]+(!![]+[])[!+[]+!+[]+!+[]]+(![]+[])[!+[]+!+[]+!+[]]]()+[])[!+[]+!+[]+!+[]]+(!![]+[])[!+[]+!+[]+!+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[])[+[]]+([]+[])[(![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(![]+[])[!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]](+[![]]+([]+[])[(![]+[])[+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+([][[]]+[])[+!+[]]+(!![]+[])[+[]]+([][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]]+[])[!+[]+!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(![]+[])[!+[]+!+[]]+(!![]+[][(![]+[])[+[]]+(![]+[])[!+[]+!+[]]+(![]+[])[+!+[]]+(!![]+[])[+[]]])[+!+[]+[+[]]]+(!![]+[])[+!+[]]]()[+!+[]+[!+[]+!+[]]])[!+[]+!+[]+[+!+[]]])";
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Enabled)) {
            assertEquals(
                    "ReferenceError: eval is not defined",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor(codeString).executeVoid(),
                            "eval should be disallowed.")
                            .getMessage());
        }
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Disabled)) {
            assertEquals(
                    1,
                    v8Runtime.getExecutor(codeString).executeInteger(),
                    "The obfuscated code should pass V8.");
            v8Runtime.allowEval(false);
            assertEquals(
                    "EvalError: Code generation from strings disallowed for this context",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor(codeString).executeVoid(),
                            "eval should be disallowed.")
                            .getMessage());
        }
    }

    @Test
    public void testKeywordAsyncAwait() {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier Promise is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("new Promise((resolve, reject) => {};"),
                        "Promise should not pass the check.")
                        .getMessage());
        assertEquals(
                "Keyword async is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("async function a() {}"),
                        "async should not pass the check.")
                        .getMessage());
        assertEquals(
                "Keyword await is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("await f();"),
                        "await should not pass the check.")
                        .getMessage());
    }

    @Test
    public void testKeywordVarLetConst() throws JavetSanitizerException {
        JavetSanitizerStatementListChecker checker = new JavetSanitizerStatementListChecker();
        assertEquals(
                "Keyword var is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("var a = 1;"),
                        "var should not pass the check.")
                        .getMessage());
        assertTrue(checker.check("let a = 1;"), "let should pass the check.");
        assertTrue(checker.check("const a = 1;"), "const should pass the check.");
    }

    @Test
    public void testModules() throws JavetSanitizerException {
        {
            JavetSanitizerModuleChecker checker = new JavetSanitizerModuleChecker();
            assertEquals(
                    "Token VariableStatementContext is invalid. Expecting FunctionDeclarationContext.",
                    assertThrows(
                            JavetSanitizerException.class,
                            () -> checker.check("const a = 1;"),
                            "Variable statement should not pass the check.")
                            .getMessage());
            assertEquals(
                    "Function main is not found.",
                    assertThrows(
                            JavetSanitizerException.class,
                            () -> checker.check("function a() {}"),
                            "main() should be reported missing.")
                            .getMessage());
            assertTrue(checker.check("function main() {} function a() {}"), "main() should pass the check.");
            assertEquals(2, checker.getFunctionParserMap().size(), "There should be 2 functions.");
            assertTrue(checker.getFunctionParserMap().containsKey("main"), "main() should be found.");
            assertTrue(checker.getFunctionParserMap().containsKey("a"), "a() should be found.");
            assertEquals(
                    "Keyword import is not allowed.",
                    assertThrows(
                            JavetSanitizerException.class,
                            () -> checker.check("import { a } from 'a.js'; function main() {}"),
                            "import should not pass the check.")
                            .getMessage());
        }
        {
            JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                    .setKeywordImportEnabled(true)
                    .seal();
            JavetSanitizerModuleChecker checker = new JavetSanitizerModuleChecker(option);
            assertTrue(
                    checker.check("import { x } from 'x.mjs'; function main() {}"),
                    "Dynamic import should pass.");
            assertEquals(1, checker.getFunctionParserMap().size(), "There should be 1 functions.");
            assertTrue(checker.getFunctionParserMap().containsKey("main"), "main() should be found.");
            assertEquals(
                    "Token ImportStatementContext is invalid. Expecting FunctionDeclarationContext.",
                    assertThrows(
                            JavetSanitizerException.class,
                            () -> checker.check("function main() {} import { a } from 'a.js';"),
                            "import should not pass the check.")
                            .getMessage());
        }
    }

    @Test
    public void testUpdateBuiltInObjects() throws JavetException {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier Object is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("Object = 1;"),
                        "Object should not pass the check.")
                        .getMessage());
        assertEquals(
                "Identifier Object is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("Object.a = 1;"),
                        "Object.a should not pass the check.")
                        .getMessage());
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Enabled)) {
            assertEquals(
                    "TypeError: Assignment to constant variable.",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor("Object = 1;").executeVoid(),
                            "Object should be immutable.")
                            .getMessage());
        }
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Disabled)) {
            assertEquals(
                    1,
                    v8Runtime.getExecutor("Object = 1; Object").executeInteger(),
                    "Object should be updated.");
        }
    }

    @Test
    public void testUpdateGlobal() {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier globalThis is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("globalThis.a = 1;"),
                        "globalThis should be inaccessible.")
                        .getMessage());
        assertEquals(
                "Identifier global is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("global.a = 1;"),
                        "global should be inaccessible.")
                        .getMessage());
    }

    @Test
    public void testUpdatePrototype() throws JavetException {
        JavetSanitizerSingleExpressionChecker checker = new JavetSanitizerSingleExpressionChecker();
        assertEquals(
                "Identifier prototype is not allowed.",
                assertThrows(
                        JavetSanitizerException.class,
                        () -> checker.check("A.prototype.x = () => {};"),
                        "prototype should not pass the check.")
                        .getMessage());
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Enabled)) {
            assertEquals(
                    "TypeError: Cannot add property size, object is not extensible",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor(
                                            "Array.prototype.size = function() { return this.length; }")
                                    .executeVoid(),
                            "Array.prototype should be immutable.")
                            .getMessage());
            assertEquals(
                    "TypeError: Cannot add property size, object is not extensible",
                    assertThrows(
                            JavetExecutionException.class,
                            () -> v8Runtime.getExecutor(
                                            "Array['prototype'].size = function() { return this.length; }")
                                    .executeVoid(),
                            "Array['prototype'] should be immutable.")
                            .getMessage());
        }
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Disabled)) {
            assertEquals(
                    2,
                    v8Runtime.getExecutor(
                                    "Array.prototype.size = function() { return this.length; };" +
                                            "[1, 2].size();")
                            .executeInteger(),
                    "Array.prototype should be updated.");
        }
        try (V8Runtime v8Runtime = getV8Runtime(FridgeStatus.Disabled)) {
            assertEquals(
                    2,
                    v8Runtime.getExecutor(
                                    "Array['prototype'].size = function() { return this.length; };" +
                                            "[1, 2].size();")
                            .executeInteger(),
                    "Array['prototype'] should be updated.");
        }
    }

    public enum FridgeStatus {
        Disabled,
        Enabled,
    }
}
