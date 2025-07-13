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

import android.content.res.ColorStateList
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.yuriybudiyev.core.EdgeToEdgeWithSystemBarsInsetsActivity
import com.github.yuriybudiyev.diff_util.colors.getColors
import com.github.yuriybudiyev.utils.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DiffUtilActivity: EdgeToEdgeWithSystemBarsInsetsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colors = getColors()
        val contentView = FrameLayout(this)
        val recyclerView = RecyclerView(this)
        setContentView(
            contentView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        contentView.addView(
            recyclerView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        val decoration = DividerItemDecoration(
            this,
            RecyclerView.VERTICAL
        )
        decoration.setDrawable(ShapeDrawable(RectShape()).apply {
            paint.color = colors.separator
            intrinsicHeight = convertDpToPx(1.0F).coerceAtLeast(1)
        })
        recyclerView.addItemDecoration(decoration)
        val oldItems = buildOldItems()
        val newItems = buildNewItems()
        var oldItemsDisplayed = true
        val adapter = TextListAdapter(
            this,
            colors,
            oldItems
        )
        recyclerView.adapter = adapter
        val changeButton = FloatingActionButton(this)
        changeButton.backgroundTintList = ColorStateList.valueOf(colors.buttonBackground)
        changeButton.setImageResource(R.drawable.ic_refresh)
        changeButton.imageTintList = ColorStateList.valueOf(colors.onButtonBackground)
        contentView.addView(
            changeButton,
            FrameLayout
                .LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM or Gravity.END
                )
                .apply {
                    marginEnd = convertDpToPx(16.0F)
                    bottomMargin = convertDpToPx(16.0F)
                }
        )
        changeButton.setOnClickListener {
            val items = if (oldItemsDisplayed) {
                oldItemsDisplayed = false
                newItems
            } else {
                oldItemsDisplayed = true
                oldItems
            }
            val diffResult = DiffUtil.calculateDiff(
                TextListDiffCallback(
                    adapter.items,
                    items
                )
            )
            adapter.items = items
            diffResult.dispatchUpdatesTo(adapter)
        }
    }
}
