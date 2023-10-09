/*
 * Copyright (c) 2023. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed, in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caoccao.javet.sanitizer.checkers;

import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.utils.SimpleMap;
import org.junit.jupiter.api.function.Executable;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BaseTestJavetSanitizerChecker {
    protected static final Map<String, String> invalidIdentifierCodeStringMap = SimpleMap.of(
            "const a = Object.__proto__;", "__proto__",
            "const a = new AsyncFunction();", "AsyncFunction",
            "const a = new AsyncGenerator();", "AsyncGenerator",
            "const a = new AsyncGeneratorFunction();", "AsyncGeneratorFunction",
            "clearInterval()", "clearInterval",
            "clearTimeout()", "clearTimeout",
            "defineProperty()", "defineProperty",
            "defineProperties()", "defineProperties",
            "eval()", "eval",
            "const a = Function('abc')", "Function",
            "global()", "global",
            "globalThis()", "globalThis",
            "getPrototypeOf()", "getPrototypeOf",
            "const a = new Generator();", "Generator",
            "const a = new GeneratorFunction();", "GeneratorFunction",
            "const a = Intl.a;", "Intl",
            "const a = Object.prototype;", "prototype",
            "const a = new Proxy();", "Proxy",
            "const a = new Promise();", "Promise",
            "const a = require('abc');", "require",
            "const a = Reflect;", "Reflect",
            "setImmediate()", "setImmediate",
            "setInterval()", "setInterval",
            "setTimeout()", "setTimeout",
            "const a = Object.setPrototypeOf;", "setPrototypeOf",
            "const a = Symbol.toPrimitive;", "Symbol",
            "uneval()", "uneval",
            "const a = new XMLHttpRequest();", "XMLHttpRequest",
            "const a = new WebAssembly();", "WebAssembly",
            "const a = window;", "window",
            "class $abc {}", "$abc",
            "function $abc() {}", "$abc");

    protected void assertException(Executable executable, int errorCode, String errorMessage, String contextString) {
        JavetSanitizerException exception = assertThrows(JavetSanitizerException.class, executable);
        assertEquals(errorCode, exception.getError().getCode());
        assertEquals(errorMessage, exception.getMessage());
        if (contextString != null) {
            assertEquals(contextString, exception.getContext().toString());
        }
    }
}
