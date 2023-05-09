package com.example.demo.repositories

import com.example.demo.model.CTimeInterval
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.toModel

/*****************************************************************************************
 * Репозиторий с запросами к серверу в части списка временного интервала.                *
 *****************************************************************************************/
class CRepositoryTimeInterval : Controller() {
    //Объект для отправки запросов к API сервера.
    private val api: Rest by inject()

    //"Кэшированный" список времнного интервала для локальной работы.
    private val timeIntervals = FXCollections.observableArrayList<CTimeInterval>()

    /*****************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                     *
     *****************************************************************************************/
    fun getAll(): ObservableList<CTimeInterval> {
        if(timeIntervals.isNotEmpty())
            return timeIntervals
        //Запрашиваем актуальные данные на сервере
        val listFromServer = requestAll()
        //Пересохраняем в локальный список,
        //в котором дополнительно будем кэшировать изменения в процессе
        //редактирования таблицы.
        timeIntervals.addAll(listFromServer)
        //Возвращаем ссылку на кэш.
        return timeIntervals
    }

    /*****************************************************************************************
     * Запрос списка временного интервала на сервере и обработка возможных проблем.          *
     *****************************************************************************************/
    private fun requestAll(): ObservableList<CTimeInterval> {
        //Собственно выполнение GET запроса к пути /timeintervals
        val response: Rest.Response = api.get("timeintervals")
        try {
            if (response.ok()) {
                //Конвертация json в список объектов типа CTimeInterval
                return response
                    .list()
                    .toModel()
            } else if (response.statusCode == 404)
                throw Exception("404")
            else
                throw Exception("getCustomer returned ${response.statusCode} ${response.reason}")
        } catch (e: Exception) {
            throw Exception("Отсутствует соединение с сервером.", e)
        } finally {
            response.consume()
        }
    }

    /*****************************************************************************************
     * Изменение временного интервала в локальном списке.                                    *
     * @param timeInterval - объект для отправки.                                            *
     *****************************************************************************************/
    fun save(timeInterval: CTimeInterval) {
        //Не стали делать отправку, т.к. приняли централизованно все изменения для списка отправлять вмсте.
    }

    /*****************************************************************************************
     * Отправка списка объектов на сервер.                                                   *
     *****************************************************************************************/
    fun saveAll() {
        //Запрос данных с сервера.
        val timeIntervalsFromServer = requestAll()

        //Перебираем все временные интервалы с сервера,
        //ищем тех, кто отсутствует в локальном списке,
        //для них отправляем запрос на удаление.
        timeIntervalsFromServer
            .forEach { timeIntervalFromServer ->
                //Для того, чтобы метод contains сработал правильно,
                //и чтобы не писать ручной перебор,
                //переопределён метод CTimeInterval.equals
                if (!timeIntervals.contains(timeIntervalFromServer)) {
                    api.delete("timeintervals", timeIntervalFromServer)
                }
            }
        //Перебираем все записи из локального кэша
        var temp: List<CTimeInterval>
        timeIntervals
            .forEach { timeIntervalLocal ->
                //Для каждой локальной записи находим записи из сервера с такими же id.
                temp = timeIntervalsFromServer
                    .filter { timeIntervalFromServer ->
                        timeIntervalLocal.equals(timeIntervalFromServer)
                    }
                // Записи с такими же id
                // (должна быть максимум 1, но гарантий никто не даст)
                // фильтруем по наличию изменений
                temp
                    .firstOrNull { timeIntervalFromServer ->
                        timeIntervalLocal.hasChanges(timeIntervalFromServer)
                    }
                    //Если изменения в полях есть, публикуем текущую запись на сервер.
                    ?.let {
                        api.post("timeintervals", timeIntervalLocal)
                    }
                //Если вообще на сервере записей с таким id нет
                //публикуем текущую запись.
                if (temp.isEmpty())
                    api.post("timeintervals", timeIntervalLocal)

            }
    }

    /*****************************************************************************************
     * Добавление временного промежутка в локальный список.                                  *
     * @param timeInterval - объект для отправки.                                            *
     *****************************************************************************************/
    fun add(timeInterval: CTimeInterval) {
        timeIntervals.add(timeInterval)
    }

    /******************************************************************************************
     * Удаление врпеменного промежутка из локального списка.                                  *
     * @param timeInterval - объект для отправки.                                             *
     ******************************************************************************************/
    fun delete(timeInterval: CTimeInterval) {
        timeIntervals.remove(timeInterval)
    }
}
