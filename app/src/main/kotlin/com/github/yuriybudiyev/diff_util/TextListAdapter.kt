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
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.yuriybudiyev.utils.colors.Colors

class TextListAdapter(
    private val context: Context,
    private val colors: Colors,
    var items: List<TextItem>,
):
    RecyclerView.Adapter<TextViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TextViewHolder =
        TextViewHolder(
            context,
            colors
        )

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(
        holder: TextViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val payload = payloads.firstOrNull() as? TextItemPayload
        if (payload != null) {
            holder.bind(
                items[position],
                payload
            )
        } else {
            onBindViewHolder(
                holder,
                position
            )
        }
    }

    override fun onBindViewHolder(
        holder: TextViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
