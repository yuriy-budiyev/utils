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

package com.github.yuriybudiyev.utils.colors

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import kotlin.math.roundToInt

fun Context.getColors(): Colors =
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> Colors(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            Color.WHITE,
            Color.WHITE,
            Color.BLACK

        )
        else -> Colors(
            Color.BLACK,
            Color.WHITE,
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            Color.WHITE
        )
    }

fun buildButtonColorStateList(colors: Colors): ColorStateList =
    ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_pressed)
        ),
        intArrayOf(
            colors.buttonBackground,
            colors.buttonBackgroundPressed,
        )
    )

@ColorInt
fun applyAlpha(
    @ColorInt color: Int,
    @FloatRange(
        from = 0.0,
        to = 1.0
    ) alpha: Float,
): Int =
    applyAlpha(
        color,
        (255 * alpha).roundToInt()
    )

@ColorInt
fun applyAlpha(
    @ColorInt color: Int,
    @IntRange(
        from = 0,
        to = 255
    ) alpha: Int,
): Int =
    Color.argb(
        alpha,
        Color.red(color),
        Color.green(color),
        Color.blue(color)
    )

data class Colors(

    @ColorInt
    val background: Int,

    @ColorInt
    val onBackground: Int,

    @ColorInt
    val buttonBackground: Int,

    @ColorInt
    val buttonBackgroundPressed: Int,

    @ColorInt
    val onButtonBackground: Int,

    @ColorInt
    val separator: Int,
)
