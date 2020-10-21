package de.carldressler.iq.utils

import de.carldressler.iq.IQPlugin

fun getTime(plugin: IQPlugin, time: String): String {
    val timeFormat = plugin.config.getString("time-format")

    if (timeFormat == "12h") {
        return when (time) {
            "0000" -> "12pm"
            "0500" -> "5am"
            "0600" -> "6am"
            "0700" -> "7am"
            "1200" -> "12am"
            "1500" -> "3pm"
            "1800" -> "6pm"
            "1900" -> "7pm"
            else -> "Missing value in utils.getTime function"
        }
    } else {
        return when (time) {
            "0000" -> "0:00"
            "0500" -> "5:00"
            "0600" -> "6:00"
            "0700" -> "7:00"
            "1200" -> "12:00"
            "1500" -> "15:00"
            "1800" -> "18:00"
            "1900" -> "19:00"
            else -> "Missing value in utils.getTime function"
        }
    }
}