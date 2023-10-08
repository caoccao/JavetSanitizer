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

import java.util.ArrayList;
import java.util.List;

public class JavaScriptStatementListParser
        extends BaseJavaScriptContextParser<JavaScriptStatementListParser, JavaScriptParser.StatementListContext> {
    public JavaScriptStatementListParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    public JavaScriptStatementListParser(
            JavaScriptParser.StatementListContext context,
            String codeString) throws JavetSanitizerException {
        super(context, codeString);
    }

    public List<JavaScriptStatementParser> getJavaScriptStatementParsers() throws JavetSanitizerException {
        List<JavaScriptStatementParser> parsers = new ArrayList<>();
        for (JavaScriptParser.StatementContext statementContext : context.statement()) {
            parsers.add(new JavaScriptStatementParser(statementContext, codeString));
        }
        return parsers;
    }

    @Override
    protected JavaScriptStatementListParser initializeContext() throws JavetSanitizerException {
        super.initializeContext();
        if (context == null) {
            context = javaScriptParser.statementList();
        }
        return this;
    }

    @Override
    public JavaScriptStatementListParser parse() throws JavetSanitizerException {
        return super.parse()
                .validateChildNotEmpty()
                .validateChildClass(JavaScriptParser.StatementContext.class);
    }
}
