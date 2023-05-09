package com.example.demo.util.convertes

import javafx.util.StringConverter
import java.util.*
/**********************************************************************
 * Контроллер вывода id.                                            *
 **********************************************************************/
object CConverterUUID                       : StringConverter<UUID>() {
    override fun toString(
        id                                  : UUID?
    )                                       = id?.toString() ?: ""
    override fun fromString(
        string                              : String?
    )                                       = if (string == null) null else UUID.fromString(string)
}
