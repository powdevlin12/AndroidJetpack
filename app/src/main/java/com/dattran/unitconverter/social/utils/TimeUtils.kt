package com.dattran.unitconverter.social.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun String.toTimeAgo(): String {
    // Fallback: just return the raw string if parsing fails
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US)
        sdf.timeZone = java.util.TimeZone.getTimeZone("UTC")
        val date = sdf.parse(this) ?: return this
        val diff = System.currentTimeMillis() - date.time
        val minutes = diff / 60_000
        val hours = minutes / 60
        val days = hours / 24
        when {
            minutes < 1 -> "just now"
            minutes < 60 -> "${minutes}m"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            else -> "${days / 7}w"
        }
    } catch (e: Exception) {
        this
    }
}