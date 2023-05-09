package com.example.demo.view

import com.example.demo.model.CDate
import com.example.demo.util.convertes.CConverterDate
import com.example.demo.viewmodels.CViewModelDates
import com.example.demo.viewmodels.CViewModelDatesList
import javafx.scene.layout.BorderPane
import tornadofx.*
/************************************************************************************
 * Страница со списком дат.                                                         *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 ************************************************************************************/
class CViewDatesTable                     : View("Даты")
{
    //Корневой элемент формы - элемент с 5ю областями (Верх, Низ, Левая, Правая, Центр)
    override val root                       = BorderPane()
    //Модель представления для списка в целом.
    private val viewModelList               : CViewModelDatesList by inject()
    //Модель представления для одного элемента,
    //отображаемого в правой боковой панели редактирвоания.
    val viewModelItem                       = CViewModelDates(CDate())

    //Таблица дат.
    //В качестве параметра передаём список дат из модели представления.
    val table                               = tableview(viewModelList.dates)
    {
        isEditable                          = true
        column(messages["ID"],CDate::propertyId)
        column(messages["Date"], CDate::propertyDate).makeEditable(CConverterDate)
        column(messages["day"], CDate::propertyDay).makeEditable()

        //Изменение выделения предаём в модель представления правой формы.
        viewModelItem.rebindOnChange(this) { selectedItem ->
            item                            = selectedItem ?: CDate()
        }
        //Изменение выделения передаём в модель представления всего списка.
        onSelectionChange {
            viewModelList.onSelectionChange(this.selectedItem)
        }

    }

    init {
        //Верхняя часть окна
        root.top{
            //Делится на 2 строки
            vbox {
                //Самая верхняя строка - меню.
                menubar {
                    menu("Окна") {
                        item("Блюда(Еда)").action{
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewFoodTable>()
                        }
                        item("Временной промежуток").action{
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewTimeIntervalsTable>()
                        }
                    }
                }
                //Вторая строчка сверху - кнопки для работы со списком объектов.
                hbox {
                    button(messages["Add"]) {
                        action{
                            viewModelList.add()
                        }
                    }
                    button(messages["Delete"]) {
                        //Активность кнопки удалить зависит от поля в модели представления.
                        enableWhen(viewModelList.elementSelected)
                        action{
                            viewModelList.delete(table.selectedItem)
                        }
                    }
                    button(messages["Save"]) {
                        action{
                            viewModelList.saveAll()
                        }
                    }
                }
            }
        }
        root.center{
            //В центральной части располагается таблица.
            this                            += table
        }
        root.right  {
            //В правой части располагается форма для редактирования одного объекта.
            form{
                fieldset("Редактировать данные даты") {
                    field("Дата") {
                        datepicker(viewModelItem.date)
                    }
                    //field("День недели") {
                    //    textfield(viewModelItem.day)
                    //}
                    hbox{
                        button(messages["Save"]) {
                            enableWhen(viewModelItem.dirty)
                            action{
                                viewModelItem.save()
                            }
                        }
                        button(messages["Cancel"]).action {
                            viewModelItem.rollback()
                        }
                    }
                }
            }
        }
    }
}
