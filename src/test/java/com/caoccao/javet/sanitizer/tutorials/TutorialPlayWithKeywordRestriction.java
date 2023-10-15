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

package com.caoccao.javet.sanitizer.tutorials;

import com.caoccao.javet.sanitizer.checkers.JavetSanitizerModuleChecker;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;

public class TutorialPlayWithKeywordRestriction {
    public static void main(String[] args) {
        String codeString = "import { x } from 'x.mjs'; function main() {}";
        // Check the script with the default option.
        try {
            new JavetSanitizerModuleChecker().check(codeString);
        } catch (JavetSanitizerException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getContext());
        }
        System.out.println("----------------------------------------");
        // Create a new option with keyword import enabled.
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setKeywordImportEnabled(true)
                .seal();
        // Check the script with the new option.
        try {
            new JavetSanitizerModuleChecker(option).check(codeString);
            System.out.println(codeString + " // Valid");
        } catch (JavetSanitizerException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getContext());
        }
    }
}
