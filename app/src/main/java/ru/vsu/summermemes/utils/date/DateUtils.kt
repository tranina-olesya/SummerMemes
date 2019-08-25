package ru.vsu.summermemes.utils.date

import java.util.Date

object DateUtils {
    fun getCurrentSeconds(): Long {
        return Date().time / 1000L
    }
}