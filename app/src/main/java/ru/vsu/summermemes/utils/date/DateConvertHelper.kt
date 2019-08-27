package ru.vsu.summermemes.utils.date

import ru.vsu.summermemes.R
import ru.vsu.summermemes.SummerMemesApp
import java.util.*

object DateConvertHelper {
    fun getDaysFromNow(dateRaw: Long): Int {
        val now = Date().time / 1000L
        val days = (now - dateRaw) / (60L * 60 * 24)
        return days.toInt()
    }

    fun getDaysAgoCreated(daysCreated: Long): String {
        val context = SummerMemesApp.appInstance
        val days = getDaysFromNow(daysCreated)
        return context.resources.getQuantityString(R.plurals.days, days, days)
    }
}
