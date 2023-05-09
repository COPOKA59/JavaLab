package com.example.demo.app

import com.example.demo.view.CViewFoodTable
import tornadofx.*
import java.util.*

class MyApp                 :App(CViewFoodTable::class, Styles::class)
{
    private val api                         : Rest by inject()
    init {
        //Язык интерфейса приложения.
        FX.locale                           = Locale("ru")
        //Базовая часть адреса для подключения к API сервера.
        api.baseURI                         = "http://localhost:8080/"
    }
}