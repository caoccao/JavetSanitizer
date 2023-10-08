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
import com.caoccao.javet.sanitizer.utils.StringUtils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The type Javet sanitizer identifier matcher.
 *
 * @since 0.1.0
 */
public final class JavetSanitizerIdentifierMatcher implements IJavetSanitizerMatcher {

    private static final JavetSanitizerIdentifierMatcher INSTANCE = new JavetSanitizerIdentifierMatcher();

    private JavetSanitizerIdentifierMatcher() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @since 0.1.0
     */
    public static JavetSanitizerIdentifierMatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public ParseTree matches(JavetSanitizerOption option, ParserRuleContext context) {
        if (context instanceof JavaScriptParser.IdentifierContext) {
            TerminalNode terminalNode = ((JavaScriptParser.IdentifierContext) context).Identifier();
            if (terminalNode != null && matches(option, terminalNode.getText())) {
                return terminalNode;
            }
        }
        return null;
    }

    private boolean matches(JavetSanitizerOption option, String identifier) {
        if (StringUtils.isEmpty(identifier)) {
            return false;
        }
        if (option.getReservedIdentifierMatcher().apply(identifier)) {
            return !option.getReservedIdentifierSet().contains(identifier);
        }
        return option.getDisallowedIdentifierSet().contains(identifier);
    }
}
