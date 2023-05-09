package com.example.demo.viewmodels

import com.example.demo.model.CDate
import com.example.demo.services.CServiceDates
import tornadofx.*

/******************************************************************************************
 * Модель представления для одной даты.                                                   *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 ******************************************************************************************/
class CViewModelDates(date:CDate): ItemViewModel<CDate>(date)
{
    private val serviceDates             : CServiceDates by inject()

    val id                                  = bind(CDate::propertyId)
    val date                                = bind(CDate::propertyDate)
    val day                                = bind(CDate::propertyDay)

    /******************************************************************************************
     * Сохранение данных из полей формы в объект в оперативной памяти.                        *
     *****************************************************************************************/
    fun save()
    {
        this.commit() //Сохранение данных из полей на форме в сущность
        //Передача данных на сервер (изменили на кэш только)
        serviceDates.save(this.item)
    }
}
