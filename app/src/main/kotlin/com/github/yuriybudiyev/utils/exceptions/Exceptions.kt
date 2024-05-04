/*
 * MIT License
 *
 * Copyright (c) 2024 Yuriy Budiyev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.yuriybudiyev.utils.exceptions

import java.util.Collections
import java.util.IdentityHashMap

/**
 * Write this [Throwable]'s stack trace to an [Appendable] [output] using given [separator].
 */
fun Throwable.writeStackTraceTo(
    output: Appendable,
    separator: String = System.lineSeparator(),
) {
    writeStackTrace(
        this,
        output,
        null,
        null,
        separator,
        Collections.newSetFromMap(IdentityHashMap())
    )
}

private fun writeStackTrace(
    throwable: Throwable,
    output: Appendable,
    caption1: String?,
    caption2: String?,
    separator: String,
    written: MutableSet<Throwable>,
) {
    if (written.contains(throwable)) {
        output
            .append("Circular reference: ")
            .append(throwable.toString())
            .append(separator)
        return
    }
    written.add(throwable)
    val stackTrace = throwable.stackTrace
    if (caption1 != null) {
        output
            .append(caption1)
            .append(separator)
    }
    if (caption2 != null) {
        output
            .append(caption2)
            .append(separator)
    }
    for (element in stackTrace) {
        output
            .append(element.toString())
            .append(separator)
    }
    val suppressed = throwable.suppressed
    for (element in suppressed) {
        writeStackTrace(
            element,
            output,
            "Suppressed: ",
            element.toString(),
            separator,
            written
        )
    }
    val cause = throwable.cause
    if (cause != null) {
        writeStackTrace(
            cause,
            output,
            "Caused by: ",
            cause.toString(),
            separator,
            written
        )
    }
}
