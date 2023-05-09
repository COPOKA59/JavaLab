package com.example.demo.viewmodels

import com.example.demo.model.CTimeInterval
import com.example.demo.services.CServiceTimeIntervals
import tornadofx.*

/******************************************************************************************
 * Модель представления для одного самолёта.                                              *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *****************************************************************************************/
class CViewModelTimeIntervals(timeInterval:CTimeInterval):ItemViewModel<CTimeInterval>(timeInterval)
{
    private val serviceTimeIntervals             : CServiceTimeIntervals by inject()

    val id                                  = bind(CTimeInterval::propertyId)
    val name                                = bind(CTimeInterval::propertyName)
    val VrN                                 = bind(CTimeInterval::propertyVrN)
    val VrK                                 = bind(CTimeInterval::propertyVrK)

    /******************************************************************************************
     * Сохранение данных из полей формы в объект в оперативной памяти.                        *
     *****************************************************************************************/
    fun save()
    {
        this.commit() //Сохранение данных из полей на форме в сущность
        //Передача данных на сервер (изменили на кэш только)
        serviceTimeIntervals.save(this.item)
    }
}
