package com.example.demo.repositories

import com.example.demo.model.CDate
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.toModel

/*****************************************************************************************
 * Репозиторий с запросами к серверу в части списка даты.                                *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *****************************************************************************************/
class CRepositoryDate : Controller() {
    //Объект для отправки запросов к API сервера.
    private val api: Rest by inject()

    //"Кэшированный" список дат для локальной работы.
    private val dates = FXCollections.observableArrayList<CDate>()

    /********************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                        *
     *******************************************************************************************/
    fun getAll(): ObservableList<CDate> {
        if(dates.isNotEmpty())
            return dates
        //Запрашиваем актуальные данные на сервере
        val listFromServer = requestAll()
        //Пересохраняем в локальный список,
        //в котором дополнительно будем кэшировать изменения в процессе
        //редактирования таблицы.
        dates.addAll(listFromServer)
        //Возвращаем ссылку на кэш.
        return dates
    }

    /*****************************************************************************************
     * Запрос списка дат на сервере и обработка возможных проблем.                           *
     *****************************************************************************************/
    private fun requestAll(): ObservableList<CDate> {
        //Собственно выполнение GET запроса к пути /dates
        val response: Rest.Response = api.get("dates")
        try {
            if (response.ok()) {
                //Конвертация json в список объектов типа CDate
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
     * Изменение даты в локальном списке.                                                  *
     * @param date - объект для отправки.                                                   *
     ****************************************************************************************/
    fun save(date: CDate) {
        //Не стали делать отправку, т.к. приняли централизованно все изменения для списка отправлять вмсте.
    }

    /***************************************************************************************
     * Отправка списка объектов на сервер.                                                 *
     **************************************************************************************/
    fun saveAll() {
        //Запрос данных с сервера.
        val datesFromServer = requestAll()

        //Перебираем всех дат с сервера,
        //ищем тех, кто отсутствует в локальном списке,
        //для них отправляем запрос на удаление.
        datesFromServer
            .forEach { dateFromServer ->
                //Для того, чтобы метод contains сработал правильно,
                //и чтобы не писать ручной перебор,
                //переопределён метод Date.equals
                if (!dates.contains(dateFromServer)) {
                    api.delete("dates", dateFromServer)
                }
            }
        //Перебираем все записи из локального кэша
        var temp: List<CDate>
        dates
            .forEach { dateLocal ->
                //Для каждой локальной записи находим записи из сервера с такими же id.
                temp = datesFromServer
                    .filter { dateFromServer ->
                        dateLocal.equals(dateFromServer)
                    }
                //Записи с такими же id
                // (должна быть максимум 1, но гарантий никто не даст)
                // фильтруем по наличию изменений
                temp
                    .firstOrNull { dateFromServer ->
                        dateLocal.hasChanges(dateFromServer)
                    }
                    //Если изменения в полях есть, публикуем текущую запись на сервер.
                    ?.let {
                        api.post("dates", dateLocal)
                    }
                //Если вообще на сервере записей с таким id нет
                //публикуем текущую запись.
                if (temp.isEmpty())
                    api.post("dates", dateLocal)

            }
    }

    /*******************************************************************************************
     * Добавление даты в локальный список.                                                     *
     * @param date - объект для отправки.                                                      *
     *******************************************************************************************/
    fun add(date: CDate) {
        dates.add(date)
    }

    /*****************************************************************************************
     * Удаление даты из локального списка.                                                   *
     * @param date - объект для отправки.                                                    *
     *****************************************************************************************/
    fun delete(date: CDate) {
        dates.remove(date)
    }
}

