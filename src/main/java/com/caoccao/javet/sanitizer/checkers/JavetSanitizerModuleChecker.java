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

package com.caoccao.javet.sanitizer.checkers;


import com.caoccao.javet.sanitizer.antlr.JavaScriptParser;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import com.caoccao.javet.sanitizer.parsers.JavaScriptFunctionDeclarationParser;
import com.caoccao.javet.sanitizer.parsers.JavaScriptStatementParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Javet sanitizer module checker.
 *
 * @since 0.1.0
 */
public class JavetSanitizerModuleChecker extends JavetSanitizerStatementListChecker {
    /**
     * The Function parser map.
     *
     * @since 0.1.0
     */
    protected Map<String, JavaScriptFunctionDeclarationParser> functionParserMap;

    /**
     * Instantiates a new Javet sanitizer module checker.
     *
     * @since 0.1.0
     */
    public JavetSanitizerModuleChecker() {
        this(JavetSanitizerOption.Default);
    }

    /**
     * Instantiates a new Javet sanitizer module checker.
     *
     * @param option the option
     * @since 0.1.0
     */
    public JavetSanitizerModuleChecker(JavetSanitizerOption option) {
        super(option);
    }

    @Override
    public boolean check(String codeString) throws JavetSanitizerException {
        super.check(codeString);
        Map<String, JavaScriptFunctionDeclarationParser> functionParserMap = getFunctionParserMap();
        functionParserMap.clear();
        boolean importStatementAllowed = option.isKeywordImportEnabled();
        for (JavaScriptStatementParser statementParser : statementParsers) {
            statementParser.validateChildCountEquals(1);
            if (importStatementAllowed
                    && statementParser.isChildClass(0, JavaScriptParser.ImportStatementContext.class)) {
                continue;
            }
            importStatementAllowed = false;
            statementParser.validateChildClass(JavaScriptParser.FunctionDeclarationContext.class);
            JavaScriptFunctionDeclarationParser javaScriptFunctionDeclarationParser =
                    statementParser.getJavaScriptFunctionDeclarationParser(0).parse();
            String functionIdentifier = javaScriptFunctionDeclarationParser.getIdentifier();
            functionParserMap.put(functionIdentifier, javaScriptFunctionDeclarationParser);
        }
        validateFunctions();
        return true;
    }

    /**
     * Gets function parser map.
     *
     * @return the function parser map
     * @since 0.1.0
     */
    public Map<String, JavaScriptFunctionDeclarationParser> getFunctionParserMap() {
        if (functionParserMap == null) {
            functionParserMap = new HashMap<>();
        }
        return functionParserMap;
    }

    @Override
    protected void reset() {
        super.reset();
        getFunctionParserMap().clear();
    }

    /**
     * Validate functions.
     *
     * @throws JavetSanitizerException the javet sanitizer exception
     * @since 0.1.0
     */
    protected void validateFunctions() throws JavetSanitizerException {
        Map<String, JavaScriptFunctionDeclarationParser> functionParserMap = getFunctionParserMap();
        for (String functionIdentifier : option.getReservedFunctionIdentifierSet()) {
            if (!functionParserMap.containsKey(functionIdentifier)) {
                throw JavetSanitizerException.functionNotFound(functionIdentifier);
            }
        }
    }
}
