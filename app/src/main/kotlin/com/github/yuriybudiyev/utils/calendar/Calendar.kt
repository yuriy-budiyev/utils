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

package com.github.yuriybudiyev.utils.calendar

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

/**
 * Returns a [Calendar] with [this] timestamp as time value
 *
 * @see GregorianCalendar
 */
fun Long.toCalendar(): Calendar =
    createCalendar(this)

/**
 * Returns new [Calendar] with current date and time
 *
 * @see GregorianCalendar
 */
fun createCalendar(): Calendar =
    GregorianCalendar()

/**
 * Returns new [Calendar] with given [timeInMillis]
 *
 * @see GregorianCalendar
 */
fun createCalendar(timeInMillis: Long): Calendar {
    val calendar = createCalendar()
    calendar.timeInMillis = timeInMillis
    return calendar
}

/**
 * Returns new [Calendar] with given [year], [month], and [dayOfMonth]
 *
 * @see GregorianCalendar
 */
fun createCalendar(
    year: Int,
    month: Int,
    dayOfMonth: Int,
): Calendar {
    val calendar = createCalendar()
    calendar.year = year
    calendar.month = month
    calendar.dayOfMonth = dayOfMonth
    calendar.setStartOfTheDay()
    return calendar
}

/**
 * Set [Calendar.HOUR_OF_DAY], [Calendar.MINUTE],
 * [Calendar.SECOND] and [Calendar.MILLISECOND] values to `0`
 */
fun Calendar.setStartOfTheDay(): Calendar {
    set(
        Calendar.HOUR_OF_DAY,
        0
    )
    set(
        Calendar.MINUTE,
        0
    )
    set(
        Calendar.SECOND,
        0
    )
    set(
        Calendar.MILLISECOND,
        0
    )
    return this
}

/**
 * Set [Calendar.HOUR_OF_DAY] to `23`, [Calendar.MINUTE] and [Calendar.SECOND] to `59`,
 * [Calendar.MILLISECOND] to `999`
 */
fun Calendar.setEndOfTheDay(): Calendar {
    set(
        Calendar.HOUR_OF_DAY,
        23
    )
    set(
        Calendar.MINUTE,
        59
    )
    set(
        Calendar.SECOND,
        59
    )
    set(
        Calendar.MILLISECOND,
        999
    )
    return this
}

/**
 * Set [Calendar.HOUR_OF_DAY] to `12`; [Calendar.MINUTE],
 * [Calendar.SECOND] and [Calendar.MILLISECOND] to `0`
 */
fun Calendar.setNoon(): Calendar {
    set(
        Calendar.HOUR_OF_DAY,
        12
    )
    set(
        Calendar.MINUTE,
        0
    )
    set(
        Calendar.SECOND,
        0
    )
    set(
        Calendar.MILLISECOND,
        0
    )
    return this
}

/**
 * Set [Calendar.DAY_OF_WEEK] to first day of week, and set start of the day
 *
 * @see setStartOfTheDay
 */
fun Calendar.setStartOfTheWeek(): Calendar {
    set(
        Calendar.DAY_OF_WEEK,
        firstDayOfWeek
    )
    setStartOfTheDay()
    return this
}

/**
 * Set [Calendar.DAY_OF_WEEK] to last day of week, and set end of the day
 *
 * @see setEndOfTheDay
 */
fun Calendar.setEndOfTheWeek(): Calendar {
    set(
        Calendar.DAY_OF_WEEK,
        firstDayOfWeek
    )
    add(
        Calendar.DAY_OF_WEEK,
        6
    )
    setEndOfTheDay()
    return this
}

/**
 * Set [Calendar.DAY_OF_MONTH] to first day of month, and set start of the day
 *
 * @see setStartOfTheDay
 */
fun Calendar.setStartOfTheMonth(): Calendar {
    set(
        Calendar.DAY_OF_MONTH,
        getActualMinimum(Calendar.DAY_OF_MONTH)
    )
    setStartOfTheDay()
    return this
}

/**
 * Set [Calendar.DAY_OF_MONTH] to last day of month, and set end of the day
 *
 * @see setEndOfTheDay
 */
fun Calendar.setEndOfTheMonth(): Calendar {
    set(
        Calendar.DAY_OF_MONTH,
        getActualMaximum(Calendar.DAY_OF_MONTH)
    )
    setEndOfTheDay()
    return this
}

/**
 * Add specific amount of [years]
 *
 * @see Calendar.YEAR
 */
fun Calendar.addYears(years: Int): Calendar {
    add(
        Calendar.YEAR,
        years
    )
    return this
}

/**
 * Remove specific amount of [years]
 *
 * @see Calendar.YEAR
 */
fun Calendar.removeYears(years: Int): Calendar {
    add(
        Calendar.YEAR,
        -years
    )
    return this
}

/**
 * Add specific amount of [months]
 *
 * @see Calendar.MONTH
 */
fun Calendar.addMonths(months: Int): Calendar {
    add(
        Calendar.MONTH,
        months
    )
    return this
}

/**
 * Remove specific amount of [months]
 *
 * @see Calendar.MONTH
 */
fun Calendar.removeMonths(months: Int): Calendar {
    add(
        Calendar.MONTH,
        -months
    )
    return this
}

/**
 * Add specific amount of [days]
 *
 * @see Calendar.DAY_OF_YEAR
 */
fun Calendar.addDays(days: Int): Calendar {
    add(
        Calendar.DAY_OF_YEAR,
        days
    )
    return this
}

/**
 * Remove specific amount of [days]
 *
 * @see Calendar.DAY_OF_YEAR
 */
fun Calendar.removeDays(days: Int): Calendar {
    add(
        Calendar.DAY_OF_YEAR,
        -days
    )
    return this
}

/**
 * Add specific amount of [hours]
 *
 * @see Calendar.HOUR_OF_DAY
 */
fun Calendar.addHours(hours: Int): Calendar {
    add(
        Calendar.HOUR_OF_DAY,
        hours
    )
    return this
}

/**
 * Remove specific amount of [hours]
 *
 * @see Calendar.HOUR_OF_DAY
 */
fun Calendar.removeHours(hours: Int): Calendar {
    add(
        Calendar.HOUR_OF_DAY,
        -hours
    )
    return this
}

/**
 * Add specific amount of [minutes]
 *
 * @see Calendar.MINUTE
 */
fun Calendar.addMinutes(minutes: Int): Calendar {
    add(
        Calendar.MINUTE,
        minutes
    )
    return this
}

/**
 * Remove specific amount of [minutes]
 *
 * @see Calendar.MINUTE
 */
fun Calendar.removeMinutes(minutes: Int): Calendar {
    add(
        Calendar.MINUTE,
        -minutes
    )
    return this
}

/**
 * Add specific amount of [seconds]
 *
 * @see Calendar.SECOND
 */
fun Calendar.addSeconds(seconds: Int): Calendar {
    add(
        Calendar.SECOND,
        seconds
    )
    return this
}

/**
 * Remove specific amount of [seconds]
 *
 * @see Calendar.SECOND
 */
fun Calendar.removeSeconds(seconds: Int): Calendar {
    add(
        Calendar.SECOND,
        -seconds
    )
    return this
}

/**
 * Add specific amount of [milliseconds]
 *
 * @see Calendar.MILLISECOND
 */
fun Calendar.addMilliseconds(milliseconds: Int): Calendar {
    add(
        Calendar.MILLISECOND,
        milliseconds
    )
    return this
}

/**
 * Remove specific amount of [milliseconds]
 *
 * @see Calendar.MILLISECOND
 */
fun Calendar.removeMilliseconds(milliseconds: Int): Calendar {
    add(
        Calendar.MILLISECOND,
        -milliseconds
    )
    return this
}

/**
 * Returns `true` if [this] calendar date is after [other]
 */
fun Calendar.isAfter(other: Calendar) =
    compareTo(other) > 0

/**
 * Returns `true` if [this] calendar date is before [other]
 */
fun Calendar.isBefore(other: Calendar) =
    compareTo(other) < 0

/**
 * Returns `true` if [this] calendar and [other] have same
 * [Calendar.YEAR] and [Calendar.DAY_OF_YEAR]
 */
fun Calendar.isSameDay(other: Calendar) =
    year == other.year && dayOfYear == other.dayOfYear

/**
 * Returns `true` if [this] calendar and [other] have same
 * [Calendar.YEAR] and [Calendar.WEEK_OF_YEAR]
 */
fun Calendar.isSameWeek(other: Calendar) =
    year == other.year && weekOfYear == other.weekOfYear

/**
 * Returns `true` if [this] calendar and [other] have same
 * [Calendar.YEAR] and [Calendar.MONTH]
 */
fun Calendar.isSameMonth(other: Calendar) =
    year == other.year && month == other.month

/**
 * Returns `true` if [this] calendar and [other] have same [Calendar.YEAR]
 */
fun Calendar.isSameYear(other: Calendar) =
    year == other.year

/**
 * Returns years from [this] calendar until [end] one
 */
fun Calendar.yearsUntil(end: Calendar): Int {
    return monthsUntil(end) / 12
}

/**
 * Returns months from [this] calendar until [end] one
 */
fun Calendar.monthsUntil(end: Calendar): Int {
    val startPacked = (year.toLong() * 12L + month.toLong() - 1L) * 32L + dayOfMonth.toLong()
    val endPacked =
        (end.year.toLong() * 12L - end.month.toLong() - 1L) * 32L + end.dayOfMonth.toLong()
    return ((endPacked - startPacked) / 32L).toInt()
}

/**
 * Returns weeks from [this] calendar until [end] one
 */
fun Calendar.weeksUntil(end: Calendar): Int {
    return daysUntil(end) / 7
}

/**
 * Returns days from [this] calendar until [end] one
 */
fun Calendar.daysUntil(end: Calendar): Int {
    return end.dayOfEra - dayOfEra
}

/**
 * Returns a copy of [this] [Calendar]
 */
fun Calendar.copy(): Calendar =
    clone() as Calendar

/**
 * Format [this] calendar date with specific [pattern]
 */
fun Calendar.toString(pattern: String): String =
    SimpleDateFormat(
        pattern,
        Locale.getDefault()
    ).format(Date(timeInMillis))

/**
 * Format [this] calendar date with specific [format]
 */
fun Calendar.toString(format: DateFormat): String =
    format.format(Date(timeInMillis))

/**
 * Returns `true` if [Calendar.DAY_OF_WEEK] id first day of week
 */
val Calendar.isStartOfTheWeek: Boolean
    get() = get(Calendar.DAY_OF_WEEK) == firstDayOfWeek

/**
 * Returns `true` if [Calendar.DAY_OF_WEEK] id last day of week
 */
val Calendar.isEndOfTheWeek: Boolean
    get() {
        val first = firstDayOfWeek
        var last = first + 6
        if (last > 7) {
            last -= 7
        }
        return get(Calendar.DAY_OF_WEEK) == last
    }

/**
 * Returns `true` if calendar date is tomorrow
 */
val Calendar.isTomorrow: Boolean
    get() {
        val calendar = createCalendar()
        calendar.setStartOfTheDay()
        calendar.addDays(1)
        if (compareTo(calendar) <= 0) {
            return false
        }
        calendar.add(
            Calendar.DAY_OF_YEAR,
            1
        )
        return compareTo(calendar) < 0
    }

/**
 * Returns `true` if calendar date is today
 */
val Calendar.isToday: Boolean
    get() {
        val calendar = createCalendar()
        calendar.setStartOfTheDay()
        if (compareTo(calendar) <= 0) {
            return false
        }
        calendar.add(
            Calendar.DAY_OF_YEAR,
            1
        )
        return compareTo(calendar) < 0
    }

/**
 * Returns `true` if calendar date is yesterday
 */
val Calendar.isYesterday: Boolean
    get() {
        val calendar = createCalendar()
        calendar.setStartOfTheDay()
        calendar.removeDays(1)
        if (compareTo(calendar) <= 0) {
            return false
        }
        calendar.add(
            Calendar.DAY_OF_YEAR,
            1
        )
        return compareTo(calendar) < 0
    }

/**
 * Year
 *
 * @see Calendar.YEAR
 */
var Calendar.year: Int
    get() = get(Calendar.YEAR)
    set(value) = set(
        Calendar.YEAR,
        value
    )

/**
 * Week of year
 *
 * @see Calendar.WEEK_OF_YEAR
 */
var Calendar.weekOfYear: Int
    get() = get(Calendar.WEEK_OF_YEAR)
    set(value) = set(
        Calendar.WEEK_OF_YEAR,
        value
    )

/**
 * Month
 *
 * @see Calendar.MONTH
 */
var Calendar.month: Int
    get() = get(Calendar.MONTH)
    set(value) = set(
        Calendar.MONTH,
        value
    )

/**
 * Week of month
 *
 * @see Calendar.WEEK_OF_MONTH
 */
var Calendar.weekOfMonth: Int
    get() = get(Calendar.WEEK_OF_MONTH)
    set(value) = set(
        Calendar.WEEK_OF_MONTH,
        value
    )

/**
 * Day of era (incrementing count of days where day 0 is 0000-01-01)
 */
val Calendar.dayOfEra: Int
    get() {
        val y = year.toLong()
        val m = (month + 1).toLong()
        var total = 0L
        total += 365L * y
        if (y >= 0L) {
            total += (y + 3L) / 4L - (y + 99L) / 100L + (y + 399L) / 400L
        } else {
            total -= y / -4L - y / -100L + y / -400L
        }
        total += (367L * m - 362L) / 12L
        total += dayOfMonth.toLong() - 1L
        if (m > 2) {
            total--
            if (!(y and 3L == 0L && (y % 100L != 0L || y % 400L == 0L))) {
                total--
            }
        }
        return total.toInt()
    }

/**
 * Day of epoch (incrementing count of days where day 0 is 1970-01-01)
 *
 * // java.time.LocalDate.toEpochDay
 */
val Calendar.dayOfEpoch: Int
    get() = dayOfEra - 719528

/**
 * Day of year
 *
 * @see Calendar.DAY_OF_YEAR
 */
var Calendar.dayOfYear: Int
    get() = get(Calendar.DAY_OF_YEAR)
    set(value) = set(
        Calendar.DAY_OF_YEAR,
        value
    )

/**
 * Day of month
 *
 * @see Calendar.DAY_OF_MONTH
 */
var Calendar.dayOfMonth: Int
    get() = get(Calendar.DAY_OF_MONTH)
    set(value) = set(
        Calendar.DAY_OF_MONTH,
        value
    )

/**
 * Day of week
 *
 * @see Calendar.DAY_OF_WEEK
 */
var Calendar.dayOfWeek: Int
    get() = get(Calendar.DAY_OF_WEEK)
    set(value) = set(
        Calendar.DAY_OF_WEEK,
        value
    )

/**
 * Day of week in month
 *
 * @see Calendar.DAY_OF_WEEK_IN_MONTH
 */
var Calendar.dayOfWeekInMonth: Int
    get() = get(Calendar.DAY_OF_WEEK_IN_MONTH)
    set(value) = set(
        Calendar.DAY_OF_WEEK_IN_MONTH,
        value
    )

/**
 * Hour of day (24 hours)
 *
 * @see Calendar.HOUR_OF_DAY
 */
var Calendar.hourOfDay: Int
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) = set(
        Calendar.HOUR_OF_DAY,
        value
    )

/**
 * Minute
 *
 * @see Calendar.MINUTE
 */
var Calendar.minute: Int
    get() = get(Calendar.MINUTE)
    set(value) = set(
        Calendar.MINUTE,
        value
    )

/**
 * Second
 *
 * @see Calendar.SECOND
 */
var Calendar.second: Int
    get() = get(Calendar.SECOND)
    set(value) = set(
        Calendar.SECOND,
        value
    )

/**
 * Millisecond
 *
 * @see Calendar.MILLISECOND
 */
var Calendar.millisecond: Int
    get() = get(Calendar.MILLISECOND)
    set(value) = set(
        Calendar.MILLISECOND,
        value
    )
