package view.shared

import getPlatformName
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime

class HelperFunctions {
    companion object {
        fun formatLongDate(time: Long): String {
            val date = Instant.fromEpochMilliseconds(time)
                .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
            return formatDate(date)
        }

        fun formatDate(date: LocalDate): String{
            return "" + date.dayOfMonth + "." + date.monthNumber + "." + date.year;
        }

        fun isWindowsPlatform(): Boolean {
            return getPlatformName().lowercase().contains("win")
        }
    }
}