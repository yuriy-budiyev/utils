/*
 * MIT License
 *
 * Copyright (c) 2026 Yuriy Budiyev
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

package com.github.yuriybudiyev.utils.collections

import kotlin.math.abs

class HashTable<Key, Value>(capacity: Int = 10) {

    var size: Int = 0
        private set

    operator fun set(
        key: Key,
        value: Value,
    ) {
        val hash = hash(key)
        val initialPosition = hash % table.size
        var position = initialPosition
        while (true) {
            val entry = table[position]
            if (entry == null) {
                break
            }
            if (entry.key == key) {
                entry.value = value
                return
            }
            position = (position + 1) % table.size
            if (position == initialPosition) {
                grow()
                position = hash % table.size
            }
        }
        size += 1
        table[position] = Entry(
            key,
            value,
        )
    }

    operator fun get(key: Key): Value? {
        val initialPosition = hash(key) % table.size
        var position = initialPosition
        while (true) {
            val entry = table[position]
            if (entry != null && entry.key == key) {
                return entry.value
            }
            position = (position + 1) % table.size
            if (position == initialPosition) {
                return null
            }
        }
    }

    fun remove(key: Key): Value? {
        val initialPosition = hash(key) % table.size
        var position = initialPosition
        while (true) {
            val entry = table[position]
            if (entry != null && entry.key == key) {
                table[position] = null
                size--
                return entry.value
            }
            position = (position + 1) % table.size
            if (position == initialPosition) {
                return null
            }
        }
    }

    fun clear() {
        size = 0
        for (position in 0..<table.size) {
            table[position] = null
        }
    }

    private fun hash(key: Key): Int =
        abs(key.hashCode())

    private fun grow() {
        val oldTable = table
        val newTable = arrayOfNulls<Entry>(oldTable.size * 2)
        for (entry in oldTable) {
            if (entry == null) {
                continue
            }
            var position = hash(entry.key) % newTable.size
            while (true) {
                val newEntry = newTable[position]
                if (newEntry == null || newEntry.key == entry.key) {
                    break
                }
                position = (position + 1) % newTable.size
            }
            newTable[position] = entry
        }
        table = newTable
    }

    override fun toString(): String =
        buildString {
            append('[')
            var prevExists = false
            for (entry in table) {
                if (entry != null) {
                    if (prevExists) {
                        append(',')
                    }
                    append(entry.toString())
                    prevExists = true
                }
            }
            append(']')
        }

    private var table: Array<Entry?>

    init {
        require(capacity > 0) { "Capacity must be greater than zero" }
        table = arrayOfNulls(capacity)
    }

    private inner class Entry(
        val key: Key,
        var value: Value,
    ) {

        override fun toString(): String =
            "(key=$key,value=$value)"
    }
}
