package ru.psu.java.laba2.makmikcro.model

import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/***********************************
 * Класс дата                      *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/

@Entity
@Table(name = "dates")
class CDate {
    /**************************
     * Идендификатор
     **************************/
    @Id
    var id: UUID? = null

    /**************************
     * Дата записи
     **************************/
    @Column
    var date: LocalDate? = null

    /*******************
     * День недели
     *******************/
    @Column
    var day: String? = null
}