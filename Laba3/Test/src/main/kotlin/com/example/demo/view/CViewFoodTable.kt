package com.example.demo.view

import com.example.demo.model.CFood
import com.example.demo.viewmodels.CViewModelFoods
import com.example.demo.viewmodels.CViewModelFoodsList
import javafx.scene.layout.BorderPane
import tornadofx.*

/************************************************************************************
 * Страница со списком еды.                                                         *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 ************************************************************************************/

class CViewFoodTable                     : View("Блюда") {
    //Корневой элемент формы - элемент с 5ю областями (Верх, Низ, Левая, Правая, Центр)
    override val root = BorderPane()

    //Модель представления для списка в целом.
    private val viewModelList: CViewModelFoodsList by inject()

    //Модель представления для одного элемента,
    //отображаемого в правой боковой панели редактирования.
    val viewModelItem = CViewModelFoods(CFood())

    //Таблица еды.
    //В качестве параметра передаём список еды из модели представления.
    private val table = tableview(viewModelList.foods)
    {
        isEditable = true
        column(messages["ID"], CFood::propertyId)
        column(messages["Name"], CFood::propertyName)
        column(messages["Kal"], CFood::propertyKal)

        column(messages["Date"], CFood::propertyDate) {
            cellFormat {
                text = ""
                if (it != null) {
                var l = viewModelList.dates.map { it1 -> it1.value }.find { i -> i.id == it.id }
                    if (l != null) {
                        text = l.date.toString()
                    }
                }
            }
        }
        column(messages["TimeInterval"], CFood::propertyTimeInterval) {
            cellFormat {
                text = ""
                if (it != null) {
                    var l = viewModelList.timeinterval.map { it1 -> it1.value }.find { i -> i.id == it.id }
                    if (l != null) {
                        text = l.name
                    }
                }
            }
        }

        //Изменение выделения предаём в модель представления правой формы.
        viewModelItem.rebindOnChange(this) { selectedItem ->
            item = selectedItem ?: CFood()
        }
        //Изменение выделения передаём в модель представления всего списка.
        onSelectionChange {
            viewModelList.onSelectionChange(this.selectedItem)
        }
    }

    init {
        //Верхняя часть окна
        root.top {
            //Делится на 2 строки
            vbox {
                //Самая верхняя строка - меню.
                menubar {
                    menu("Окна") {
                        item(messages["Date"]).action {
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewDatesTable>()
                        }
                        item(messages["TimeInterval"]).action {
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewTimeIntervalsTable>()
                        }
                    }
                }
                //Вторая строчка сверху - кнопки для работы со списком объектов.
                hbox {
                    button(messages["Add"]) {
                        action {
                            viewModelList.add()

                        }
                    }
                    button(messages["Delete"]) {
                        //Активность кнопки удалить зависит от поля в модели представления.
                        enableWhen(viewModelList.elementSelected)
                        action {
                            viewModelList.delete(table.selectedItem)
                        }
                    }
                    button(messages["Save"]) {
                        action {
                            viewModelList.saveAll()
                        }
                    }
                }
            }
        }
        root.center {
            //В центральной части располагается таблица.
            this += table
        }
        root.right {
            //В правой части располагается форма для редактирования одного объекта.
            form {
                fieldset("Редактировать данные еды") {
                    field(messages["Name"]) {
                        textfield(viewModelItem.name)
                    }
                    field(messages["Kal"]) {
                        textfield(viewModelItem.kal) //textfield(Double.toString(viewModelItem.kal))
                    }

                    field(messages["TimeInterval"]) {
                        combobox(viewModelItem.timeinterval) {
                            items = viewModelList.timeinterval
                            setOnAction {
                                viewModelItem.timeinterval.value.value = this.selectedItem?.value
                                viewModelItem.markDirty(viewModelItem.timeinterval)
                            }
                            cellFormat { text = it.value?.name }
                        }
                    }
                    field(messages["Date"]) {
                        combobox(viewModelItem.date) {
                            items = viewModelList.dates
                            setOnAction {
                                viewModelItem.date.value.value = this.selectedItem?.value
                                viewModelItem.markDirty(viewModelItem.date)
                            }
                            cellFormat{ text = it.value?.date.toString() }
                        }
                    }

                    hbox {
                        button(messages["Save"]) {
                            enableWhen(viewModelItem.dirty)
                            action {
                                table.selectedItem?.timeinterval = viewModelItem.timeinterval.value.value
                                table.selectedItem?.date = viewModelItem.date.value.value

                                viewModelItem.save()

                            }
                        }
                        button(messages["Cancel"]).action {
                            viewModelItem.rollback()
                        }
                    }
                }
                setMaxSize(500.0, 50.0)
            }
        }
    }
}
