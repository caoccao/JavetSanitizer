/*
 * MCHR CONFIDENTIAL
 *
 * Copyright (c) 2020-2021 mchrcloud.com
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of MCHR Incorporated and its suppliers, if any.
 * The intellectual and technical concepts contained
 * herein are proprietary to MCHR Incorporated and its suppliers
 * and may be covered by Chinese and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from MCHR Incorporated.
 */

package com.caoccao.javet.sanitizer.matchers;

import com.caoccao.javet.sanitizer.options.JavetSanitizerOption;
import com.caoccao.javet.sanitizer.utils.SimpleList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJavetSanitizerIdentifierMatcher {
    @Test
    public void testInvalidCases() {
        List<String> identifiers = SimpleList.of("asdf", "$123", "_abc");
        JavetSanitizerOption option = JavetSanitizerOption.Default.toClone()
                .setReservedIdentifierMatcher(identifier -> false)
                .seal();
        identifiers.forEach(identifier -> {
            assertFalse(
                    JavetSanitizerIdentifierMatcher.getInstance().matches(option, identifier),
                    identifier + " should pass.");
        });
    }

    @Test
    public void testValidCases() {
        JavetSanitizerOption.Default.getDisallowedIdentifierSet().forEach(identifier ->
                assertTrue(
                        JavetSanitizerIdentifierMatcher.getInstance().matches(JavetSanitizerOption.Default, identifier),
                        identifier + " should pass."));
    }
}
