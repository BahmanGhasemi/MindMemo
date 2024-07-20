package ir.bahmanghasemi.mindmemo.feature_note.presentation.util

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    fun toUtc(): Long {
        return ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toEpochSecond() * 1000
    }

    fun toLocalDate(timeInMillis: Long): String {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val localDateTime =
            ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("EEE dd-MM-yyyy")
        return localDateTime.format(formatter)
    }
}