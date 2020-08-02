package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun numToWords(num: Long, words: List<String>): String {
    var tmp = num
    tmp %= 100
    if (num > 19) {
        tmp %= 10
    }
    return num.toString() + " " + when (tmp) {
        1L -> words[0]
        in 2L..4L -> words[1]
        else -> words[2]
    }
}

enum class TimeUnits(val millis: Long, private val id: Int) {
    SECOND(1000L, 0),
    MINUTE(SECOND.millis * 60, 1),
    HOUR(MINUTE.millis * 60, 2),
    DAY(HOUR.millis * 24, 3);

    fun plural(value: Long): String = when (id) {
        0 -> numToWords(value, listOf("секунду", "секунды", "секунд"))
        1 -> numToWords(value, listOf("минуту", "минуты", "минут"))
        2 -> numToWords(value, listOf("час", "часа", "часов"))
        else -> numToWords(value, listOf("день", "дня", "дней"))
    }
}

fun toSeconds(millis: Long): Long = millis / TimeUnits.SECOND.millis
fun toMinutes(millis: Long): Long = millis / TimeUnits.MINUTE.millis
fun toHours(millis: Long): Long = millis / TimeUnits.HOUR.millis
fun toDays(millis: Long): Long = millis / TimeUnits.DAY.millis


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String =
    SimpleDateFormat(pattern, Locale("ru")).format(this)

fun Date.add(value: Int, unit: TimeUnits): Date {
    this.time += unit.millis * value
    return this
}

fun Date.isSameDay(date: Date): Boolean{
    return this.time / TimeUnits.DAY.millis == date.time / TimeUnits.DAY.millis
}

fun Date.shortFormat(): String? {
    val pattern = if(this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    return this.format(pattern)
}


fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = abs(this.time - date.time)
    val past = this.before(date)
    return when {
        toSeconds(diff) <= 1 -> "только что"
        toSeconds(diff) <= 45 -> {
            (if (past) "" else "через ") +
                    "несколько секунд" +
                    if (past) " назад" else ""
        }
        toSeconds(diff) <= 75 -> {
            (if (past) "" else "через ") +
                    "минуту" +
                    if (past) " назад" else ""

        }
        toMinutes(diff) <= 45 -> {
            (if (past) "" else "через ") +
                    TimeUnits.MINUTE.plural(toMinutes(diff)) +
                    if (past) " назад" else ""
        }
        toMinutes(diff) <= 75 -> {
            (if (past) "" else "через ") +
                    "час" +
                    if (past) " назад" else ""
        }
        toHours(diff) <= 22 -> {
            (if (past) "" else "через ") +
                    TimeUnits.HOUR.plural(toHours(diff)) +
                    if (past) " назад" else ""
        }
        toHours(diff) <= 26 -> {
            (if (past) "" else "через ") +
                    "день" +
                    if (past) " назад" else ""
        }
        toDays(diff) <= 360 -> {
            (if (past) "" else "через ") +
                    TimeUnits.DAY.plural(toDays(diff)) +
                    if (past) " назад" else ""
        }
        else ->{
            if(past){
                "более года назад"
            } else{
                "более чем через год"
            }
        }
    }
}
