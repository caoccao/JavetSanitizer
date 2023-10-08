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
import org.antlr.v4.runtime.tree.TerminalNode;

public class JavaScriptFunctionDeclarationParser
        extends BaseJavaScriptContextParser<JavaScriptFunctionDeclarationParser, JavaScriptParser.FunctionDeclarationContext> {
    protected JavaScriptParser.FormalParameterListContext formalParameterListContext;
    protected JavaScriptParser.FunctionBodyContext functionBodyContext;
    protected JavaScriptParser.IdentifierContext identifierContext;

    public JavaScriptFunctionDeclarationParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    public JavaScriptFunctionDeclarationParser(
            JavaScriptParser.FunctionDeclarationContext context,
            String codeString) throws JavetSanitizerException {
        super(context, codeString);
        formalParameterListContext = null;
        functionBodyContext = null;
        identifierContext = null;
    }

    public JavaScriptParser.FormalParameterListContext getFormalParameterListContext() {
        return formalParameterListContext;
    }

    public JavaScriptParser.FunctionBodyContext getFunctionBodyContext() {
        return functionBodyContext;
    }

    public String getIdentifier() {
        if (identifierContext != null) {
            TerminalNode terminalNode = identifierContext.Identifier();
            if (terminalNode != null) {
                return terminalNode.getText();
            }
        }
        return null;
    }

    public JavaScriptParser.IdentifierContext getIdentifierContext() {
        return identifierContext;
    }

    @Override
    protected JavaScriptFunctionDeclarationParser initializeContext() throws JavetSanitizerException {
        super.initializeContext();
        if (context == null) {
            context = javaScriptParser.functionDeclaration();
        }
        return this;
    }

    @Override
    public JavaScriptFunctionDeclarationParser parse() throws JavetSanitizerException {
        super.parse();
        formalParameterListContext = context.formalParameterList();
        functionBodyContext = context.functionBody();
        identifierContext = context.identifier();
        return this;
    }

    public JavaScriptFunctionDeclarationParser validateArgumentCountEquals(int expectedCount) throws JavetSanitizerException {
        int actualCount = 0;
        if (formalParameterListContext != null) {
            actualCount = formalParameterListContext.formalParameterArg().size();
        }
        if (actualCount != expectedCount) {
            throw JavetSanitizerException.argumentCountMismatch(expectedCount, actualCount)
                    .setContext(toJavaScriptParserContext(context));
        }
        return this;
    }
}
