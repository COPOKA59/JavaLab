package ru.psu.java.laba2.makmikcro.services

import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CFood
import java.util.UUID
/**********************************************************************
 * Интерфейс сервиса с бизнес-логикой для работы со списком дат.      *
 * @author Макарова П.Ф. 12.03.2023.                                  *
 **********************************************************************/
interface IServiceDates {
    fun save(date : CDate)

    fun getAllByDateId(id: UUID): List<CDate>

    fun getAllDates(): List<CDate>
}