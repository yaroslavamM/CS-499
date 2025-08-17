package edu.snhu.yaroslavameleshkevich.eventsapp.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @JvmStatic
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return if (value != null) {
            LocalDate.parse(value)
        } else {
            null
        }
    }

    @JvmStatic
    @TypeConverter
    fun toString(value: LocalDate?): String? {
        return value?.toString()
    }

}
