package ru.vsu.summermemes.utils

import ru.vsu.summermemes.R
import ru.vsu.summermemes.SummerMemesApp
import java.util.*

object DateConvertHelper {
    fun getDaysFromNow(dateRaw: Long): Int {
        val now = Date().time / 1000L
        val days = (now - dateRaw) / (60L * 60 * 24)
        return days.toInt()
    }

    fun getDaysForm(days: Int): String {
        val context = SummerMemesApp.provideContext()

        val form1 = context.getString(R.string.day_form_1)
        val form2 = context.getString(R.string.day_form_2)
        val form3 = context.getString(R.string.day_form_3)

        val twoLastDays = days % 100
        val lastDay = twoLastDays % 10

        if (twoLastDays in 11..19) {
            return form3
        }

        return when (lastDay) {
            1 -> form1
            in 2..4 -> form2
            else -> form3
        }
    }

    fun getDaysAgoCreated(daysCreated: Long): String {
        val days = getDaysFromNow(daysCreated)
        val daysForm = getDaysForm(days)

        return when (days) {
            0 -> SummerMemesApp.provideContext().getString(R.string.today)
            else -> "%d %s %s".format(days, daysForm, SummerMemesApp.provideContext().getString(R.string.days_ago))
        }
    }
}
