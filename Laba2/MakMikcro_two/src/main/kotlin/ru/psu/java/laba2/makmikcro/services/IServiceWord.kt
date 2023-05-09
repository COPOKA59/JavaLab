package ru.psu.java.laba2.makmikcro.services

import javax.servlet.http.HttpServletResponse

/****************************************************************************************************
 * Интерфейс сервиса с бизнес-логикой для построения результатирующего файла.                       *
 * @author Макарова П.Ф. 12.03.2023                                                                      *
 ****************************************************************************************************/
interface IServiceWord {
    fun downloadWord(response : HttpServletResponse) //получение отчёта
}