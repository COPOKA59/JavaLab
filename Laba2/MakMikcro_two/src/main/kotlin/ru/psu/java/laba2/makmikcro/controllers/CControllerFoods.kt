package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.model.IDateWithCounter
import ru.psu.java.laba2.makmikcro.services.IServiceFoods
import java.util.*

/*****************************************************
 * Контроллер REST запросов к справочнику еды.       *
 * @author Макарова П.Ф. 12.03.2023.                 *
 *****************************************************/
@RestController
@RequestMapping("/foods") //Относительный путь, по которому должны поступать запросы.
class CControllerFoods
/********************************************************************************************************
 * Конструктор.                                                                                         *
 * @param serviceFoods - сервис с логикой для работы со списком еды (устанавливается Spring-ом)*
 *******************************************************************************************************/
(
    val serviceFoods                     : IServiceFoods
)
{
    /****************************************************************************************************
     * Список всей еды.                                                                           *
     ***************************************************************************************************/
    @GetMapping
    fun getAll()                            : Iterable<CFood>
    {
        return serviceFoods.getAll()
    }
    /****************************************************************************************************
     * Поиск еды по идентификатору.                                                                *
     * @param id - идентификатор еды для поиска в формате UUID.                                    *
     ***************************************************************************************************/
    @GetMapping(
        //Этой строкой говорим, что метод вызывать только тогда,
        //когда в параметрах запроса есть параметр id.
        params                              = ["id"]
    )
    fun getById(
        //Значение перменной id заполняется содержимым из параметра запроса id
        @RequestParam id: UUID)               : ResponseEntity<CFood>
    {
        return serviceFoods.getById(id)
    }
    /*************************************************************
     * Поиск еды по названию.                                    *
     * @param name - название еды для поиска в формате String.   *
     *************************************************************/
    @GetMapping(params = ["name"])
    fun getByName(@RequestParam name: String) : Iterable<CFood>
    {
        return serviceFoods.getByName(name)
    }
    /*************************************************************
     * Поиск еды по калориям.                                    *
     * @param kal - калории еды для поиска в формате Double.     *
     *************************************************************/
    @GetMapping(params = ["kal"])
    fun getByKal(@RequestParam kal: Double) : Iterable<CFood> //Double
    {
        return serviceFoods.getByKal(kal)
    }
    /*****************************************************
     * Создание/изменение еды.                          *
     * @param food - данные еды.                        *
     *****************************************************/
    @PostMapping
    fun save(
        //Данные даты должны быть в теле запроса.
        @RequestBody food: CFood) : String?
    {
        serviceFoods.save(food)
        return "Еда добавлена"
    }
    /*****************************************************
     * Удаление еды.                                    *
     * @param food - данные еды.                        *
     *****************************************************/
    @DeleteMapping
    fun delete(
        //Данные даты должны быть в теле запроса.
        @RequestBody food: CFood) : String?
    {
        serviceFoods.delete(food)
        return "Еда удалена"
    }
    /*****************************************************
     * Удаление еды по идентификатору.                  *
     * @param id - идентификатор еды для удаления.      *
     *****************************************************/
    @DeleteMapping(params= ["id"])
    fun deleteById(@RequestParam id: UUID): String?
    {
        return serviceFoods.deleteById(id)
    }
    //Список дней с максимальным количеством еды (меньше 400 каллорий) и количество этих еды.
    @GetMapping(path = ["/dates/kal"])
    fun getWithMaxProblems() : List<IDateWithCounter>
    {
        return serviceFoods.getWithMaxFoods()
    }
    // получить списки дат по названию("Бананы")
    @GetMapping(path = ["/DayN"])
    fun getByDayN() : Iterable<CFood>
    {
        return serviceFoods.getByDayN()
    }
}