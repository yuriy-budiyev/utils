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

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.yuriybudiyev.core.EdgeToEdgeActivity
import com.github.yuriybudiyev.diff_util.DiffUtilActivity
import com.github.yuriybudiyev.diff_util.convertDpToPx
import com.github.yuriybudiyev.utils.R
import com.github.yuriybudiyev.utils.colors.buildButtonColorStateList
import com.github.yuriybudiyev.utils.colors.getColors

class MainActivity: EdgeToEdgeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colors = getColors()
        val contentView = ConstraintLayout(this)
        contentView.id = View.generateViewId()
        val diffUtilButton = Button(this)
        diffUtilButton.id = View.generateViewId()
        diffUtilButton.backgroundTintList = buildButtonColorStateList(colors)
        diffUtilButton.setTextColor(colors.onButtonBackground)
        diffUtilButton.isAllCaps = false
        diffUtilButton.text = getString(R.string.activity_diff_util)
        diffUtilButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DiffUtilActivity::class.java
                )
            )
        }
        contentView.addView(diffUtilButton,
            ConstraintLayout
                .LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                .apply {
                    startToStart = contentView.id
                    topToTop = contentView.id
                    endToEnd = contentView.id
                    topMargin = convertDpToPx(12.0F)
                    marginStart = convertDpToPx(16.0F)
                    marginEnd = convertDpToPx(16.0F)
                })
        setContentView(
            contentView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
}
