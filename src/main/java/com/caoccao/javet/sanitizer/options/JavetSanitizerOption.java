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

package com.caoccao.javet.sanitizer.options;

import com.caoccao.javet.sanitizer.antlr.JavaScriptParserListener;
import com.caoccao.javet.sanitizer.exceptions.JavetSanitizerException;
import com.caoccao.javet.sanitizer.listeners.JavetSanitizerListener;
import com.caoccao.javet.sanitizer.listeners.JavetSanitizerSecurityCheckListener;
import com.caoccao.javet.sanitizer.utils.SimpleSet;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Function;

/**
 * The type Javet sanitizer option.
 *
 * @since 0.1.0
 */
public final class JavetSanitizerOption {
    /**
     * Default name of global identifier.
     *
     * @since 0.1.0
     */
    public static final String DEFAULT_GLOBAL_IDENTIFIER = "globalThis";

    /**
     * Default reserved function identifier set.
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_RESERVED_FUNCTION_IDENTIFIER_SET =
            Collections.unmodifiableSet(SimpleSet.of("main"));
    /**
     * Default reserved identifier set.
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_RESERVED_IDENTIFIER_SET =
            Collections.unmodifiableSet(SimpleSet.of());
    /**
     * Default reserved mutable identifier set.
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_RESERVED_MUTABLE_IDENTIFIER_SET =
            Collections.unmodifiableSet(SimpleSet.of());

    /**
     * The Built-in object set.
     *
     * <pre>
     * Object.getOwnPropertyNames(global)
     *   .concat(['Decimal'])
     *   .sort()
     *   .forEach(o => console.log(o))
     * </pre>
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_BUILT_IN_OBJECT_SET = Collections.unmodifiableSet(SimpleSet.of(
            "AbortController", "AbortSignal", "AggregateError", "Array", "ArrayBuffer", "Atomics",
            "BigInt", "BigInt64Array", "BigUint64Array", "Boolean", "Buffer",
            "DataView", "Date", "Decimal",
            "Error", "EvalError", "Event", "EventTarget",
            "FinalizationRegistry", "Float32Array", "Float64Array", "Function",
            "Infinity", "Int16Array", "Int32Array", "Int8Array", "Intl",
            "JSON",
            "Map", "Math", "MessageChannel", "MessageEvent", "MessagePort",
            "NaN", "Number",
            "Object",
            "Promise", "Proxy",
            "RangeError", "ReferenceError", "Reflect", "RegExp",
            "Set", "SharedArrayBuffer", "String", "Symbol", "SyntaxError",
            "TextDecoder", "TextEncoder", "TypeError",
            "URIError", "URL", "URLSearchParams", "Uint16Array", "Uint32Array", "Uint8Array", "Uint8ClampedArray",
            "WeakMap", "WeakRef", "WeakSet", "WebAssembly",
            "_", "_error",
            "assert",
            "async_hooks", "atob",
            "btoa", "buffer",
            "child_process", "clearImmediate", "clearInterval", "clearTimeout", "cluster", "console", "constants", "crypto",
            "decodeURI", "decodeURIComponent", "dgram", "diagnostics_channel", "dns", "domain",
            "encodeURI", "encodeURIComponent", "escape", "eval", "events",
            "fs",
            "global", "globalThis",
            "http", "http2", "https",
            "inspector", "isFinite", "isNaN",
            "module",
            "net",
            "os",
            "parseFloat", "parseInt", "path", "perf_hooks", "performance", "process",
            "punycode",
            "querystring", "queueMicrotask",
            "readline", "repl", "require",
            "setImmediate", "setInterval", "setTimeout", "stream", "string_decoder", "sys",
            "timers", "tls", "trace_events", "tty",
            "undefined", "unescape", "url", "util",
            "v8", "vm",
            "wasi", "worker_threads",
            "zlib"));

    /**
     * The Disallowed identifier set.
     * <p>
     * <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects">JS Global Objects</a>
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_DISALLOWED_IDENTIFIER_SET = Collections.unmodifiableSet(SimpleSet.of(
            "__proto__",
            "apply", "AsyncFunction", "AsyncGenerator", "AsyncGeneratorFunction",
            "bind",
            "call", "clearInterval", "clearTimeout",
            "defineProperty", "defineProperties",
            "eval",
            "Function",
            "global", "globalThis", "getPrototypeOf",
            "Generator", "GeneratorFunction",
            "Intl",
            "prototype", "Proxy",
            "Promise",
            "require",
            "Reflect",
            "setImmediate", "setInterval", "setTimeout", "setPrototypeOf", "Symbol",
            "uneval",
            "XMLHttpRequest",
            "WebAssembly", "window"));

    /**
     * The default to be deleted objects from V8.
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_TO_BE_DELETED_OBJECT_SET = Collections.unmodifiableSet(SimpleSet.of(
            "eval",
            "Function",
            "WebAssembly"));
    /**
     * The default to be frozen objects from V8.
     *
     * @since 0.1.0
     */
    public static final Set<String> DEFAULT_TO_BE_FROZEN_OBJECT_SET = Collections.unmodifiableSet(SimpleSet.of(
            "Object", // Object must be the first.
            "AggregateError",
            "Array",
            "ArrayBuffer",
            "Atomics",
            "BigInt",
            "BigInt64Array",
            "BigUint64Array",
            "Boolean",
            "DataView",
            "Date",
            "decodeURI",
            "decodeURIComponent",
            "encodeURI",
            "encodeURIComponent",
            "Error",
            "escape",
            "EvalError",
            "FinalizationRegistry",
            "Float32Array",
            "Float64Array",
            "Int8Array",
            "Int16Array",
            "Int32Array",
            "isFinite",
            "isNaN",
            "JSON",
            "Map",
            "Math",
            "Number",
            "parseFloat",
            "parseInt",
            "Promise",
            "Proxy",
            "RangeError",
            "ReferenceError",
            "Reflect",
            "RegExp",
            "Set",
            "SharedArrayBuffer",
            "String",
            "Symbol",
            "SyntaxError",
            "TypeError",
            "Uint8Array",
            "Uint8ClampedArray",
            "Uint16Array",
            "Uint32Array",
            "unescape",
            "URIError",
            "WeakMap",
            "WeakRef",
            "WeakSet"));
    /**
     * Default option is the most strict and secure option.
     * Most built-in objects and keywords are disabled.
     *
     * @since 0.1.0
     */
    public static final JavetSanitizerOption Default = new JavetSanitizerOption("Default")
            .setListenerClass(JavetSanitizerSecurityCheckListener.class)
            .seal();
    private Map<String, Object> argumentMap;
    private Set<String> builtInObjectSet;
    private Set<String> disallowedIdentifierSet;
    private String globalIdentifier;
    private boolean keywordAsyncEnabled;
    private boolean keywordAwaitEnabled;
    private boolean keywordDebuggerEnabled;
    private boolean keywordExportEnabled;
    private boolean keywordImportEnabled;
    private boolean keywordVarEnabled;
    private boolean keywordWithEnabled;
    private boolean keywordYieldEnabled;
    private Class<? extends JavaScriptParserListener> listenerClass;
    private String name;
    private Set<String> reservedFunctionIdentifierSet;
    private Function<String, Boolean> reservedIdentifierMatcher;
    private Set<String> reservedIdentifierSet;
    private Set<String> reservedMutableIdentifierSet;
    private boolean sealed;
    private Set<String> toBeDeletedIdentifierSet;
    private Set<String> toBeFrozenIdentifierSet;

    private JavetSanitizerOption(String name) {
        argumentMap = new HashMap<>();
        builtInObjectSet = new HashSet<>(DEFAULT_BUILT_IN_OBJECT_SET);
        disallowedIdentifierSet = new HashSet<>(DEFAULT_DISALLOWED_IDENTIFIER_SET);
        globalIdentifier = DEFAULT_GLOBAL_IDENTIFIER;
        keywordAsyncEnabled = false;
        keywordAwaitEnabled = false;
        keywordDebuggerEnabled = false;
        keywordExportEnabled = false;
        keywordImportEnabled = false;
        keywordVarEnabled = false;
        keywordWithEnabled = false;
        keywordYieldEnabled = false;
        listenerClass = JavetSanitizerSecurityCheckListener.class;
        this.name = Objects.requireNonNull(name);
        reservedFunctionIdentifierSet = new HashSet<>(DEFAULT_RESERVED_FUNCTION_IDENTIFIER_SET);
        reservedIdentifierMatcher = identifier -> false;
        reservedIdentifierSet = new HashSet<>(DEFAULT_RESERVED_IDENTIFIER_SET);
        reservedMutableIdentifierSet = new HashSet<>(DEFAULT_RESERVED_MUTABLE_IDENTIFIER_SET);
        toBeDeletedIdentifierSet = new HashSet<>(DEFAULT_TO_BE_DELETED_OBJECT_SET);
        toBeFrozenIdentifierSet = new HashSet<>(DEFAULT_TO_BE_FROZEN_OBJECT_SET);
        sealed = false;
    }

    /**
     * Gets argument map.
     *
     * @return the argument map
     * @since 0.1.0
     */
    public Map<String, Object> getArgumentMap() {
        return argumentMap;
    }

    /**
     * Gets built-in object set.
     *
     * @return the built-in object set
     * @since 0.1.0
     */
    public Set<String> getBuiltInObjectSet() {
        return builtInObjectSet;
    }

    /**
     * Gets disallowed identifier set.
     *
     * @return the disallowed identifier set
     * @since 0.1.0
     */
    public Set<String> getDisallowedIdentifierSet() {
        return disallowedIdentifierSet;
    }

    /**
     * Gets global identifier.
     *
     * @return the global identifier
     * @since 0.1.0
     */
    public String getGlobalIdentifier() {
        return globalIdentifier;
    }

    /**
     * Gets listener.
     *
     * @param <Listener> the type parameter
     * @return the listener
     * @throws JavetSanitizerException the javet sanitizer exception
     */
    @SuppressWarnings("unchecked")
    public <Listener extends JavetSanitizerListener> Listener getListener() throws JavetSanitizerException {
        try {
            Constructor<Listener> constructor =
                    (Constructor<Listener>) listenerClass.getConstructor(JavetSanitizerOption.class);
            return constructor.newInstance(this);
        } catch (Throwable t) {
            throw JavetSanitizerException.listenerNotFound(listenerClass.getName(), t);
        }
    }

    /**
     * Gets listener class.
     *
     * @return the listener class
     * @since 0.1.0
     */
    public Class<? extends JavaScriptParserListener> getListenerClass() {
        return listenerClass;
    }

    /**
     * Gets name.
     *
     * @return the name
     * @since 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets reserved function identifier set.
     *
     * @return the reserved function identifier set
     * @since 0.1.0
     */
    public Set<String> getReservedFunctionIdentifierSet() {
        return reservedFunctionIdentifierSet;
    }

    /**
     * Gets reserved identifier matcher.
     *
     * @return the reserved identifier matcher
     * @since 0.1.0
     */
    public Function<String, Boolean> getReservedIdentifierMatcher() {
        return reservedIdentifierMatcher;
    }

    /**
     * Gets reserved identifier set.
     *
     * @return the reserved identifier set
     * @since 0.1.0
     */
    public Set<String> getReservedIdentifierSet() {
        return reservedIdentifierSet;
    }

    /**
     * Gets reserved mutable identifier set.
     *
     * @return the reserved mutable identifier set
     * @since 0.1.0
     */
    public Set<String> getReservedMutableIdentifierSet() {
        return reservedMutableIdentifierSet;
    }

    /**
     * Gets to be deleted identifier set.
     *
     * @return the to be deleted identifier set
     * @since 0.1.0
     */
    public Set<String> getToBeDeletedIdentifierSet() {
        return toBeDeletedIdentifierSet;
    }

    /**
     * Gets to be frozen identifier set.
     *
     * @return the to be frozen identifier set
     * @since 0.1.0
     */
    public Set<String> getToBeFrozenIdentifierSet() {
        return toBeFrozenIdentifierSet;
    }

    /**
     * Is keyword async enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordAsyncEnabled() {
        return keywordAsyncEnabled;
    }

    /**
     * Is keyword await enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordAwaitEnabled() {
        return keywordAwaitEnabled;
    }

    /**
     * Is keyword debugger enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordDebuggerEnabled() {
        return keywordDebuggerEnabled;
    }

    /**
     * Is keyword export enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordExportEnabled() {
        return keywordExportEnabled;
    }

    /**
     * Is keyword import enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordImportEnabled() {
        return keywordImportEnabled;
    }

    /**
     * Is keyword var enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordVarEnabled() {
        return keywordVarEnabled;
    }

    /**
     * Is keyword with enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordWithEnabled() {
        return keywordWithEnabled;
    }

    /**
     * Is keyword yield enabled.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isKeywordYieldEnabled() {
        return keywordYieldEnabled;
    }

    /**
     * Is sealed.
     *
     * @return the boolean
     * @since 0.1.0
     */
    public boolean isSealed() {
        return sealed;
    }

    /**
     * Seal javet sanitizer option. After it is sealed, it will be immutable.
     *
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption seal() {
        argumentMap = Collections.unmodifiableMap(argumentMap);
        builtInObjectSet = Collections.unmodifiableSet(builtInObjectSet);
        disallowedIdentifierSet = Collections.unmodifiableSet(disallowedIdentifierSet);
        reservedFunctionIdentifierSet = Collections.unmodifiableSet(reservedFunctionIdentifierSet);
        reservedIdentifierSet = Collections.unmodifiableSet(reservedIdentifierSet);
        reservedMutableIdentifierSet = Collections.unmodifiableSet(reservedMutableIdentifierSet);
        toBeDeletedIdentifierSet = Collections.unmodifiableSet(toBeDeletedIdentifierSet);
        toBeFrozenIdentifierSet = Collections.unmodifiableSet(toBeFrozenIdentifierSet);
        sealed = true;
        return this;
    }

    /**
     * Sets global identifier.
     *
     * @param globalIdentifier the global identifier
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setGlobalIdentifier(String globalIdentifier) {
        if (!sealed) {
            this.globalIdentifier = globalIdentifier;
        }
        return this;
    }

    /**
     * Sets keyword async enabled.
     *
     * @param keywordAsyncEnabled the keyword async enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordAsyncEnabled(boolean keywordAsyncEnabled) {
        if (!sealed) {
            this.keywordAsyncEnabled = keywordAsyncEnabled;
        }
        return this;
    }

    /**
     * Sets keyword await enabled.
     *
     * @param keywordAwaitEnabled the keyword await enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordAwaitEnabled(boolean keywordAwaitEnabled) {
        if (!sealed) {
            this.keywordAwaitEnabled = keywordAwaitEnabled;
        }
        return this;
    }

    /**
     * Sets keyword debugger enabled.
     *
     * @param keywordDebuggerEnabled the keyword debugger enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordDebuggerEnabled(boolean keywordDebuggerEnabled) {
        if (!sealed) {
            this.keywordDebuggerEnabled = keywordDebuggerEnabled;
        }
        return this;
    }

    /**
     * Sets keyword export enabled.
     *
     * @param keywordExportEnabled the keyword export enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordExportEnabled(boolean keywordExportEnabled) {
        if (!sealed) {
            this.keywordExportEnabled = keywordExportEnabled;
        }
        return this;
    }

    /**
     * Sets keyword import enabled.
     *
     * @param keywordImportEnabled the keyword import enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordImportEnabled(boolean keywordImportEnabled) {
        if (!sealed) {
            this.keywordImportEnabled = keywordImportEnabled;
        }
        return this;
    }

    /**
     * Sets keyword var enabled.
     *
     * @param keywordVarEnabled the keyword var enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordVarEnabled(boolean keywordVarEnabled) {
        if (!sealed) {
            this.keywordVarEnabled = keywordVarEnabled;
        }
        return this;
    }

    /**
     * Sets keyword with enabled.
     *
     * @param keywordWithEnabled the keyword with enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordWithEnabled(boolean keywordWithEnabled) {
        if (!sealed) {
            this.keywordWithEnabled = keywordWithEnabled;
        }
        return this;
    }

    /**
     * Sets keyword yield enabled.
     *
     * @param keywordYieldEnabled the keyword yield enabled
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setKeywordYieldEnabled(boolean keywordYieldEnabled) {
        if (!sealed) {
            this.keywordYieldEnabled = keywordYieldEnabled;
        }
        return this;
    }

    /**
     * Sets listener class.
     *
     * @param listenerClass the listener class
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setListenerClass(Class<? extends JavaScriptParserListener> listenerClass) {
        if (!sealed) {
            this.listenerClass = Objects.requireNonNull(listenerClass);
        }
        return this;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setName(String name) {
        if (!sealed) {
            this.name = Objects.requireNonNull(name);
        }
        return this;
    }

    /**
     * Sets reserved identifier matcher.
     *
     * @param reservedIdentifierMatcher the reserved identifier matcher
     * @return the self
     * @since 0.1.0
     */
    public JavetSanitizerOption setReservedIdentifierMatcher(Function<String, Boolean> reservedIdentifierMatcher) {
        if (!sealed) {
            this.reservedIdentifierMatcher = Objects.requireNonNull(reservedIdentifierMatcher);
        }
        return this;
    }

    /**
     * To clone javet sanitizer option.
     *
     * @return the new javet sanitizer option
     * @since 0.1.0
     */
    public JavetSanitizerOption toClone() {
        JavetSanitizerOption option = new JavetSanitizerOption(name);
        option.argumentMap.clear();
        option.argumentMap.putAll(argumentMap);
        option.builtInObjectSet.clear();
        option.builtInObjectSet.addAll(builtInObjectSet);
        option.disallowedIdentifierSet.clear();
        option.disallowedIdentifierSet.addAll(disallowedIdentifierSet);
        option.globalIdentifier = globalIdentifier;
        option.keywordAsyncEnabled = keywordAsyncEnabled;
        option.keywordAwaitEnabled = keywordAwaitEnabled;
        option.keywordDebuggerEnabled = keywordDebuggerEnabled;
        option.keywordExportEnabled = keywordExportEnabled;
        option.keywordImportEnabled = keywordImportEnabled;
        option.keywordWithEnabled = keywordWithEnabled;
        option.keywordYieldEnabled = keywordYieldEnabled;
        option.listenerClass = listenerClass;
        option.reservedFunctionIdentifierSet.clear();
        option.reservedFunctionIdentifierSet.addAll(reservedFunctionIdentifierSet);
        option.reservedIdentifierMatcher = reservedIdentifierMatcher;
        option.reservedIdentifierSet.clear();
        option.reservedIdentifierSet.addAll(reservedIdentifierSet);
        option.reservedMutableIdentifierSet.clear();
        option.reservedMutableIdentifierSet.addAll(reservedMutableIdentifierSet);
        option.toBeDeletedIdentifierSet.clear();
        option.toBeDeletedIdentifierSet.addAll(toBeDeletedIdentifierSet);
        option.toBeFrozenIdentifierSet.clear();
        option.toBeFrozenIdentifierSet.addAll(toBeFrozenIdentifierSet);
        return option;
    }
}
