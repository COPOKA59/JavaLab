package ru.psu.java.laba2.makmikcro.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.model.IDateWithCounter
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryFoods
import java.time.LocalDate
import java.util.*

/******************************************************************************
 * Сервис с бизнес-логикой для работы со списком еды.                         *
 * @author Макарова П.Ф. 12.03.2023                                           *
 ******************************************************************************/

@Service
class CServiceFoods
    /***************************************************************************
     * Конструктор.                                                            *
     * @param repositoryFoods - репозиторий с запросами к таблице еды в СУБД   *
     * (устанавливается Spring-ом).                                            *
     ***************************************************************************/
    (
    val repositoryFoods                         : IRepositoryFoods
    )                                           : IServiceFoods
    {
        /************************************************************
         * Список всей еды.                                         *
         ************************************************************/
        override fun getAll()                   :Iterable<CFood>
        {
            return repositoryFoods.findAll()
        }
        /*************************************************************
         * Поиск еды по идентификатору.                              *
         * @param id - идентификатор еды для поиска в формате UUID.  *
         *************************************************************/
        override fun getById(id: UUID)           : ResponseEntity<CFood>
        {
            return repositoryFoods.findById(id)
                .map { food -> ResponseEntity.ok(food) }
                .orElse(ResponseEntity.notFound().build())
        }
        /*************************************************************
         * Поиск еды по названию.                                    *
         * @param name - название еды для поиска в формате String.   *
         *************************************************************/
        override fun getByName(name: String) : List<CFood>
        {
            return repositoryFoods.findByNameNative(name)
        }
        /*************************************************************
         * Поиск еды по калориям.                                    *
         * @param kal - калории еды для поиска в формате Double.     *
         *************************************************************/
        override fun getByKal(kal: Double) : List<CFood> //Double
        {
            return repositoryFoods.findByKalNative(kal)
        }

        /****************************************************************************************************
         * Поиск дней с определённой едой(Бананы).                                                 *
         ***************************************************************************************************/
        override fun getByDayN() : List<CFood>
        {
            return repositoryFoods.findByNameNative("Бананы")
        }

        override fun getWithMaxFoods() : List<IDateWithCounter>
        {
            return repositoryFoods.getDatesWithMaxFoods()
        }

        /***************************************************************************************************
         * Создание/изменение еды.                                                                        *
         * @param food - данные еды.                                                                      *
         ***************************************************************************************************/
        override fun save(food: CFood)
        {
            repositoryFoods.save(food)
        }
        /***************************************************************************************************
         * Удаление еды.                                                                                  *
         * @param food - данные еды.                                                                      *
         ***************************************************************************************************/
        override fun delete(food: CFood)
        {
            repositoryFoods.delete(food)
        }
        /***************************************************************************************************
         * Удаление еды по идентификатору.                                                                *
         * @param id - идентификатор еды для удаления.                                                    *
         ***************************************************************************************************/
        override fun deleteById(id: UUID): String
        {
            return if (repositoryFoods.existsById(id)) {
                repositoryFoods.deleteById(id)
                "Элемент удалён"
            }
            else
                "Элемент не найден"
        }
}