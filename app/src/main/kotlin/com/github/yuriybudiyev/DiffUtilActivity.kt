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
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.yuriybudiyev.utils.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt

/**
 * Simple sample, intentionally in one file
 */
class DiffUtilActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colors = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> Colors(
                Color.WHITE,
                Color.BLACK,
                Color.BLACK,
                Color.WHITE,
                Color.BLACK

            )
            else -> Colors(
                Color.BLACK,
                Color.WHITE,
                Color.WHITE,
                Color.BLACK,
                Color.WHITE
            )
        }
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

    private lateinit var recyclerView: RecyclerView
}

private data class Colors(

    @ColorInt
    val background: Int,

    @ColorInt
    val onBackground: Int,

    @ColorInt
    val buttonBackground: Int,

    @ColorInt
    val onButtonBackground: Int,

    @ColorInt
    val separator: Int,
)

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
    ) {
        holder.bind(items[position])
    }
}

private class TextViewHolder(
    context: Context,
    colors: Colors,
): ViewHolder(LinearLayout(context)) {

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
