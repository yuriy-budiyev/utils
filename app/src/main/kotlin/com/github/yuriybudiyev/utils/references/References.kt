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

package com.github.yuriybudiyev.utils.references

import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Returns a new soft reference that refers to the [this] object
 *
 * Going from strongest to weakest, the different levels of reachability
 * reflect the life cycle of an object.
 *
 * They are operationally defined as follows:
 * - An object is strongly reachable if it can be reached by some thread without traversing any
 *   reference objects. A newly-created object is strongly reachable by the thread that created it.
 * - An object is softly reachable if it is not strongly reachable but can be reached by
 *   traversing a soft reference.
 * - An object is weakly reachable if it is neither strongly nor softly reachable but can be
 *   reached by traversing a weak reference. When the weak references to a weakly-reachable object
 *   are cleared, the object becomes eligible for finalization.
 * - An object is phantom reachable if it is neither strongly, softly, nor weakly reachable, it
 *   has been finalized, and some phantom reference refers to it.
 * - Finally, an object is unreachable, and therefore eligible for reclamation, when it is not
 *   reachable in any of the above ways.
 */
fun <T> T.asSoftReference(queue: ReferenceQueue<T>? = null): SoftReference<T> =
    SoftReference(
        this,
        queue
    )

/**
 * Returns a new weak reference that refers to the [this] object
 *
 * Going from strongest to weakest, the different levels of reachability
 * reflect the life cycle of an object.
 *
 * They are operationally defined as follows:
 * - An object is strongly reachable if it can be reached by some thread without traversing any
 *   reference objects. A newly-created object is strongly reachable by the thread that created it.
 * - An object is softly reachable if it is not strongly reachable but can be reached by
 *   traversing a soft reference.
 * - An object is weakly reachable if it is neither strongly nor softly reachable but can be
 *   reached by traversing a weak reference. When the weak references to a weakly-reachable object
 *   are cleared, the object becomes eligible for finalization.
 * - An object is phantom reachable if it is neither strongly, softly, nor weakly reachable, it
 *   has been finalized, and some phantom reference refers to it.
 * - Finally, an object is unreachable, and therefore eligible for reclamation, when it is not
 *   reachable in any of the above ways.
 */
fun <T> T.asWeakReference(queue: ReferenceQueue<T>? = null): WeakReference<T> =
    WeakReference(
        this,
        queue
    )

/**
 * Returns a new phantom reference that refers to the [this] object
 *
 * Going from strongest to weakest, the different levels of reachability
 * reflect the life cycle of an object.
 *
 * They are operationally defined as follows:
 * - An object is strongly reachable if it can be reached by some thread without traversing any
 *   reference objects. A newly-created object is strongly reachable by the thread that created it.
 * - An object is softly reachable if it is not strongly reachable but can be reached by
 *   traversing a soft reference.
 * - An object is weakly reachable if it is neither strongly nor softly reachable but can be
 *   reached by traversing a weak reference. When the weak references to a weakly-reachable object
 *   are cleared, the object becomes eligible for finalization.
 * - An object is phantom reachable if it is neither strongly, softly, nor weakly reachable, it
 *   has been finalized, and some phantom reference refers to it.
 * - Finally, an object is unreachable, and therefore eligible for reclamation, when it is not
 *   reachable in any of the above ways.
 */
fun <T> T.asPhantomReference(queue: ReferenceQueue<T>): PhantomReference<T> =
    PhantomReference(
        this,
        queue
    )
