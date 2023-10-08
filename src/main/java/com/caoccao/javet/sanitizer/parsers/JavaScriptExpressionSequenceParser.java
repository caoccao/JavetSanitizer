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

package com.caoccao.javet.sanitizer.parsers;

import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;

/**
 * The type JavaScript expression sequence parser.
 *
 * @since 0.1.0
 */
public class JavaScriptExpressionSequenceParser
        extends BaseJavaScriptContextParser<JavaScriptExpressionSequenceParser, JavaScriptParser.ExpressionSequenceContext> {
    /**
     * Instantiates a new JavaScript expression sequence parser.
     *
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptExpressionSequenceParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    /**
     * Instantiates a new JavaScript expression sequence parser.
     *
     * @param context    the context
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptExpressionSequenceParser(
            JavaScriptParser.ExpressionSequenceContext context,
            String codeString)
            throws JavetSanitizerException {
        super(context, codeString);
    }

    @Override
    protected JavaScriptExpressionSequenceParser initializeContext() throws JavetSanitizerException {
        super.initializeContext();
        if (context == null) {
            context = javaScriptParser.expressionSequence();
        }
        return this;
    }

    @Override
    public JavaScriptExpressionSequenceParser parse() throws JavetSanitizerException {
        return super.parse()
                .validateChildNotEmpty();
    }
}
