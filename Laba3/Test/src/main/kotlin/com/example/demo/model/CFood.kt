package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*
import javax.json.JsonObject

/***********************************
 * Класс блюдо(еда)                *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/
class CFood(
    id: UUID? = null,
    name: String = "",
    kal: Double = 0.0,
    date: CDate? = null,
    timeinterval: CTimeInterval? = null
) : JsonModel {
    val propertyId = SimpleObjectProperty(id)
    var id by propertyId

    val propertyName = SimpleStringProperty(name)
    var name by propertyName
    val propertyKal = SimpleDoubleProperty(kal)
    var kal by propertyKal
    val propertyDate = SimpleObjectProperty(date)
    var date by propertyDate
    val propertyTimeInterval = SimpleObjectProperty(timeinterval)
    var timeinterval by propertyTimeInterval


    /****************************************************************************************************
     * Обновление полей текущего класса по данным из JSON.                                              *
     ***************************************************************************************************/
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = UUID.fromString(string("id"))
            name = string("name")

            kal = double("kal")!! //string("kal")

            var tmp_date: JsonObject? = jsonObject("date")
            date = CDate(UUID.fromString( tmp_date?.getString("id")) )
            var tmp_timeInterval: JsonObject? = jsonObject("timeinterval")
            timeinterval = CTimeInterval(UUID.fromString( tmp_timeInterval?.getString("id")) )
        }
    }

    /****************************************************************************************************
     * Создание JSON по данным из полей текущего класса.                                                *
     ****************************************************************************************************/
    override fun toJSON(
        json: JsonBuilder
    ) {
        with(json) {
            add("id", id)
            add("name", name)
            add("kal", kal)
            add("date", date?.id)
            add("timeinterval", timeinterval?.id)
        }
    }

    /****************************************************************************************************
     * Проверка на соответствие двух объектов друг другу по идентификтаорам.                            *
     * @param other - другой объект для проверки.                                                       *
     * @return true, если объект описывает туже сущность.                                               *
     ****************************************************************************************************/
    override fun equals(other: Any?) = (other is CFood) && id == other.id

    /***************************************************************************************************
     * Проверка наличия изменений в объекте по сравнению с другим объектом.                            *
     * @param other - другой объект для проверки.                                                      *
     * @return true, если объект описывает ту же сущность, но имеет отличия в полях.                   *
     ***************************************************************************************************/
    fun hasChanges(other: CFood) = equals(other) && //Совсем другой объект не считаем изменённым текущим.
            ( //Изменения в любом другом поле считаем изменениями в объекте.
                    name != other.name || kal != other.kal ||
                            date?.id != other.date?.id || timeinterval?.id != other.timeinterval?.id

                    )

    override fun toString(): String {
        return "food(id=$id, name='$name', kal='$kal', date=$date, timeinterval=$timeinterval)"
    }
}