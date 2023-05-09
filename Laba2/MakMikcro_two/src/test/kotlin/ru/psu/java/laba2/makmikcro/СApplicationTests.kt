package ru.psu.java.laba2.makmikcro

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.psu.java.laba2.makmikcro.services.CServiceFoods
import ru.psu.java.laba2.makmikcro.controllers.CControllerFoods
import java.util.*

/*****************************************************************************
 * Класс с автоматическими тестами для проверки функционала контроллера еды. *
 *****************************************************************************/

@SpringBootTest
class CApplicationTests {

    //Продуктивный сервис использовать не будем,
    //его функционал тестируется в отдельном классе,
    //здесь его имитируем с помощью метода mockk()
    private val serviceFoods             : CServiceFoods
            = mockk()

    //А вот контроллер еды продуктивный,
    //но будет вызывать методы имитируемого сервиса.
    private val controllerFoods          = CControllerFoods(serviceFoods)

    /*********************************************************************
     * Проверка обработки запроса на удаление еды по идентификатору.  *
     *********************************************************************/
    @Test
    fun testDeleteFoodsById()
    {
        val id                              = UUID.randomUUID()
        every {serviceFoods.deleteById(id)} returns "Объект удалён"

        val result                          = controllerFoods.deleteById(id)
        Assertions.assertEquals("Объект удалён", result)
    }
}
