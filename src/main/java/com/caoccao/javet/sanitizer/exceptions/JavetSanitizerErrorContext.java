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

package com.caoccao.javet.sanitizer.exceptions;


import com.caoccao.javet.sanitizer.utils.StringUtils;

/**
 * The type Javet sanitizer error context.
 *
 * @since 0.1.0
 */
public class JavetSanitizerErrorContext {
    private int endColumn;
    private int endLineNumber;
    private int endPosition;
    private String sourceCode;
    private int startColumn;
    private int startLineNumber;
    private int startPosition;

    /**
     * Instantiates a new Javet sanitizer error context.
     *
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext() {
    }

    /**
     * Gets end column.
     *
     * @return the end column
     * @since 0.1.0
     */
    public int getEndColumn() {
        return endColumn;
    }

    /**
     * Gets end line number.
     *
     * @return the end line number
     * @since 0.1.0
     */
    public int getEndLineNumber() {
        return endLineNumber;
    }

    /**
     * Gets end position.
     *
     * @return the end position
     * @since 0.1.0
     */
    public int getEndPosition() {
        return endPosition;
    }

    /**
     * Gets source code.
     *
     * @return the source code
     * @since 0.1.0
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Gets source line.
     *
     * @return the source line
     * @since 0.1.0
     */
    public String getSourceLine() {
        return StringUtils.isEmpty(sourceCode)
                ? StringUtils.EMPTY
                : sourceCode.substring(getStartPosition(), getEndPosition());
    }

    /**
     * Gets start column.
     *
     * @return the start column
     * @since 0.1.0
     */
    public int getStartColumn() {
        return startColumn;
    }

    /**
     * Gets start line number.
     *
     * @return the start line number
     * @since 0.1.0
     */
    public int getStartLineNumber() {
        return startLineNumber;
    }

    /**
     * Gets start position.
     *
     * @return the start position
     * @since 0.1.0
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets end column.
     *
     * @param endColumn the end column
     * @return the end column
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setEndColumn(int endColumn) {
        this.endColumn = endColumn;
        return this;
    }

    /**
     * Sets end line number.
     *
     * @param endLineNumber the end line number
     * @return the end line number
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setEndLineNumber(int endLineNumber) {
        this.endLineNumber = endLineNumber;
        return this;
    }

    /**
     * Sets end position.
     *
     * @param endPosition the end position
     * @return the end position
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setEndPosition(int endPosition) {
        this.endPosition = endPosition;
        return this;
    }

    /**
     * Sets source code.
     *
     * @param sourceCode the source code
     * @return the source code
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
        return this;
    }

    /**
     * Sets start column.
     *
     * @param startColumn the start column
     * @return the start column
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setStartColumn(int startColumn) {
        this.startColumn = startColumn;
        return this;
    }

    /**
     * Sets start line number.
     *
     * @param startLineNumber the start line number
     * @return the start line number
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setStartLineNumber(int startLineNumber) {
        this.startLineNumber = startLineNumber;
        return this;
    }

    /**
     * Sets start position.
     *
     * @param startPosition the start position
     * @return the start position
     * @since 0.1.0
     */
    public JavetSanitizerErrorContext setStartPosition(int startPosition) {
        this.startPosition = startPosition;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Source Code: ").append(getSourceLine()).append("\n");
        sb.append("Line Number: ").append(startLineNumber).append(", ").append(endLineNumber).append("\n");
        sb.append("Column: ").append(startColumn).append(", ").append(endColumn).append("\n");
        sb.append("Position: ").append(startPosition).append(", ").append(endPosition);
        return sb.toString();
    }
}
