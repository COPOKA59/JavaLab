package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.web.bind.annotation.*
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.services.IServiceDates
import java.time.LocalDate
import java.util.*

/*****************************************************
 * Контроллер REST запросов к справочнику дат.       *
 * @author Макарова П.Ф. 12.03.2023.                 *
 *****************************************************/
@RestController
@RequestMapping("/dates") //Относительный путь, по которому должны поступать запросы.
class CControllerDates
/******************************************************************************************************
* Конструктор.                                                                                        *
* @param serviceDates - сервис с логикой для работы со списком дат (устанавливается Spring-ом)        *
*******************************************************************************************************/
(
    var serviceDates                        : IServiceDates
) {
    /*****************************************************
     * Создание/изменение дат.                           *
     * @param date - данные дат.                         *
     *****************************************************/
    @PostMapping
    fun save(@RequestBody date: CDate): String? //создание
    {
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
        serviceDates.save(date)
        return "Дата добавлена"
    }

    @GetMapping(params = ["id"])//поиск по id
    fun getAllByDateId(@RequestParam id: UUID): List<CDate>
    {
        return serviceDates.getAllByDateId(id)
    }
    @GetMapping
    fun getAll()                              : List<CDate>
    {
        return serviceDates.getAllDates()
    }
}