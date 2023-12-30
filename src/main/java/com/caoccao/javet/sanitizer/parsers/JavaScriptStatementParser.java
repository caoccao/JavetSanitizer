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

package com.caoccao.javet.sanitizer.parsers;


import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;

/**
 * The type JavaScript statement parser.
 *
 * @since 0.1.0
 */
public class JavaScriptStatementParser
        extends BaseJavaScriptContextParser<JavaScriptStatementParser, JavaScriptParser.StatementContext> {
    /**
     * Instantiates a new JavaScript statement parser.
     *
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptStatementParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    /**
     * Instantiates a new JavaScript statement parser.
     *
     * @param context    the context
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptStatementParser(
            JavaScriptParser.StatementContext context,
            String codeString)
            throws JavetSanitizerException {
        super(context, codeString);
    }

    @Override
    protected JavaScriptStatementParser initializeContext() throws JavetSanitizerException {
        super.initializeContext();
        if (context == null) {
            context = javaScriptParser.statement();
        }
        return this;
    }
}
