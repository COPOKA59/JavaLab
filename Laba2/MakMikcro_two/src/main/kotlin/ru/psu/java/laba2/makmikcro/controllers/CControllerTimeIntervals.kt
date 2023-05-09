package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.web.bind.annotation.*
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CTimeInterval
import ru.psu.java.laba2.makmikcro.services.IServiceTimeIntervals
import java.util.*

/***********************************************************************
 * Контроллер REST запросов к справочнику временного просежутка.       *
 * @author Макарова П.Ф. 12.03.2023.                                   *
 ***********************************************************************/
@RestController
@RequestMapping("/timeintervals") //Относительный путь, по которому должны поступать запросы.
class CControllerTimeIntervals
/*************************************************************************************************************************
* Конструктор.                                                                                                           *
* @param serviceTimeIntervals - сервис с логикой для работы со списком временного промежутка (устанавливается Spring-ом) *
**************************************************************************************************************************/
(
        var serviceTimeIntervals                       : IServiceTimeIntervals
) {
    /*******************************************************
     * Создание/изменение временного промежутка.           *
     * @param timeinterval - данные временного промежутка. *
     *******************************************************/
    @PostMapping
    fun save(@RequestBody timeinterval: CTimeInterval) : String? //создание
    {
        serviceTimeIntervals.save(timeinterval)
        return "Временной промежуток добавлен"
    }

    @GetMapping(params = ["id"])//поиск по id
    fun getAllByTimeIntervalId(@RequestParam id: UUID): List<CTimeInterval>
    {
        return serviceTimeIntervals.getAllByTimeIntervalId(id)
    }
    @GetMapping
    fun getAll()                              : List<CTimeInterval>
    {
        return serviceTimeIntervals.getAllTimeIntervals()
    }

}