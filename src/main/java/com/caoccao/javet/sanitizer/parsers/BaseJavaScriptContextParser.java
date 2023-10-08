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

import com.caoccao.javet.sanitizer.antlr.JavaScriptLexer;
import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerErrorContext;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.listeners.JavetSanitizerListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Objects;

/**
 * The type Base JavaScript context parser.
 *
 * @param <Parser>  the type parameter
 * @param <Context> the type parameter
 * @since 0.1.0
 */
@SuppressWarnings("unchecked")
public abstract class BaseJavaScriptContextParser<
        Parser extends BaseJavaScriptContextParser<Parser, Context>,
        Context extends ParserRuleContext> {
    /**
     * The Code string.
     *
     * @since 0.1.0
     */
    protected String codeString;
    /**
     * The Context.
     *
     * @since 0.1.0
     */
    protected Context context;
    /**
     * The JavaScript lexer.
     *
     * @since 0.1.0
     */
    protected JavaScriptLexer javaScriptLexer;
    /**
     * The JavaScript parser.
     *
     * @since 0.1.0
     */
    protected JavaScriptParser javaScriptParser;
    /**
     * The Token stream.
     *
     * @since 0.1.0
     */
    protected TokenStream tokenStream;

    /**
     * Instantiates a new Base JavaScript context parser.
     *
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public BaseJavaScriptContextParser(String codeString) throws JavetSanitizerException {
        this(null, codeString);
    }

    /**
     * Instantiates a new Base JavaScript context parser.
     *
     * @param context    the context
     * @param codeString the code string
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public BaseJavaScriptContextParser(Context context, String codeString) throws JavetSanitizerException {
        this.codeString = Objects.requireNonNull(codeString);
        this.context = context;
        javaScriptLexer = null;
        javaScriptParser = null;
        tokenStream = null;
    }

    /**
     * Gets code string.
     *
     * @return the code string
     * @since 0.1.0
     */
    public String getCodeString() {
        return codeString;
    }

    /**
     * Gets context.
     *
     * @return the context
     * @since 0.1.0
     */
    public Context getContext() {
        return context;
    }

    /**
     * Gets JavaScript function declaration parser.
     *
     * @param index the index
     * @return the JavaScript function declaration parser
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptFunctionDeclarationParser getJavaScriptFunctionDeclarationParser(int index)
            throws JavetSanitizerException {
        return new JavaScriptFunctionDeclarationParser(
                context.getRuleContext(JavaScriptParser.FunctionDeclarationContext.class, index),
                getCodeString());
    }

    /**
     * Gets JavaScript statement parser.
     *
     * @param index the index
     * @return the JavaScript statement parser
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public JavaScriptStatementParser getJavaScriptStatementParser(int index) throws JavetSanitizerException {
        return new JavaScriptStatementParser(
                context.getRuleContext(JavaScriptParser.StatementContext.class, index),
                getCodeString());
    }

    /**
     * Initialize context parser.
     *
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    protected Parser initializeContext() throws JavetSanitizerException {
        if (context == null) {
            javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(codeString));
            javaScriptLexer.setUseStrictDefault(true);
            tokenStream = new CommonTokenStream(javaScriptLexer);
            javaScriptParser = new JavaScriptParser(tokenStream);
            javaScriptParser.setBuildParseTree(true);
            javaScriptParser.setErrorHandler(new BailErrorStrategy());
        }
        return (Parser) this;
    }

    /**
     * Is child class boolean.
     *
     * @param index              the index
     * @param expectedChildClass the expected child class
     * @return the boolean
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public boolean isChildClass(int index, Class<?> expectedChildClass) throws JavetSanitizerException {
        return context.getChild(index).getClass() == expectedChildClass;
    }

    /**
     * Parse parser.
     *
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser parse() throws JavetSanitizerException {
        try {
            initializeContext();
        } catch (Throwable t) {
            throw toJavetSanitizerExceptionException(t);
        }
        return (Parser) this;
    }

    /**
     * To JavaScript parser context javet sanitizer error context.
     *
     * @param token the token
     * @return the javet sanitizer error context
     * @since 0.1.0
     */
    protected JavetSanitizerErrorContext toJavaScriptParserContext(Token token) {
        if (token != null) {
            final int startPosition = token.getStartIndex();
            final int endPosition = token.getStopIndex() + 1;
            return new JavetSanitizerErrorContext()
                    .setStartLineNumber(token.getLine())
                    .setEndLineNumber(token.getLine())
                    .setStartColumn(token.getCharPositionInLine())
                    .setEndColumn(token.getCharPositionInLine() + endPosition - startPosition)
                    .setStartPosition(startPosition)
                    .setEndPosition(endPosition)
                    .setSourceCode(codeString);
        }
        return null;
    }

    /**
     * To JavaScript parser context javet sanitizer error context.
     *
     * @param parseTree the parse tree
     * @return the javet sanitizer error context
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext toJavaScriptParserContext(ParseTree parseTree) {
        if (parseTree instanceof ParserRuleContext) {
            ParserRuleContext parserRuleContext = (ParserRuleContext) parseTree;
            Token stopToken = parserRuleContext.getStop();
            final int endPosition = stopToken.getStopIndex() + 1;
            return new JavetSanitizerErrorContext()
                    .setStartLineNumber(parserRuleContext.getStart().getLine())
                    .setEndLineNumber(parserRuleContext.getStop().getLine())
                    .setStartColumn(parserRuleContext.getStart().getCharPositionInLine())
                    .setEndColumn(parserRuleContext.getStop().getCharPositionInLine() + endPosition - stopToken.getStartIndex())
                    .setStartPosition(parserRuleContext.getStart().getStartIndex())
                    .setEndPosition(endPosition)
                    .setSourceCode(codeString);
        }
        return null;
    }

    /**
     * To javet sanitizer exception exception javet sanitizer exception.
     *
     * @param t the t
     * @return the javet sanitizer exception
     * @since 0.1.0
     */
    public JavetSanitizerException toJavetSanitizerExceptionException(Throwable t) {
        if (t instanceof ParseCancellationException) {
            Throwable innerException = t.getCause();
            if (innerException instanceof RecognitionException) {
                RecognitionException recognitionException = (RecognitionException) innerException;
                IntervalSet intervalSet = recognitionException.getExpectedTokens();
                Token offendingToken = recognitionException.getOffendingToken();
                return JavetSanitizerException.tokenMismatch(
                                intervalSet.toString(JavaScriptParser.VOCABULARY),
                                offendingToken.getText())
                        .setContext(toJavaScriptParserContext(offendingToken));
            }
        }
        return JavetSanitizerException.unknownError(t.getMessage(), t);
    }

    /**
     * Validate child class parser.
     *
     * @param expectedChildClass the expected child class
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildClass(Class<?> expectedChildClass) throws JavetSanitizerException {
        final int childCount = context.getChildCount();
        for (int index = 0; index < childCount; ++index) {
            validateChildClass(index, expectedChildClass);
        }
        return (Parser) this;
    }

    /**
     * Validate child class parser.
     *
     * @param index              the index
     * @param expectedChildClass the expected child class
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildClass(int index, Class<?> expectedChildClass) throws JavetSanitizerException {
        ParseTree parseTree = context.getChild(index);
        if (parseTree.getClass() != expectedChildClass) {
            throw JavetSanitizerException.tokenMismatch(
                            expectedChildClass.getSimpleName(),
                            parseTree.getClass().getSimpleName())
                    .setContext(toJavaScriptParserContext(parseTree));
        }
        return (Parser) this;
    }

    /**
     * Validate child count between parser.
     *
     * @param minCount the min count
     * @param maxCount the max count
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildCountBetween(int minCount, int maxCount) throws JavetSanitizerException {
        if (context.getChildCount() < minCount) {
            throw JavetSanitizerException.syntaxCountTooSmall(minCount, context.getChildCount())
                    .setContext(toJavaScriptParserContext(context));
        }
        if (context.getChildCount() > maxCount) {
            throw JavetSanitizerException.syntaxCountTooLarge(maxCount, context.getChildCount())
                    .setContext(toJavaScriptParserContext(context));
        }
        return (Parser) this;
    }

    /**
     * Validate child count equals parser.
     *
     * @param expectedCount the expected count
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildCountEquals(int expectedCount) throws JavetSanitizerException {
        if (context.getChildCount() != expectedCount) {
            throw JavetSanitizerException.syntaxCountMismatch(expectedCount, context.getChildCount())
                    .setContext(toJavaScriptParserContext(context));
        }
        return (Parser) this;
    }

    /**
     * Validate child count greater than parser.
     *
     * @param minCount the min count
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildCountGreaterThan(int minCount) throws JavetSanitizerException {
        if (context.getChildCount() < minCount) {
            throw JavetSanitizerException.syntaxCountTooSmall(minCount, context.getChildCount())
                    .setContext(toJavaScriptParserContext(context));
        }
        return (Parser) this;
    }

    /**
     * Validate child not empty parser.
     *
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public Parser validateChildNotEmpty() throws JavetSanitizerException {
        return validateChildCountGreaterThan(1);
    }

    /**
     * Walk parser.
     *
     * @param <Listener> the type parameter
     * @param listener   the listener
     * @return the self
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    public <Listener extends JavetSanitizerListener> Parser walk(Listener listener) throws JavetSanitizerException {
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        try {
            parseTreeWalker.walk(listener, context);
        } catch (Throwable t) {
            JavetSanitizerException javetSanitizerException = listener.getException();
            if (javetSanitizerException == null) {
                throw toJavetSanitizerExceptionException(t);
            }
            ParserRuleContext parserRuleContext = listener.getErrorContext();
            if (parserRuleContext != null) {
                javetSanitizerException.setContext(toJavaScriptParserContext(parserRuleContext));
            }
            throw javetSanitizerException;
        }
        return (Parser) this;
    }
}
