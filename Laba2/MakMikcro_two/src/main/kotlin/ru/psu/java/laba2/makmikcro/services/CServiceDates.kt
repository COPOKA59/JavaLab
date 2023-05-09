package ru.psu.java.laba2.makmikcro.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryDates
import java.time.LocalDate
import java.util.*

/*******************************************************
 * Сервис с бизнес-логикой для работы со списком дат.  *
 * @author Макарова П.Ф. 12.03.2023                    *
 *******************************************************/
@Service
class CServiceDates : IServiceDates {
    @Autowired
    lateinit var repositoryDates: IRepositoryDates

    /************************************************************************************************
     * Создание даты.                                                                                 *
     ***************************************************************************************************/
    override fun save(date: CDate) {
        try {
            println(date.date)
            println(date.day)
            println(date.date!!.dayOfWeek.value)
            if (date.date!!.dayOfWeek.value == 1) {
                date.day = "Пн"
            } else { if (date.date!!.dayOfWeek.value == 2) {
                date.day = "Вт"
            } else { if (date.date!!.dayOfWeek.value == 3) {
                date.day = "Ср"
            } else { if (date.date!!.dayOfWeek.value == 4) {
                date.day = "Чт"
            } else { if (date.date!!.dayOfWeek.value == 5) {
                date.day = "Пт"
            } else { if (date.date!!.dayOfWeek.value == 6) {
                date.day = "Сб"
            } else { if (date.date!!.dayOfWeek.value == 7) {
                date.day = "Вс"
            } } } } } } }
            println(date.day)
            repositoryDates.save(date)
        }
        catch (e : Exception)
        {
            throw Exception("Не указаны обязательные данные для даты!", e)
        }
    }

    /****************************************************************************************************
     * Поиск даты по идентификатору.                                                                *
     * @param id - идентификатор даты для поиска в формате UUID.                                    *
     ***************************************************************************************************/
    override fun getAllByDateId(id: UUID): List<CDate>
    {
        return repositoryDates.findAllById(id)
    }

    override fun getAllDates(): List<CDate> {
        val list = ArrayList<CDate>()
        list.addAll(repositoryDates.findAll())
        return list
    }
}
