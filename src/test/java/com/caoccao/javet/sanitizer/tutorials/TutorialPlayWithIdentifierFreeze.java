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

package com.caoccao.javet.sanitizer.tutorials;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.exceptions.JavetExecutionException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.sanitizer.codegen.JavetSanitizerFridge;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;

public class TutorialPlayWithIdentifierFreeze {
    public static void main(String[] args) {
        try (V8Runtime v8Runtime = V8Host.getV8Instance().createV8Runtime()) {
            // Initialize V8 with the default option.
            String codeString = JavetSanitizerFridge.generate(JavetSanitizerOption.Default);
            v8Runtime.getExecutor(codeString).executeVoid();
            codeString = "JSON.stringify = (str) => {}";
            v8Runtime.getExecutor(codeString).setResourceName("test.js").executeVoid();
        } catch (JavetExecutionException e) {
            System.out.println(e.getScriptingError());
        } catch (JavetException ignored) {
        }

        System.out.println("----------------------------------------");

        // Create a new option with JSON allowed.
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone();
        option.getToBeFrozenIdentifierList().remove("JSON");
        option.seal();
        try (V8Runtime v8Runtime = V8Host.getV8Instance().createV8Runtime()) {
            // Initialize V8 with the new option.
            String codeString = JavetSanitizerFridge.generate(option);
            v8Runtime.getExecutor(codeString).executeVoid();
            codeString = "JSON.stringify = (str) => {}";
            v8Runtime.getExecutor(codeString).setResourceName("test.js").executeVoid();
            System.out.println(codeString + " // Valid");
        } catch (JavetExecutionException e) {
            System.out.println(e.getScriptingError());
        } catch (JavetException ignored) {
        }
    }
}
