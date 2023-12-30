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
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The type JavaScript function declaration parser.
 *
 * @since 0.1.0
 */
public class JavaScriptFunctionDeclarationParser
        extends BaseJavaScriptContextParser<JavaScriptFunctionDeclarationParser, JavaScriptParser.FunctionDeclarationContext> {
    /**
     * The Formal parameter list context.
     *
     * @since 0.1.0
     */
    protected JavaScriptParser.FormalParameterListContext formalParameterListContext;
    /**
     * The Function body context.
     *
     * @since 0.1.0
     */
    protected JavaScriptParser.FunctionBodyContext functionBodyContext;
    /**
     * The Identifier context.
     *
     * @since 0.1.0
     */
    protected JavaScriptParser.IdentifierContext identifierContext;

    /**
     * Instantiates a new JavaScript function declaration parser.
     *
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptFunctionDeclarationParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    /**
     * Instantiates a new JavaScript function declaration parser.
     *
     * @param context    the context
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptFunctionDeclarationParser(
            JavaScriptParser.FunctionDeclarationContext context,
            String codeString) throws JavetSanitizerException {
        super(context, codeString);
        formalParameterListContext = null;
        functionBodyContext = null;
        identifierContext = null;
    }

    /**
     * Gets formal parameter list context.
     *
     * @return the formal parameter list context
     * @since 0.1.0
     */
    public JavaScriptParser.FormalParameterListContext getFormalParameterListContext() {
        return formalParameterListContext;
    }

    /**
     * Gets function body context.
     *
     * @return the function body context
     * @since 0.1.0
     */
    public JavaScriptParser.FunctionBodyContext getFunctionBodyContext() {
        return functionBodyContext;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     * @since 0.1.0
     */
    public String getIdentifier() {
        if (identifierContext != null) {
            TerminalNode terminalNode = identifierContext.Identifier();
            if (terminalNode != null) {
                return terminalNode.getText();
            }
        }
        return null;
    }

    /**
     * Gets identifier context.
     *
     * @return the identifier context
     * @since 0.1.0
     */
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

    /**
     * Validate argument count equals javaScript function declaration parser.
     *
     * @param expectedCount the expected count
     * @return the JavaScript function declaration parser
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
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
