package com.example.demo.util.convertes

import javafx.util.StringConverter
import java.time.LocalDate
import java.time.Year
/**********************************************************************
 * Контроллер вывода даты.                                            *
 **********************************************************************/
object  CConverterDate                      : StringConverter<LocalDate>() {
    override fun toString(
        id                                  : LocalDate?
    )                                       = id?.toString() ?: ""
    override fun fromString(
        string                              : String?
    )                                       : LocalDate?
    {
        if (string == null) return null
        return try {
            LocalDate.parse(string)
        }
        catch (e                            : Exception)
        {
            null
        }
    }
}
