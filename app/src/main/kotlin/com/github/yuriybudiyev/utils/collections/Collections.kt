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

package com.github.yuriybudiyev.utils.collections

/**
 * Swaps the elements at the [first] and [second] positions in [this] list.
 */
fun <E> MutableList<E>.swap(
    first: Int,
    second: Int,
) {
    set(
        first,
        set(
            second,
            get(first)
        )
    )
}

/**
 * Removes first element matching the given [predicate] from this collection
 */
inline fun <E> MutableIterable<E>.removeFirst(predicate: (element: E) -> Boolean) {
    val iterator = iterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.remove()
            break
        }
    }
}

/**
 * Returns `true` if this collection contains any of [elements]
 */
fun <E> Collection<E>.containsAny(elements: Iterable<E>): Boolean {
    for (element in elements) {
        if (contains(element)) {
            return true
        }
    }
    return false
}

/**
 * Returns a shallow copy of this [ArrayList] instance
 */
@Suppress("UNCHECKED_CAST")
fun <E> ArrayList<E>.copy(): ArrayList<E> =
    clone() as ArrayList<E>

/**
 * Returns an [ArrayList] containing all elements of this collection
 */
fun <E> Collection<E>.toArrayList(): ArrayList<E> =
    ArrayList(this)

/**
 * Returns a list with [size] amount of `null` values
 */
inline fun <reified E> listOfNulls(size: Int): List<E?> =
    arrayOfNulls<E>(size).asList()

/**
 * Returns a mutable list with [size] amount of `null` values
 */
fun <E> mutableListOfNulls(size: Int): MutableList<E?> =
    arrayListOfNulls(size)

/**
 * Returns an [ArrayList] list with [size] amount of `null` values
 */
fun <E> arrayListOfNulls(size: Int): ArrayList<E?> {
    val list = ArrayList<E?>(size)
    repeat(size) {
        list.add(null)
    }
    return list
}

/**
 * Returns a [HashMap] for a known number of elements ([expectedSize])
 */
fun <K, V> HashMap(expectedSize: Int): HashMap<K, V> =
    HashMap(
        mapCapacityForExpectedSize(expectedSize),
        0.75f
    )

/**
 * Returns a [LinkedHashMap] for a known number of elements ([expectedSize]),
 * and given [accessOrder] (ordering mode - `true` for access-order, `false` for insertion-order)
 */
fun <K, V> LinkedHashMap(
    expectedSize: Int,
    accessOrder: Boolean = false,
): LinkedHashMap<K, V> =
    LinkedHashMap(
        mapCapacityForExpectedSize(expectedSize),
        0.75f,
        accessOrder
    )

/**
 * Returns a [HashSet] for a known number of elements ([expectedSize])
 */
fun <E> HashSet(expectedSize: Int): HashSet<E> =
    HashSet(
        mapCapacityForExpectedSize(expectedSize),
        0.75f
    )

/**
 * Returns a [LinkedHashSet] for a known number of elements ([expectedSize])
 */
fun <E> LinkedHashSet(expectedSize: Int): LinkedHashSet<E> =
    LinkedHashSet(
        mapCapacityForExpectedSize(expectedSize),
        0.75f
    )

/**
 * Initial capacity for [HashMap] with 0.75f load factor and [expectedSize]
 */
private fun mapCapacityForExpectedSize(expectedSize: Int): Int =
    when {
        expectedSize < 3 -> {
            expectedSize + 1
        }

        expectedSize < Int.MAX_VALUE / 2 + 1 -> {
            expectedSize + expectedSize / 3
        }

        else -> {
            Int.MAX_VALUE
        }
    }

/**
 * Returns a [Map] containing the elements from the [this] collection indexed by the key
 * returned from [keySelector] function applied to each element.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original collection.
 *
 * Null keys are skipped.
 */
inline fun <K, V> Iterable<V>.associateByNotNull(keySelector: (V) -> K?): Map<K, V> =
    associateByNotNull(keySelector) { it }

/**
 * Populates and returns the [destination] mutable map with elements from [this]
 * collection indexed by the key returned from [keySelector] function applied to each element.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * Null keys are skipped.
 */
inline fun <K, V, D: MutableMap<K, V>> Iterable<V>.associateByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
): D =
    associateByNotNullTo(
        destination,
        keySelector
    ) { it }

/**
 * Returns a [Map] containing the values provided by [valueTransform] and indexed
 * by [keySelector] function applied to elements of the [this] collection.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original collection.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R> Iterable<V>.associateByNotNull(
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): Map<K, R> =
    associateByNotNullTo(
        LinkedHashMap(),
        keySelector,
        valueTransform
    )

/**
 * Populates and returns the [destination] mutable map with values provided by [valueTransform]
 * and indexed by [keySelector] function applied to elements of the [this] sequence.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R, D: MutableMap<K, R>> Iterable<V>.associateByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): D {
    for (value in this) {
        val key = keySelector(value)
        if (key != null) {
            val transformed = valueTransform(value)
            if (transformed != null) {
                destination[key] = transformed
            }
        }
    }
    return destination
}

/**
 * Returns a [Map] containing the elements from the [this] sequence indexed by the key
 * returned from [keySelector] function applied to each element.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original sequence.
 *
 * Null keys are skipped.
 */
inline fun <K, V> Sequence<V>.associateByNotNull(keySelector: (V) -> K?): Map<K, V> =
    associateByNotNull(keySelector) { it }

/**
 * Returns a [Map] containing the values provided by [valueTransform] and indexed
 * by [keySelector] function applied to elements of the [this] sequence.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original sequence.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R> Sequence<V>.associateByNotNull(
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): Map<K, R> =
    associateByNotNullTo(
        LinkedHashMap(),
        keySelector,
        valueTransform
    )

/**
 * Populates and returns the [destination] mutable map with values provided by [valueTransform]
 * and indexed by [keySelector] function applied to elements of the [this] sequence.
 *
 * If any two elements would have the same key returned by [keySelector]
 * the last one gets added to the map.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R, D: MutableMap<K, R>> Sequence<V>.associateByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): D {
    for (value in this) {
        val key = keySelector(value)
        if (key != null) {
            val transformed = valueTransform(value)
            if (transformed != null) {
                destination[key] = transformed
            }
        }
    }
    return destination
}

/**
 * Groups elements of [this] collection by the key returned by the given [keySelector] function
 * applied to each element and returns a map where each group key is associated
 * with a list of corresponding elements.
 *
 * The returned map preserves the entry iteration order of the keys produced from [this] collection.
 *
 * Null keys are skipped.
 */
inline fun <K, V> Iterable<V>.groupByNotNull(keySelector: (V) -> K?): Map<K, List<V>> =
    groupByNotNull(keySelector) { it }

/**
 * Groups elements of [this] sequence by the key returned by the given [keySelector] function
 * applied to each element and puts to the [destination] map each group key associated with
 * a list of corresponding values.
 *
 * Null keys are skipped.
 */
inline fun <K, V, D: MutableMap<K, MutableList<V>>> Iterable<V>.groupByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
): D =
    groupByNotNullTo(
        destination,
        keySelector
    ) { it }

/**
 * Groups values returned by the [valueTransform] function applied to each element
 * of [this] collection by the key returned by the given [keySelector] function applied to the
 * element and returns a map where each group key is associated with a list of corresponding values.
 *
 * The returned map preserves the entry iteration order of the keys produced from [this] collection.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R> Iterable<V>.groupByNotNull(
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): Map<K, List<R>> =
    groupByNotNullTo(
        LinkedHashMap(),
        keySelector,
        valueTransform
    )

/**
 * Groups values returned by the [valueTransform] function applied to each element
 * of [this] sequence by the key returned by the given [keySelector] function applied to the element
 * and puts to the [destination] map each group key associated with a list of corresponding values.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R, D: MutableMap<K, MutableList<R>>> Iterable<V>.groupByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): D {
    for (value in this) {
        val key = keySelector(value)
        if (key != null) {
            val transformed = valueTransform(value)
            if (transformed != null) {
                destination.getOrPut(key) { ArrayList() } += transformed
            }
        }
    }
    return destination
}

/**
 * Groups elements of [this] sequence by the key returned by the given [keySelector] function
 * applied to each element and returns a map where each group key is associated with a list
 * of corresponding elements.
 *
 * The returned map preserves the entry iteration order of the keys produced from [this] collection.
 *
 * Null keys are skipped.
 */
inline fun <K, V> Sequence<V>.groupByNotNull(keySelector: (V) -> K?): Map<K, List<V>> =
    groupByNotNull(keySelector) { it }

/**
 * Groups elements of [this] sequence by the key returned by the given [keySelector] function
 * applied to each element and puts to the [destination] map each group key associated with
 * a list of corresponding values.
 *
 * Null keys are skipped.
 */
inline fun <K, V, D: MutableMap<K, MutableList<V>>> Sequence<V>.groupByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
): D =
    groupByNotNullTo(
        destination,
        keySelector
    ) { it }

/**
 * Groups values returned by the [valueTransform] function applied to each element
 * of [this] sequence by the key returned by the given [keySelector] function applied to the element
 * and returns a map where each group key is associated with a list of corresponding values.
 *
 * The returned map preserves the entry iteration order of the keys produced from [this] collection.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R> Sequence<V>.groupByNotNull(
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): Map<K, List<R>> =
    groupByNotNullTo(
        LinkedHashMap(),
        keySelector,
        valueTransform
    )

/**
 * Groups values returned by the [valueTransform] function applied to each element
 * of [this] sequence by the key returned by the given [keySelector] function applied to the element
 * and puts to the [destination] map each group key associated with a list of corresponding values.
 *
 * Null keys and values are skipped.
 */
inline fun <K, V, R, D: MutableMap<K, MutableList<R>>> Sequence<V>.groupByNotNullTo(
    destination: D,
    keySelector: (V) -> K?,
    valueTransform: (V) -> R?,
): D {
    for (value in this) {
        val key = keySelector(value)
        if (key != null) {
            val transformed = valueTransform(value)
            if (transformed != null) {
                destination.getOrPut(key) { ArrayList() } += transformed
            }
        }
    }
    return destination
}

/**
 * Returns a [Set] of a values returned by the [valueTransform] function applied
 * to each element of [this] collection.
 *
 * Null values are skipped.
 */
inline fun <E, V> Iterable<E>.toSetNotNull(valueTransform: (E) -> V?): Set<V> =
    toCollectionNotNull(
        LinkedHashSet(),
        valueTransform
    )

/**
 * Returns a [Set] of a values returned by the [valueTransform] function applied
 * to each element of [this] collection.
 *
 * Null values are skipped.
 */
inline fun <E, V> Sequence<E>.toSetNotNull(valueTransform: (E) -> V?): Set<V> =
    toCollectionNotNull(
        LinkedHashSet(),
        valueTransform
    )

/**
 * Populates and returns the [destination] mutable collection with values provided by [valueTransform]
 * function applied to each element of [this] collection.
 *
 * Null values are skipped.
 */
inline fun <E, V, D: MutableCollection<V>> Iterable<E>.toCollectionNotNull(
    destination: D,
    valueTransform: (E) -> V?,
): D {
    for (value in this) {
        val transformed = valueTransform(value)
        if (transformed != null) {
            destination += transformed
        }
    }
    return destination
}

/**
 * Populates and returns the [destination] mutable collection with values provided by [valueTransform]
 * function applied to each element of [this] collection.
 *
 * Null values are skipped.
 */
inline fun <E, V, D: MutableCollection<V>> Sequence<E>.toCollectionNotNull(
    destination: D,
    valueTransform: (E) -> V?,
): D {
    for (value in this) {
        val transformed = valueTransform(value)
        if (transformed != null) {
            destination += transformed
        }
    }
    return destination
}

/**
 * Join [Array] to [String] skipping nulls
 *
 * @param separator Separator (", " by default)
 * @param valueTransform Transformation function ([Any.toString] by default)
 *                  if this function returns null then it will be skipped
 */
inline fun <E> Array<E>.joinToStringNotNull(
    separator: String = ", ",
    valueTransform: (E) -> String? = { it?.toString() },
): String {
    val builder = StringBuilder()
    var prevExists = false
    for (element in this) {
        if (prevExists) {
            builder.append(separator)
        }
        val transformed = valueTransform(element)
        if (transformed != null) {
            builder.append(transformed)
            prevExists = true
        } else {
            prevExists = false
        }
    }
    return builder.toString()
}

/**
 * Join [Iterable] to [String] skipping nulls
 *
 * @param separator Separator (", " by default)
 * @param valueTransform Transformation function ([Any.toString] by default)
 *                  if this function returns null then it will be skipped
 */
inline fun <E> Iterable<E>.joinToStringNotNull(
    separator: String = ", ",
    valueTransform: (E) -> String? = { it?.toString() },
): String {
    val builder = StringBuilder()
    var prevExists = false
    for (element in this) {
        if (prevExists) {
            builder.append(separator)
        }
        val transformed = valueTransform(element)
        if (transformed != null) {
            builder.append(transformed)
            prevExists = true
        } else {
            prevExists = false
        }
    }
    return builder.toString()
}

/**
 * Join [Sequence] to [String] skipping nulls
 *
 * @param separator Separator (", " by default)
 * @param valueTransform Transformation function ([Any.toString] by default)
 *                  if this function returns null then it will be skipped
 */
inline fun <E> Sequence<E>.joinToStringNotNull(
    separator: String = ", ",
    valueTransform: (E) -> String? = { it?.toString() },
): String {
    val builder = StringBuilder()
    var prevExists = false
    for (element in this) {
        if (prevExists) {
            builder.append(separator)
        }
        val transformed = valueTransform(element)
        if (transformed != null) {
            builder.append(transformed)
            prevExists = true
        } else {
            prevExists = false
        }
    }
    return builder.toString()
}
