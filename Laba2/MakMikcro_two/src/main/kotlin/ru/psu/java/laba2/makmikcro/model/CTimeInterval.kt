package ru.psu.java.laba2.makmikcro.model

import com.fasterxml.jackson.annotation.JsonIncludeProperties
import java.time.LocalTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Id

/***********************************
 * Класс временной интервал        *
 * @autor Макарова П.Ф. 12.03.2023 *
 ***********************************/

@Entity
@Table(name = "timeintervals")
class CTimeInterval {
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
     * Временной промежуток
     **************************/
    //@Column
    //var vr: String? = null

    /**************************
     * Временной промежуток Начало
     **************************/
    @Column
    var vrN: LocalTime? = null

    /**************************
     * Временной промежуток Конец
     **************************/
    @Column
    var vrK: LocalTime? = null

}