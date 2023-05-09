package com.example.demo.services

import com.example.demo.model.CTimeInterval
import com.example.demo.repositories.CRepositoryTimeInterval
import tornadofx.*
import java.util.*

/*****************************************************************************************
 * Сервис с логикой для работы со списком временного промежутка.                         *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *****************************************************************************************/
class CServiceTimeIntervals                      : Controller()
{
    private val repositoryTimeInterval          : CRepositoryTimeInterval by inject()
    /***************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                   *
     ***************************************************************************************/
    fun getAll()                            = repositoryTimeInterval.getAll()

    /***************************************************************************************
     * Отправка данных объекта на сервер.                                                  *
     * @param timeInterval - объект для отправки.                                          *
     ***************************************************************************************/
    fun save(timeInterval: CTimeInterval)=repositoryTimeInterval.save(timeInterval)

    /*************************************************************************************
     * Отправка всех данных из списка на сервер.                                         *
     *************************************************************************************/
    fun saveAll()=repositoryTimeInterval.saveAll()

    /***************************************************************************************
     * Отправка данных объекта на сервер.                                                  *
     * @param timeInterval - объект для отправки.                                          *
     ***************************************************************************************/
    fun add(timeInterval:CTimeInterval) {
        timeInterval.id = UUID.randomUUID()
        repositoryTimeInterval.add(timeInterval)
    }

    /**************************************************************************************
     * Отправка данных объекта на сервер.                                                 *
     * @param timeInterval - объект для отправки.                                         *
     **************************************************************************************/
    fun delete(timeInterval: CTimeInterval)=repositoryTimeInterval.delete(timeInterval)
}
