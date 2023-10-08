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

package com.caoccao.javet.sanitizer.matchers;

import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The type Javet sanitizer built in object matcher.
 *
 * @since 0.1.0
 */
public final class JavetSanitizerBuiltInObjectMatcher implements IJavetSanitizerMatcher {
    private static final JavetSanitizerBuiltInObjectMatcher INSTANCE = new JavetSanitizerBuiltInObjectMatcher();

    private JavetSanitizerBuiltInObjectMatcher() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @since 0.1.0
     */
    public static JavetSanitizerBuiltInObjectMatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public ParseTree matches(JavetSanitizerOption option, ParserRuleContext context) {
        if (context instanceof JavaScriptParser.ArrayElementContext) {
            JavaScriptParser.ArrayElementContext arrayElementContext = (JavaScriptParser.ArrayElementContext) context;
            return matches(option, arrayElementContext.singleExpression());
        }
        if (context instanceof JavaScriptParser.ArrayLiteralExpressionContext) {
            JavaScriptParser.ArrayLiteralExpressionContext arrayLiteralExpressionContext =
                    (JavaScriptParser.ArrayLiteralExpressionContext) context;
            JavaScriptParser.ArrayLiteralContext arrayLiteralContext = arrayLiteralExpressionContext.arrayLiteral();
            JavaScriptParser.ElementListContext elementListContext = arrayLiteralContext.elementList();
            for (JavaScriptParser.ArrayElementContext arrayElementContext : elementListContext.arrayElement()) {
                ParseTree parseTree = matches(option, arrayElementContext);
                if (parseTree != null) {
                    return parseTree;
                }
            }
        }
        if (context instanceof JavaScriptParser.AssignableContext) {
            JavaScriptParser.AssignableContext assignableContext = (JavaScriptParser.AssignableContext) context;
            return matches(option, assignableContext.identifier());
        }
        if (context instanceof JavaScriptParser.AssignmentExpressionContext) {
            JavaScriptParser.AssignmentExpressionContext assignmentExpressionContext =
                    (JavaScriptParser.AssignmentExpressionContext) context;
            return matches(option, assignmentExpressionContext.singleExpression(0));
        }
        if (context instanceof JavaScriptParser.AssignmentOperatorExpressionContext) {
            JavaScriptParser.AssignmentOperatorExpressionContext assignmentOperatorExpressionContext =
                    (JavaScriptParser.AssignmentOperatorExpressionContext) context;
            return matches(option, assignmentOperatorExpressionContext.singleExpression(0));
        }
        if (context instanceof JavaScriptParser.CatchProductionContext) {
            JavaScriptParser.CatchProductionContext catchProductionContext =
                    (JavaScriptParser.CatchProductionContext) context;
            return matches(option, catchProductionContext.assignable());
        }
        if (context instanceof JavaScriptParser.ClassDeclarationContext) {
            JavaScriptParser.ClassDeclarationContext classDeclarationContext =
                    (JavaScriptParser.ClassDeclarationContext) context;
            return matches(option, classDeclarationContext.identifier());
        }
        if (context instanceof JavaScriptParser.ClassExpressionContext) {
            JavaScriptParser.ClassExpressionContext classExpressionContext =
                    (JavaScriptParser.ClassExpressionContext) context;
            return matches(option, classExpressionContext.identifier());
        }
        if (context instanceof JavaScriptParser.FormalParameterArgContext) {
            JavaScriptParser.FormalParameterArgContext formalParameterArgContext =
                    (JavaScriptParser.FormalParameterArgContext) context;
            return matches(option, formalParameterArgContext.assignable());
        }
        if (context instanceof JavaScriptParser.FunctionDeclarationContext) {
            JavaScriptParser.FunctionDeclarationContext functionDeclarationContext =
                    (JavaScriptParser.FunctionDeclarationContext) context;
            return matches(option, functionDeclarationContext.identifier());
        }
        if (context instanceof JavaScriptParser.IdentifierContext) {
            JavaScriptParser.IdentifierContext identifierContext = (JavaScriptParser.IdentifierContext) context;
            TerminalNode terminalNode = identifierContext.Identifier();
            if (terminalNode != null) {
                String identifier = terminalNode.getText();
                if (option.getReservedIdentifierMatcher().apply(identifier)) {
                    if (identifierContext.getParent() instanceof JavaScriptParser.FunctionDeclarationContext) {
                        if (!option.getReservedFunctionIdentifierSet().contains(identifier)) {
                            return terminalNode;
                        }
                    } else if (!option.getReservedMutableIdentifierSet().contains(identifier)) {
                        return terminalNode;
                    }
                } else if (option.getBuiltInObjectSet().contains(identifier)) {
                    return terminalNode;
                }
            }
        }
        if (context instanceof JavaScriptParser.IdentifierExpressionContext) {
            JavaScriptParser.IdentifierExpressionContext identifierExpressionContext =
                    (JavaScriptParser.IdentifierExpressionContext) context;
            return matches(option, identifierExpressionContext.identifier());
        }
        if (context instanceof JavaScriptParser.MemberDotExpressionContext) {
            JavaScriptParser.MemberDotExpressionContext memberDotExpressionContext =
                    (JavaScriptParser.MemberDotExpressionContext) context;
            return matches(option, memberDotExpressionContext.singleExpression());
        }
        if (context instanceof JavaScriptParser.MemberIndexExpressionContext) {
            JavaScriptParser.MemberIndexExpressionContext memberIndexExpressionContext =
                    (JavaScriptParser.MemberIndexExpressionContext) context;
            return matches(option, memberIndexExpressionContext.singleExpression());
        }
        if (context instanceof JavaScriptParser.VariableDeclarationContext) {
            JavaScriptParser.VariableDeclarationContext variableDeclarationContext =
                    (JavaScriptParser.VariableDeclarationContext) context;
            JavaScriptParser.AssignableContext assignableContext = variableDeclarationContext.assignable();
            return matches(option, assignableContext);
        }
        return null;
    }
}
