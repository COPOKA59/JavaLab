package ru.psu.java.laba2.makmikcro.model

import com.fasterxml.jackson.annotation.JsonIncludeProperties
import java.util.*
import javax.persistence.*

/***********************************
 * Класс блюдо(еда)                *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/

@Entity
@Table(name = "foods")
class CFood() {
    /**************************
     * Идендификатор
     **************************/
    @Id
    var id: UUID? = null

    /**************************
     * Название
     **************************/
    @Column
    var name: String? = null

    /**************************
     * Каллории
     **************************/
    @Column
    var kal : Double = 0.0 //Double = 0.0

    /**************************
     * Дата
     **************************/
    @ManyToOne
    @JoinColumn(name="date_id", nullable = false)
    @JsonIncludeProperties(value = ["id"])
    var date: CDate? = null

    /**************************
     * Временной промежуток
     **************************/

    @ManyToOne
    @JoinColumn(name="timeinterval_id", nullable = false)
    @JsonIncludeProperties(value = ["id"])
    var timeinterval: CTimeInterval? = null

    /**************************
     * Вывод
     **************************/
    override fun toString(): String {
        return "Дата " + (if (date == null) "не указана" else date) + " Блюдо -" + name
    }
}