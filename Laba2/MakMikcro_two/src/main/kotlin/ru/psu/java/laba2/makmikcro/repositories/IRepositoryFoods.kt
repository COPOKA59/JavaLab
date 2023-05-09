package ru.psu.java.laba2.makmikcro.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.model.IDateWithCounter
import java.time.LocalDate
import java.util.*

/******************************************************************
 * Интерфейс с заголовками методов для доступа к данным в СУБД.   *
 * @author Макарова П.Ф. 12.03.2023                               *
 ******************************************************************/
@Repository
interface IRepositoryFoods : CrudRepository<CFood, UUID>
{

    //Типовые методы наследуются из интерфйса CrudRepository
    /**********************************************************************************
     * Список еды с названием name (через запрос на обычном SQL).                         *
     * @param name - парметр для фильтрации еды.                                      *
     * @return список отфильтрованной еды.                                            *
     **********************************************************************************/
    @Query("SELECT * FROM foods f WHERE f.name = ?1", nativeQuery= true)
    fun findByNameNative(
            name                                : String
    )                                           : List<CFood>

    /**********************************************************************************
     * Список еды с калориями kal (через запрос на обычном SQL).                         *
     * @param kal - парметр для фильтрации еды.                                      *
     * @return список отфильтрованной еды.                                            *
     **********************************************************************************/
    @Query("SELECT * FROM foods f WHERE f.kal = ?1", nativeQuery= true)
    fun findByKalNative(
            kal                                 : Double //Double
    )                                           : List<CFood>

    /**********************************************************************************
     * Список еды с названием name (через запрос JPQL).                                   *
     * @param name - парметр для фильтрации еды.                                      *
     * @return список отфильтрованной еды.                                            *
     **********************************************************************************/
    @Query("SELECT f FROM CFood f WHERE f.name = ?1")
    fun findByFoodCustom(
        name                                : String
    )                                           : List<CFood>
    /******************************************************************
     * Список дат с максимальным количеством еды меньше 400 каллорий. *
     * @return список отфильтрованных дат.                            *
     ******************************************************************/
    @Query("""
        select cast(t.id as varchar), t.date, t.count from (
            select s.id, s.date, count(*) as count
            from dates s
            left join foods m on s.id=m.date_id
            where m.kal<400
            group by s.id, s.date
        ) as t
        where t.count in
        (
            select max(t1.cnt) from (
                select s.id, count(*) as cnt
                from dates s
                left join foods m on s.id=m.date_id
                where m.kal<400
                group by s.id
            ) as t1)
        """,
        nativeQuery                         = true
    )
    fun getDatesWithMaxFoods()            : List<IDateWithCounter>
}