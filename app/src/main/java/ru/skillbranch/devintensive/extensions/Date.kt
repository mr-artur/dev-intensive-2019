package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String = when (val diff = this.time - date.time) {
    in 0..1 * SECOND -> "только что"
    in 1 * SECOND..45 * SECOND -> "несколько секунд назад"
    in 45 * SECOND..75 * SECOND -> "минуту назад"
    in 75 * SECOND..45 * MINUTE -> "${diff / MINUTE} минут назад"
    in 45 * MINUTE..75 * MINUTE -> "час назад"
    in 75 * MINUTE..22 * HOUR -> "${diff / HOUR} часов назад"
    in 22 * HOUR..26 * HOUR -> "день назад"
    in 26 * HOUR..360 * DAY -> "${diff / DAY} дней назад"
    else -> "более года назад"
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String = "$value ".plus(when (value % 10) {
            1 -> "секунду"
            in 2..4 -> "секунды"
            else -> "секунд"
        })
    },
    MINUTE {
        override fun plural(value: Int): String = "$value ".plus(when (value % 10) {
            1 -> "минуту"
            in 2..4 -> "минуты"
            else -> "минут"
        })
    },
    HOUR {
        override fun plural(value: Int): String = "$value ".plus(when (value % 10) {
            1 -> "час"
            in 2..4 -> "часа"
            else -> "часов"
        })
    },
    DAY {
        override fun plural(value: Int): String = "$value ".plus(when (value % 10) {
            1 -> "день"
            in 2..4 -> "дня"
            else -> "дней"
        })
    };

    abstract fun plural(value: Int): String
}
