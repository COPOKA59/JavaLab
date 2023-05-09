package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.psu.java.laba2.makmikcro.services.IServiceExcel

/*************************************************************
 * Контроллер REST запросов для для импорта данных из xlsx.  *
 * @author Макарова П.Ф. 12.03.2023.                         *
 *************************************************************/
@RestController
@RequestMapping("/upload")
class CControllerUpload (
    val serviceExcel                        : IServiceExcel
)
{
    @PostMapping("/")
    fun handleFileUpload(
        @RequestParam("file") file      : MultipartFile
    )                                       : String?
    {
        serviceExcel.uploadExcel(file)
        return "OK"
    }
}