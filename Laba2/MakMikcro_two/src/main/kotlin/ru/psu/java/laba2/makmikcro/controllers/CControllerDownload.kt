package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.web.bind.annotation.*
import ru.psu.java.laba2.makmikcro.services.IServiceWord
import javax.servlet.http.HttpServletResponse

/*********************************************************************
 * Контроллер REST запросов для формирования аналитического отчёта.  *
 * @author Макарова П.Ф. 12.03.2023.                                 *
 *********************************************************************/
@RestController
@RequestMapping("/download")
class CControllerDownload(
    val serviceWord: IServiceWord
) {

    @GetMapping("/")
    fun getFile(
        response: HttpServletResponse
    ) {
        serviceWord.downloadWord(response)
    }
}