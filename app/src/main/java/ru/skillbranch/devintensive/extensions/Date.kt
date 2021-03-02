package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.Date
import kotlin.math.abs

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

fun Date.humanizeDiff(date: Date = Date()): String {
    val dif = abs(this.time -  date.time)
    val isPast = this.time < date.time

    return when {
        dif <= SECOND -> "только что"
        dif <= SECOND * 45 -> getTenseForm("несколько секунд", isPast)
        dif <= SECOND * 75 -> getTenseForm("минуту", isPast)
        dif <= MINUTE * 45 -> getTenseForm(TimeUnits.MINUTE.plural((dif / MINUTE).toInt()), isPast)
        dif <= MINUTE * 75 -> getTenseForm("час", isPast)
        dif <= HOUR * 22 -> getTenseForm(TimeUnits.HOUR.plural((dif / HOUR).toInt()), isPast)
        dif <= HOUR * 26 -> getTenseForm("день", isPast)
        dif <= DAY * 360 -> getTenseForm(TimeUnits.DAY.plural((dif / DAY).toInt()), isPast)
        else -> if(isPast) "более года назад" else "более чем через год"
    }
}

fun getTenseForm(interval: String, isPast: Boolean): String {
    val prefix = if (isPast) "" else "через"
    val postfix = if (isPast) "назад" else ""
    return "$prefix $interval $postfix".trim()
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
