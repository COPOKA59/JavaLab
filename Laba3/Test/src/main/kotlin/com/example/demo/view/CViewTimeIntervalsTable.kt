package com.example.demo.view

import com.example.demo.model.CTimeInterval
import com.example.demo.util.convertes.CConverterTime
import com.example.demo.viewmodels.CViewModelTimeIntervals
import com.example.demo.viewmodels.CViewModelTimeIntervalsList
import javafx.scene.layout.BorderPane
import tornadofx.*
/*************************************************************************************
 * Страница со списком временного промежутка.                                        *
 * @author Макарова П.Ф. 26.03.2023                                                      *
 *************************************************************************************/
class CViewTimeIntervalsTable                     : View("Временные промежутки")
{
    //Корневой элемент формы - элемент с 5ю областями (Верх, Низ, Левая, Правая, Центр)
    override val root                       = BorderPane()
    //Модель представления для списка в целом.
    private val viewModelList               : CViewModelTimeIntervalsList by inject()
    //Модель представления для одного элемента,
    //отображаемого в правой боковой панели редактирвоания.
    val viewModelItem                       = CViewModelTimeIntervals(CTimeInterval())

    //Таблица временных промежутков.
    //В качестве параметра передаём список временных промежутков из модели представления.
    val table                               = tableview(viewModelList.timeIntervals)
    {
        isEditable                          = true
        column(messages["ID"], CTimeInterval::propertyId)
        column(messages["Name"],CTimeInterval::propertyName)
        column(messages["VrN"],CTimeInterval::propertyVrN).makeEditable(CConverterTime)
        column(messages["VrK"],CTimeInterval::propertyVrK).makeEditable(CConverterTime)

        //Изменение выделения предаём в модель представления правой формы.
        viewModelItem.rebindOnChange(this) { selectedItem ->
            item                            = selectedItem ?: CTimeInterval()
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
                        item("Даты").action{
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewDatesTable>()
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
                fieldset("Редактировать данные временного промежутка") {

                    field(messages["Name"]) {
                        textfield(viewModelItem.name)
                    }
                    field(messages["VrN"]) {
                        textfield(viewModelItem.VrN, CConverterTime)
                    }
                    field(messages["VrK"]) {
                        textfield(viewModelItem.VrK, CConverterTime)
                    }
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
