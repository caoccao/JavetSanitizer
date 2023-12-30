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
import com.caoccao.javet.sanitizer.parsers.BaseJavaScriptContextParser;
import com.caoccao.javet.sanitizer.utils.StringUtils;

import java.util.Objects;

/**
 * The type Base javet sanitizer checker.
 *
 * @param <Parser> the type parameter
 * @since 0.1.0
 */
public abstract class BaseJavetSanitizerChecker
        <Parser extends BaseJavaScriptContextParser<Parser, ?>>
        implements IJavetSanitizerChecker {
    /**
     * The Option.
     *
     * @since 0.1.0
     */
    protected JavetSanitizerOption option;
    /**
     * The Root parser.
     *
     * @since 0.1.0
     */
    protected Parser rootParser;

    /**
     * Instantiates a new Base javet sanitizer checker.
     *
     * @since 0.1.0
     */
    public BaseJavetSanitizerChecker() {
        this(JavetSanitizerOption.Default);
    }

    /**
     * Instantiates a new Base javet sanitizer checker.
     *
     * @param option the option
     * @since 0.1.0
     */
    public BaseJavetSanitizerChecker(JavetSanitizerOption option) {
        this.option = Objects.requireNonNull(option);
        reset();
    }

    @Override
    public boolean check(String codeString) throws JavetSanitizerException {
        reset();
        validateBlank(codeString);
        return true;
    }

    @Override
    public JavetSanitizerOption getOption() {
        return option;
    }

    /**
     * Reset.
     *
     * @since 0.1.0
     */
    protected void reset() {
        rootParser = null;
    }

    /**
     * Validate blank.
     *
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    protected void validateBlank(String codeString) throws JavetSanitizerException {
        if (StringUtils.isBlank(codeString)) {
            throw JavetSanitizerException.emptyCodeString();
        }
    }
}
