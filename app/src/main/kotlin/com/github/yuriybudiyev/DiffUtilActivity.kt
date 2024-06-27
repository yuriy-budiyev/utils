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

package com.github.yuriybudiyev

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.yuriybudiyev.utils.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt

/**
 * Simple sample
 */
class DiffUtilActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = FrameLayout(this)
        recyclerView = RecyclerView(this)
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
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )
        val oldItems = buildOldItems()
        val newItems = buildNewItems()
        var oldItemsDisplayed = true
        val adapter = TextListAdapter(
            this,
            oldItems
        )
        recyclerView.adapter = adapter
        val changeButton = FloatingActionButton(this)
        changeButton.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
        changeButton.setImageResource(R.drawable.ic_refresh)
        changeButton.imageTintList = ColorStateList.valueOf(Color.WHITE)
        contentView.addView(
            changeButton,
            FrameLayout
                .LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM or Gravity.END
                )
                .apply {
                    marginEnd = convertDpToPx(16F)
                    bottomMargin = convertDpToPx(16F)
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

    private lateinit var recyclerView: RecyclerView
}

private data class TextItem(
    val id: Int,
    val text: String,
    var isChecked: Boolean,
)

private class TextListDiffCallback(
    private val oldItems: List<TextItem>,
    private val newItems: List<TextItem>,
): DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        oldItems.size

    override fun getNewListSize(): Int =
        newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.text == newItem.text
            && oldItem.isChecked == newItem.isChecked
    }
}

private class TextListAdapter(
    private val context: Context,
    var items: List<TextItem>,
):
    RecyclerView.Adapter<TextViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TextViewHolder =
        TextViewHolder(context)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(
        holder: TextViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}

private class TextViewHolder(context: Context): ViewHolder(LinearLayout(context)) {

    private val contentView: LinearLayout = itemView as LinearLayout
    private val textView: TextView = TextView(context)
    private val checkBox: CheckBox = CheckBox(context)
    private var item: TextItem? = null

    fun bind(item: TextItem) {
        this.item = item
        textView.text = item.text
        checkBox.isChecked = item.isChecked
    }

    init {
        contentView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        contentView.orientation = LinearLayout.HORIZONTAL
        with(context) {
            contentView.setPaddingRelative(
                convertDpToPx(16F),
                convertDpToPx(12F),
                convertDpToPx(16F),
                convertDpToPx(12F),
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
        textView.setTextColor(Color.BLACK)
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
        checkBox.buttonTintList = ColorStateList.valueOf(Color.BLACK)
        contentView.addView(
            checkBox,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        )
    }
}

private fun Context.convertDpToPx(dp: Float): Int =
    TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
        .roundToInt()

private fun buildOldItems(): List<TextItem> =
    listOf(
        TextItem(
            0,
            "A",
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

private fun buildNewItems(): List<TextItem> =
    listOf(
        TextItem(
            11,
            "B",
            true
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
            0,
            "G",
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
