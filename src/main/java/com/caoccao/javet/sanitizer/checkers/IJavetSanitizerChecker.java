/*
 * Copyright (c) 2023-2023. caoccao.com Sam Cao
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

/**
 * The interface Javet sanitizer checker.
 *
 * @since 0.1.0
 */
public interface IJavetSanitizerChecker {
    /**
     * Check the given code string.
     *
     * @param codeString the code string
     * @return true : checked, false : not checked
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    boolean check(String codeString) throws JavetSanitizerException;

    /**
     * Gets option.
     *
     * @return the option
     * @since 0.1.0
     */
    JavetSanitizerOption getOption();
}
