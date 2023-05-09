package com.example.demo.viewmodels


import com.example.demo.model.CFood
import com.example.demo.services.CServiceFoods
import tornadofx.ItemViewModel

/************************************************************************************************
 * Модель представления для одной еды.                                                       *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 ***********************************************************************************************/
class CViewModelFoods(food:CFood) : ItemViewModel<CFood>(food)
{
    private val serviceFoods             : CServiceFoods by inject()

    val id                                  = bind(CFood::propertyId)
    val name                                = bind(CFood::propertyName)
    val kal                                 = bind(CFood::propertyKal)
    val date                                = bind(CFood::propertyDate)
    val timeinterval                        = bind(CFood::propertyTimeInterval)

    /******************************************************************************************
     * Сохранение данных из полей формы в объект в оперативной памяти.                        *
     *****************************************************************************************/
    fun save()
    {
        this.commit() //Сохранение данных из полей на форме в сущность

        //Передача данных на сервер (изменили на кэш только)
        serviceFoods.save(this.item)
    }
}

