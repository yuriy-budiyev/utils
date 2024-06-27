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

package com.github.yuriybudiyev.diff_util

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

fun Context.convertDpToPx(dp: Float): Int =
    TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
        .roundToInt()

fun buildOldItems(): List<TextItem> =
    listOf(
        TextItem(
            0,
            "P",
            false
        ),
        TextItem(
            1,
            "A",
            true
        ),
        TextItem(
            2,
            "B",
            true
        ),
        TextItem(
            3,
            "C",
            true
        ),
        TextItem(
            4,
            "X",
            true
        ),
        TextItem(
            5,
            "B",
            true
        ),
        TextItem(
            6,
            "Z",
            true
        ),
        TextItem(
            7,
            "T",
            true
        ),
        TextItem(
            8,
            "Z",
            true
        ),
        TextItem(
            10,
            "G",
            true
        ),
        TextItem(
            11,
            "H",
            true
        ),
        TextItem(
            12,
            "I",
            true
        ),
        TextItem(
            9,
            "C",
            true
        ),
        TextItem(
            13,
            "B",
            true
        ),
        TextItem(
            14,
            "G",
            true
        ),
        TextItem(
            15,
            "L",
            true
        ),
        TextItem(
            16,
            "L",
            true
        ),
    )

fun buildNewItems(): List<TextItem> =
    listOf(
        TextItem(
            0,
            "P",
            true
        ),
        TextItem(
            11,
            "A",
            false
        ),
        TextItem(
            14,
            "A",
            true
        ),
        TextItem(
            15,
            "C",
            true
        ),
        TextItem(
            16,
            "C",
            true
        ),
        TextItem(
            4,
            "X",
            true
        ),
        TextItem(
            5,
            "Z",
            true
        ),
        TextItem(
            6,
            "E",
            true
        ),
        TextItem(
            10,
            "G",
            true
        ),
        TextItem(
            7,
            "T",
            true
        ),
        TextItem(
            8,
            "C",
            true
        ),
        TextItem(
            9,
            "C",
            true
        ),
        TextItem(
            13,
            "B",
            true
        ),
        TextItem(
            1,
            "L",
            true
        ),
        TextItem(
            2,
            "M",
            true
        ),
    )
