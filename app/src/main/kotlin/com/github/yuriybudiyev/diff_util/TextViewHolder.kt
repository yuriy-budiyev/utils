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
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.yuriybudiyev.utils.colors.Colors

class TextViewHolder(
    context: Context,
    colors: Colors,
): RecyclerView.ViewHolder(LinearLayout(context)) {

    private val contentView: LinearLayout = itemView as LinearLayout
    private val textView: TextView = TextView(context)
    private val checkBox: CheckBox = CheckBox(context)
    private var item: TextItem? = null

    fun bind(item: TextItem) {
        this.item = item
        textView.text = item.text
        checkBox.isChecked = item.isChecked
    }

    fun bind(
        item: TextItem,
        payload: TextItemPayload,
    ) {
        this.item = item
        if (payload.isTextChanged) {
            textView.text = item.text
        }
        if (payload.isCheckedChanged) {
            checkBox.isChecked = item.isChecked
        }
    }

    init {
        contentView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        contentView.background = ColorDrawable(colors.background)
        contentView.orientation = LinearLayout.HORIZONTAL
        with(context) {
            contentView.setPaddingRelative(
                convertDpToPx(16.0F),
                convertDpToPx(12.0F),
                convertDpToPx(16.0F),
                convertDpToPx(12.0F),
            )
        }
        textView.setTypeface(
            Typeface.create(
                "sans-serif",
                Typeface.NORMAL
            )
        )
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            16F
        )
        textView.setTextColor(colors.onBackground)
        contentView.addView(
            textView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
        )
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            item?.isChecked = isChecked
        }
        checkBox.buttonTintList = ColorStateList.valueOf(colors.onBackground)
        contentView.addView(
            checkBox,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        )
    }
}
