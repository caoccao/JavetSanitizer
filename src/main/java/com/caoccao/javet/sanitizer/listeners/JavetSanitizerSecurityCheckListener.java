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

package com.caoccao.javet.sanitizer.listeners;

import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;

/**
 * The type Javet sanitizer security check listener.
 *
 * @since 0.1.0
 */
public class JavetSanitizerSecurityCheckListener extends JavetSanitizerListener {
    /**
     * Instantiates a new Javet sanitizer security check listener.
     *
     * @param option the option
     * @since 0.1.0
     */
    public JavetSanitizerSecurityCheckListener(JavetSanitizerOption option) {
        super(option);
    }

    @Override
    public void enterAliasName(JavaScriptParser.AliasNameContext ctx) {
        super.enterAliasName(ctx);
        for (JavaScriptParser.IdentifierNameContext identifierNameContext : ctx.identifierName()) {
            JavaScriptParser.IdentifierContext identifierContext = identifierNameContext.identifier();
            validateIdentifier(identifierContext);
        }
    }

    @Override
    public void enterAnonymousFunctionDecl(JavaScriptParser.AnonymousFunctionDeclContext ctx) {
        super.enterAnonymousFunctionDecl(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {
        super.enterArrowFunction(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterAssignable(JavaScriptParser.AssignableContext ctx) {
        super.enterAssignable(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx) {
        super.enterAssignmentExpression(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) {
        super.enterAssignmentOperatorExpression(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) {
        super.enterAwaitExpression(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterCatchProduction(JavaScriptParser.CatchProductionContext ctx) {
        super.enterCatchProduction(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
        super.enterClassDeclaration(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterClassExpression(JavaScriptParser.ClassExpressionContext ctx) {
        super.enterClassExpression(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
        super.enterDebuggerStatement(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterExportDeclaration(JavaScriptParser.ExportDeclarationContext ctx) {
        super.enterExportDeclaration(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterExportDefaultDeclaration(JavaScriptParser.ExportDefaultDeclarationContext ctx) {
        super.enterExportDefaultDeclaration(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
        super.enterForOfStatement(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
        super.enterFormalParameterArg(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
        super.enterFunctionDeclaration(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) {
        super.enterFunctionProperty(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterIdentifier(JavaScriptParser.IdentifierContext ctx) {
        super.enterIdentifier(ctx);
        validateIdentifier(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterImportExpression(JavaScriptParser.ImportExpressionContext ctx) {
        super.enterImportExpression(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterImportStatement(JavaScriptParser.ImportStatementContext ctx) {
        super.enterImportStatement(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterImportedBinding(JavaScriptParser.ImportedBindingContext ctx) {
        super.enterImportedBinding(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterKeyword(JavaScriptParser.KeywordContext ctx) {
        super.enterKeyword(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
        super.enterMethodDefinition(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterNamedFunction(JavaScriptParser.NamedFunctionContext ctx) {
        super.enterNamedFunction(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterVarModifier(JavaScriptParser.VarModifierContext ctx) {
        super.enterVarModifier(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) {
        super.enterVariableDeclaration(ctx);
        validateBuiltInObject(ctx);
    }

    @Override
    public void enterWithStatement(JavaScriptParser.WithStatementContext ctx) {
        super.enterWithStatement(ctx);
        validateKeyword(ctx);
    }

    @Override
    public void enterYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
        super.enterYieldStatement(ctx);
        validateKeyword(ctx);
    }
}
