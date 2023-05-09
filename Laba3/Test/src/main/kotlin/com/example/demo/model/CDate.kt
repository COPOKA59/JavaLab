package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
import javax.json.JsonObject
/***********************************
 * Класс дата                      *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/
class CDate(
    id: UUID?= null,
    date: LocalDate= LocalDate.ofEpochDay(1000),
    day: String= ""
): JsonModel {
    val propertyId                          = SimpleObjectProperty(id)
    var id by propertyId

    val propertyDate                  = SimpleObjectProperty(date)
    var date by propertyDate

    val propertyDay                        = SimpleStringProperty(day)
    var day by propertyDay

    /****************************************************************************************************
     * Обновление полей текущего класса по данным из JSON.                                              *
     ****************************************************************************************************/
    override fun updateModel(json: JsonObject)
    {
        with(json) {
            id                              = UUID.fromString(string("id"))
            if(string("date") != null)
                date                        = LocalDate.parse(string("date"))
            day                            = string("day")
        }
    }
    /****************************************************************************************************
     * Создание JSON по данным из полей текущего класса.                                                *
     ****************************************************************************************************/
    override fun toJSON(json: JsonBuilder)
    {
        with(json) {
            add("id", id)
            add("date", date?.toString())
            add("day", day)
        }
    }
    /****************************************************************************************************
     * Проверка на соответствие двух объектов друг другу по идентификтаорам.                            *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает туже сущность.                                               *
     ****************************************************************************************************/
    override fun equals(other: Any?)=(other is CDate) && id == other.id

    /****************************************************************************************************
     * Проверка наличия изменений в объекте по сравнению с другим объектом.                             *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает ту же сущность, но имеет отличия в полях.                    *
     ****************************************************************************************************/
    fun hasChanges(other: CDate)=equals(other) && //Совсем другой объект не считаем изменённым текущим.
                ( //Изменения в любом другом поле считаем изменениями в объекте.
                        date != other.date ||
                                day != other.day
                        )

    override fun toString(): String {
        return "Выберите значение"
    }
}
