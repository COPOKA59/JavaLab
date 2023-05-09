package com.example.demo.viewmodels

import com.example.demo.model.CFood
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import com.example.demo.services.CServiceFoods
import com.example.demo.services.CServiceDates
import com.example.demo.services.CServiceTimeIntervals
import tornadofx.*

/*************************************************************************************
 * Модель представления для списка еды.                                              *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *************************************************************************************/
class CViewModelFoodsList                 : Controller()
{
    //Сервис с логикой для работы со списком рейсов.
    private val serviceFoods                     : CServiceFoods by inject()
    private val serviceDates                     : CServiceDates by inject()
    private val serviceTimeIntervals             : CServiceTimeIntervals by inject()

    //Список еды с возможность отслеживания изменений.
    val foods                           = serviceFoods.getAll()
    val dates                           = serviceDates.getAll().map { SimpleObjectProperty(it) }.toObservable()
    val timeinterval                    = serviceTimeIntervals.getAll().map { SimpleObjectProperty(it) }.toObservable()

    //Факт наличия выделения в таблице.
    //От него зависит активность некоторых кнопок.
    val elementSelected                       = SimpleBooleanProperty(false)

    /***********************************************************************************
     * Добавление пустой записи в таблицу.                                             *
     **********************************************************************************/
    fun add()
    {
        serviceFoods.add(CFood()) //Передача данных на сервер

    }
    /**************************************************************************************
     * Удаление записи из списка.                                                         *
     * @param item - элемент для удаления.                                                *
     *************************************************************************************/
    fun delete(item:CFood?)
    {
        //Если элемент не указан - ничего не делаем.
        item ?: return
        serviceFoods.delete(item) //Передача данных на сервер
    }
    /***********************************************************************************
     * Обработка изменения выделенных элементов в списке.                              *
     * @param selectedItem - выделенный элемент.                                       *
     **********************************************************************************/
    fun onSelectionChange(selectedItem:CFood?)
    {
        elementSelected.set(selectedItem != null)
    }
    /***************************************************************************************
     * Отправка всех текущих данных из списка на сервер.                                   *
     **************************************************************************************/
    fun saveAll()
    {
        serviceFoods.saveAll() //Передача команды на отправку данных на сервер
    }
}

