package com.example.demo.util.convertes

import javafx.util.StringConverter
import java.time.LocalTime
/**********************************************************************
 * Контроллер вывода времени.                                            *
 **********************************************************************/
object  CConverterTime                      : StringConverter<LocalTime>() {
    override fun toString(
        id                                  : LocalTime?
    )                                       = id?.toString() ?: ""
    override fun fromString(
        string                              : String?
    )                                       : LocalTime?
    {
        if (string == null) return null
        return try {
            LocalTime.parse(string)
        }
        catch (e                            : Exception)
        {
            null
        }
    }
}
