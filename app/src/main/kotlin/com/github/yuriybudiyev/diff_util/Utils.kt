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
            id = 0,
            text = "P",
            isChecked = false
        ),
        TextItem(
            id = 1,
            text = "A",
            isChecked = true
        ),
        TextItem(
            id = 2,
            text = "B",
            isChecked = true
        ),
        TextItem(
            id = 3,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 4,
            text = "X",
            isChecked = true
        ),
        TextItem(
            id = 5,
            text = "B",
            isChecked = true
        ),
        TextItem(
            id = 6,
            text = "Z",
            isChecked = true
        ),
        TextItem(
            id = 7,
            text = "T",
            isChecked = true
        ),
        TextItem(
            id = 8,
            text = "Z",
            isChecked = true
        ),
        TextItem(
            id = 10,
            text = "G",
            isChecked = true
        ),
        TextItem(
            id = 11,
            text = "H",
            isChecked = true
        ),
        TextItem(
            id = 12,
            text = "I",
            isChecked = true
        ),
        TextItem(
            id = 9,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 13,
            text = "B",
            isChecked = true
        ),
        TextItem(
            id = 14,
            text = "G",
            isChecked = true
        ),
        TextItem(
            id = 15,
            text = "L",
            isChecked = true
        ),
        TextItem(
            id = 16,
            text = "L",
            isChecked = true
        ),
    )

fun buildNewItems(): List<TextItem> =
    listOf(
        TextItem(
            id = 0,
            text = "P",
            isChecked = true
        ),
        TextItem(
            id = 11,
            text = "A",
            isChecked = false
        ),
        TextItem(
            id = 14,
            text = "A",
            isChecked = true
        ),
        TextItem(
            id = 15,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 16,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 4,
            text = "X",
            isChecked = true
        ),
        TextItem(
            id = 5,
            text = "Z",
            isChecked = true
        ),
        TextItem(
            id = 6,
            text = "E",
            isChecked = true
        ),
        TextItem(
            id = 10,
            text = "G",
            isChecked = true
        ),
        TextItem(
            id = 7,
            text = "T",
            isChecked = true
        ),
        TextItem(
            id = 8,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 9,
            text = "C",
            isChecked = true
        ),
        TextItem(
            id = 13,
            text = "B",
            isChecked = true
        ),
        TextItem(
            id = 1,
            text = "L",
            isChecked = true
        ),
        TextItem(
            id = 2,
            text = "M",
            isChecked = true
        ),
    )
