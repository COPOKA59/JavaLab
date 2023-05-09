package ru.psu.java.laba2.makmikcro.services

import org.springframework.web.multipart.MultipartFile

/***************************************************************************************************
 * Интерфейс сервиса с бизнес-логикой для работы с входными данными.                               *
 * @author Макарова П.Ф. 12.03.2023                                                                *
 ***************************************************************************************************/
interface IServiceExcel {
    fun uploadExcel(file : MultipartFile) //загрузка данных
}