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

package com.caoccao.javet.sanitizer.matchers;

import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The type Javet sanitizer keyword matcher.
 *
 * @since 0.1.0
 */
public final class JavetSanitizerKeywordMatcher implements IJavetSanitizerMatcher {
    private static final JavetSanitizerKeywordMatcher INSTANCE = new JavetSanitizerKeywordMatcher();

    private JavetSanitizerKeywordMatcher() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @since 0.1.0
     */
    public static JavetSanitizerKeywordMatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public ParseTree matches(JavetSanitizerOption option, ParserRuleContext context) {
        if (!option.isKeywordAsyncEnabled()) {
            if (context instanceof JavaScriptParser.AnonymousFunctionDeclContext) {
                return ((JavaScriptParser.AnonymousFunctionDeclContext) context).Async();
            }
            if (context instanceof JavaScriptParser.ArrowFunctionContext) {
                return ((JavaScriptParser.ArrowFunctionContext) context).Async();
            }
            if (context instanceof JavaScriptParser.FunctionDeclarationContext) {
                return ((JavaScriptParser.FunctionDeclarationContext) context).Async();
            }
            if (context instanceof JavaScriptParser.FunctionPropertyContext) {
                return ((JavaScriptParser.FunctionPropertyContext) context).Async();
            }
            if (context instanceof JavaScriptParser.IdentifierContext) {
                return ((JavaScriptParser.IdentifierContext) context).Async();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Async();
            }
            if (context instanceof JavaScriptParser.MethodDefinitionContext) {
                return ((JavaScriptParser.MethodDefinitionContext) context).Async();
            }
            if (context instanceof JavaScriptParser.NamedFunctionContext) {
                return ((JavaScriptParser.NamedFunctionContext) context).functionDeclaration().Async();
            }
        }
        if (!option.isKeywordAwaitEnabled()) {
            if (context instanceof JavaScriptParser.AwaitExpressionContext) {
                return ((JavaScriptParser.AwaitExpressionContext) context).Await();
            }
            if (context instanceof JavaScriptParser.ForOfStatementContext) {
                return ((JavaScriptParser.ForOfStatementContext) context).Await();
            }
            if (context instanceof JavaScriptParser.ImportedBindingContext) {
                return ((JavaScriptParser.ImportedBindingContext) context).Await();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Await();
            }
        }
        if (!option.isKeywordDebuggerEnabled()) {
            if (context instanceof JavaScriptParser.DebuggerStatementContext) {
                return ((JavaScriptParser.DebuggerStatementContext) context).Debugger();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Debugger();
            }
        }
        if (!option.isKeywordExportEnabled()) {
            if (context instanceof JavaScriptParser.ExportDeclarationContext) {
                return ((JavaScriptParser.ExportDeclarationContext) context).Export();
            }
            if (context instanceof JavaScriptParser.ExportDefaultDeclarationContext) {
                return ((JavaScriptParser.ExportDefaultDeclarationContext) context).Export();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Export();
            }
        }
        if (!option.isKeywordImportEnabled()) {
            if (context instanceof JavaScriptParser.ImportExpressionContext) {
                return ((JavaScriptParser.ImportExpressionContext) context).Import();
            }
            if (context instanceof JavaScriptParser.ImportStatementContext) {
                return ((JavaScriptParser.ImportStatementContext) context).Import();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Import();
            }
        }
        if (!option.isKeywordVarEnabled()) {
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Var();
            }
            if (context instanceof JavaScriptParser.VarModifierContext) {
                return ((JavaScriptParser.VarModifierContext) context).Var();
            }
        }
        if (!option.isKeywordWithEnabled()) {
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).With();
            }
            if (context instanceof JavaScriptParser.WithStatementContext) {
                return ((JavaScriptParser.WithStatementContext) context).With();
            }
        }
        if (!option.isKeywordYieldEnabled()) {
            if (context instanceof JavaScriptParser.ImportedBindingContext) {
                return ((JavaScriptParser.ImportedBindingContext) context).Yield();
            }
            if (context instanceof JavaScriptParser.KeywordContext) {
                return ((JavaScriptParser.KeywordContext) context).Yield();
            }
            if (context instanceof JavaScriptParser.YieldStatementContext) {
                return ((JavaScriptParser.YieldStatementContext) context).Yield();
            }
        }
        return null;
    }
}
