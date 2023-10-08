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

package com.caoccao.javet.sanitizer.checkers;


import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import com.caoccao.javet.sanitizer.parsers.JavaScriptStatementListParser;
import com.caoccao.javet.sanitizer.parsers.JavaScriptStatementParser;

import java.util.ArrayList;
import java.util.List;

public class JavetSanitizerStatementListChecker
        extends BaseJavetSanitizerChecker<JavaScriptStatementListParser> {
    protected List<JavaScriptStatementParser> statementParsers;

    public JavetSanitizerStatementListChecker() {
        this(JavetSanitizerOption.Default);
    }

    public JavetSanitizerStatementListChecker(JavetSanitizerOption option) {
        super(option);
    }

    @Override
    public boolean check(String codeString) throws JavetSanitizerException {
        super.check(codeString);
        rootParser = new JavaScriptStatementListParser(codeString)
                .parse()
                .walk(option.getListener());
        statementParsers.addAll(rootParser.getJavaScriptStatementParsers());
        return true;
    }

    @Override
    protected void reset() {
        super.reset();
        if (statementParsers == null) {
            statementParsers = new ArrayList<>();
        } else {
            statementParsers.clear();
        }
    }
}
