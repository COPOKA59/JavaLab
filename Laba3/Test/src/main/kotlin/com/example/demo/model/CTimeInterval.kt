package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.util.*
import javax.json.JsonObject
import java.time.LocalTime

/***********************************
 * Класс временной интервал        *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/
class CTimeInterval(
    id: UUID?= null,
    name: String= "",
    vrN: LocalTime = LocalTime.MIN,
    vrK: LocalTime = LocalTime.MIN
): JsonModel {
    val propertyId                          = SimpleObjectProperty(id)
    var id by propertyId

    val propertyName                        = SimpleStringProperty(name)
    var name by propertyName

    val propertyVrN                         = SimpleObjectProperty(vrN)
    var vrN by propertyVrN

    val propertyVrK                         = SimpleObjectProperty(vrK)
    var vrK by propertyVrK


    /****************************************************************************************************
     * Обновление полей текущего класса по данным из JSON.                                              *
     ****************************************************************************************************/
    override fun updateModel(json:JsonObject)
    {
        with(json) {
            id                              = UUID.fromString(string("id"))
            name                            = string("name")
            vrN                             = LocalTime.parse(string("vrN"))
            vrK                             = LocalTime.parse(string("vrK"))
        }
    }
    /****************************************************************************************************
     * Создание JSON по данным из полей текущего класса.                                                *
     ****************************************************************************************************/
    override fun toJSON(json: JsonBuilder)
    {
        with(json) {
            add("id", id)
            add("name", name)
            add("vrN", vrN.toString())
            add("vrK", vrK.toString())
        }
    }
    /****************************************************************************************************
     * Проверка на соответствие двух объектов друг другу по идентификтаорам.                            *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает туже сущность.                                               *
     ****************************************************************************************************/
    override fun equals(other:Any?)=(other is CTimeInterval) && id == other.id

    /****************************************************************************************************
     * Проверка наличия изменений в объекте по сравнению с другим объектом.                             *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает ту же сущность, но имеет отличия в полях.                    *
     ****************************************************************************************************/
    fun hasChanges(other:CTimeInterval)=equals(other) && //Совсем другой объект не считаем изменённым текущим.
                ( //Изменения в любом другом поле считаем изменениями в объекте.
                        name != other.name ||
                                vrN != other.vrN||vrK != other.vrK)

    override fun toString(): String {
        return "Выберите значение"
    }
}
