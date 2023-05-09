package com.example.demo.repositories

import com.example.demo.model.CFood
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.toModel

/*****************************************************************************************
 * Репозиторий с запросами к серверу в части списка еды.                                 *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *****************************************************************************************/
class CRepositoryFood : Controller() {
    //Объект для отправки запросов к API сервера.
    private val api: Rest by inject()

    //"Кэшированный" список еды для локальной работы.
    private val foods: ObservableList<CFood> = FXCollections.observableArrayList()

    /*****************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                     *
     *****************************************************************************************/
    fun getAll(): ObservableList<CFood> {
        if(foods.isNotEmpty())
            return foods
        //Запрашиваем актуальные данные на сервере
        val listFromServer = requestAll()
        //Пересохраняем в локальный список,
        //в котором дополнительно будем кэшировать изменения в процессе
        //редактирования таблицы.
        foods.addAll(listFromServer)
        //Возвращаем ссылку на кэш.
        return foods
    }

    /*****************************************************************************************
     * Запрос списка еды на сервере и обработка возможных проблем.                         *
     *****************************************************************************************/
    private fun requestAll(): ObservableList<CFood> {
        //Собственно выполнение GET запроса к пути /foods
        val response: Rest.Response = api.get("foods")
        try {
            if (response.ok()) {
                //Конвертация json в список объектов типа CFood
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
     * Изменение еды в локальном списке.                                                     *
     * @param food - объект для отправки.                                                    *
     *****************************************************************************************/
    fun save(food: CFood) {
        //Не стали делать отправку, т.к. приняли централизованно все изменения для списка отправлять вместе.
    }

    /*****************************************************************************************
     * Отправка списка объектов на сервер.                                                   *
     *****************************************************************************************/
    fun saveAll() {
        //Запрос данных с сервера.
        val foodsFromServer = requestAll()

        //Перебираем всю еду с сервера,
        //ищем ту, кто отсутствует в локальном списке,
        //для них отправляем запрос на удаление.
        foodsFromServer
            .forEach { foodFromServer ->
                //Для того, чтобы метод contains сработал правильно,
                //и чтобы не писать ручной перебор,
                //переопределён метод Food.equals
                if (!foods.contains(foodFromServer)) {
                    api.delete("foods", foodFromServer)
                }
            }
        //Перебираем все записи из локального кэша
        var temp: List<CFood>
        foods
            .forEach { foodLocal ->
                //Для каждой локальной записи находим записи из сервера с такими же id.
                temp = foodsFromServer
                    .filter { foodFromServer ->
                        foodLocal.equals(foodFromServer)
                    }
                //Записи с такими же id
                // (должна быть максимум 1, но гарантий никто не даст)
                // фильтруем по наличию изменений
                temp
                    .firstOrNull { foodFromServer ->
                        foodLocal.hasChanges(foodFromServer)
                    }
                    //Если изменения в полях есть, публикуем текущую запись на сервер.
                    ?.let {
                        api.post("foods", foodLocal)
                    }
                //Если вообще на сервере записей с таким id нет
                //публикуем текущую запись.
                if (temp.isEmpty())
                    api.post("foods", foodLocal)

            }
    }

    /*****************************************************************************************
     * Добавление еды в локальный список.                                                    *
     * @param food - объект для отправки.                                                    *
     *****************************************************************************************/
    fun add(food: CFood) {
        this.foods.add(food)
    }

    /*****************************************************************************************
     * Удаление еды из локального списка.                                                    *
     * @param food - объект для отправки.                                                    *
     *****************************************************************************************/
    fun delete(food: CFood) {
        foods.remove(food)
    }
}
