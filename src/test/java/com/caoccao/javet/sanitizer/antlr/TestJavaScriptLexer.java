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

package com.caoccao.javet.sanitizer.antlr;

import com.caoccao.javet.sanitizer.listeners.JavetSanitizerListener;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJavaScriptLexer {
    @Test
    public void testCommentWithBufferedTokenStream() {
        String jsString = "// comment\n// comment";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        BufferedTokenStream tokenStream = new BufferedTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.ProgramContext programContext = javaScriptParser.program();
        assertNotNull(programContext);
        assertNotNull(programContext.exception);
        assertEquals(InputMismatchException.class, programContext.exception.getClass());
        assertEquals(3, programContext.getChildCount());
        assertEquals("// comment", programContext.getChild(0).getText());
        assertEquals("\n", programContext.getChild(1).getText());
        assertEquals("// comment", programContext.getChild(2).getText());
    }

    @Test
    public void testCommentWithCommonTokenStream() {
        String jsString = "// comment\n// comment";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.ProgramContext programContext = javaScriptParser.program();
        assertNotNull(programContext);
        assertNull(programContext.exception);
        assertEquals(1, programContext.getChildCount());
        assertEquals("<EOF>", programContext.getChild(0).getText());
    }

    @Test
    public void testExpressionStatementContext() {
        String jsString = "()=>console.log('a');";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.ExpressionStatementContext expressionStatementContext = javaScriptParser.expressionStatement();
        assertNotNull(expressionStatementContext);
        assertEquals(2, expressionStatementContext.getChildCount());
        assertNull(expressionStatementContext.exception);
        assertTrue(expressionStatementContext.getChild(0) instanceof JavaScriptParser.ExpressionSequenceContext);
        JavaScriptParser.ExpressionSequenceContext expressionSequenceContext =
                expressionStatementContext.getChild(JavaScriptParser.ExpressionSequenceContext.class, 0);
        assertTrue(expressionSequenceContext.getChild(0) instanceof JavaScriptParser.FunctionExpressionContext);
        JavaScriptParser.FunctionExpressionContext functionExpressionContext =
                expressionSequenceContext.getChild(JavaScriptParser.FunctionExpressionContext.class, 0);
        assertTrue(functionExpressionContext.getChild(0) instanceof JavaScriptParser.ArrowFunctionContext);
    }

    @Test
    public void testFunctionDeclaration1() {
        String jsString = "function test(x) {\nif (a == 1) { b = 2; }\n// comment\n};";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        javaScriptLexer.setUseStrictDefault(true);
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        while (true) {
            Token token = javaScriptLexer.nextToken();
            if ("<EOF>".equals(token.getText())) {
                break;
            }
        }
        javaScriptLexer.reset();
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.FunctionDeclarationContext functionDeclarationContext = javaScriptParser.functionDeclaration();
        assertNotNull(functionDeclarationContext);
        assertNull(functionDeclarationContext.exception);
        assertEquals(6, functionDeclarationContext.getChildCount());
        assertEquals("function", functionDeclarationContext
                .getChild(TerminalNode.class, 0).getSymbol().getText());
        assertEquals("test", functionDeclarationContext
                .getChild(JavaScriptParser.IdentifierContext.class, 0)
                .getChild(TerminalNode.class, 0).getSymbol().getText());
        assertEquals("(", functionDeclarationContext
                .getChild(TerminalNode.class, 1).getSymbol().getText());
        assertEquals("x", functionDeclarationContext
                .getChild(JavaScriptParser.FormalParameterListContext.class, 0)
                .getChild(JavaScriptParser.FormalParameterArgContext.class, 0)
                .getChild(JavaScriptParser.AssignableContext.class, 0)
                .getChild(JavaScriptParser.IdentifierContext.class, 0)
                .getChild(TerminalNode.class, 0).getSymbol().getText());
        assertEquals(")", functionDeclarationContext
                .getChild(TerminalNode.class, 2).getSymbol().getText());
        JavaScriptParser.FunctionBodyContext functionBodyContext =
                functionDeclarationContext.getChild(JavaScriptParser.FunctionBodyContext.class, 0);
        assertEquals(3, functionBodyContext.getChildCount());
        assertEquals("{", functionBodyContext
                .getChild(TerminalNode.class, 0).getSymbol().getText());
        assertEquals("}", functionBodyContext
                .getChild(TerminalNode.class, 1).getSymbol().getText());
    }

    @Test
    public void testLiteral() {
        String jsString = "'abc中文'";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.LiteralContext literalContext = javaScriptParser.literal();
        assertNotNull(literalContext);
        assertNull(literalContext.exception);
        assertEquals(1, literalContext.getChildCount());
        assertEquals(jsString, literalContext.getChild(TerminalNode.class, 0).getSymbol().getText());
    }

    @Test
    public void testProgram() {
        String jsString = "console.log('a');";
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString(jsString));
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.ProgramContext programContext = javaScriptParser.program();
        assertNotNull(programContext);
        assertNull(programContext.exception);
        assertEquals(2, programContext.getChildCount());
        ParseTree parseTree = programContext.getChild(0);
        assertEquals(jsString, parseTree.getText());
        parseTree = programContext.getChild(1);
        assertEquals("<EOF>", parseTree.getText());
    }

    @Test
    public void testVariableDeclaration() {
        JavaScriptLexer javaScriptLexer = new JavaScriptLexer(CharStreams.fromString("$a = 1;"));
        CommonTokenStream tokenStream = new CommonTokenStream(javaScriptLexer);
        JavaScriptParser javaScriptParser = new JavaScriptParser(tokenStream);
        javaScriptParser.setBuildParseTree(true);
        javaScriptParser.addParseListener(new JavetSanitizerListener(JavetSanitizerOption.Default));
        JavaScriptParser.VariableDeclarationContext variableDeclarationContext = javaScriptParser.variableDeclaration();
        assertNotNull(variableDeclarationContext);
        assertNull(variableDeclarationContext.exception);
        assertEquals(3, variableDeclarationContext.getChildCount());
        assertTrue(variableDeclarationContext.getChild(0) instanceof JavaScriptParser.AssignableContext);
        JavaScriptParser.AssignableContext assignableContext = (JavaScriptParser.AssignableContext)
                variableDeclarationContext.getChild(0);
        assertEquals(1, assignableContext.getChildCount());
        assertTrue(assignableContext.getChild(0) instanceof JavaScriptParser.IdentifierContext);
        assertEquals("$a", ((JavaScriptParser.IdentifierContext) assignableContext.getChild(0)).Identifier().getSymbol().getText());
        assertEquals("$a=1", variableDeclarationContext.getText());
        assertEquals("$a", variableDeclarationContext.getStart().getText());
        assertEquals("1", variableDeclarationContext.getStop().getText());
    }
}
