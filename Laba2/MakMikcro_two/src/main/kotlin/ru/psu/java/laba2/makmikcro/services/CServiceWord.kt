package ru.psu.java.laba2.makmikcro.services

import org.apache.poi.xwpf.usermodel.*
import org.springframework.stereotype.Service
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.model.IDateWithCounter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime
import javax.servlet.http.HttpServletResponse

@Service
class CServiceWord(val serviceFoods: IServiceFoods) : IServiceWord {
    /****************************************************************************************************
     * Создание заголовка для файла-отчёта.                                                             *
     * @param document - заготовка файла-отчёта.                                                        *
     ***************************************************************************************************/
    private fun createTitle(
            document: XWPFDocument
    ) {
        //Создание параграфа
        val par = document.createParagraph()
        //Центрирование параграфа
        par.alignment = ParagraphAlignment.CENTER

        //Создание куска текста
        val run = par.createRun()
        //Установка содержимого текста
        run.setText("Список дат с максимальным количеством еды меньше 400 калорий и количество этой еды")
        //Жирность
        run.isBold = true
        //Шрифт
        run.fontFamily = "!GOST_A"
        //Размер шрифта
        run.fontSize = 20
        run.color = "2A52BE"
    }

    private fun createTitleDayN(
            document: XWPFDocument
    ) {
        //Создание параграфа
        document.createParagraph().createRun().setText(" ")
        val par = document.createParagraph()

        //Центрирование параграфа
        par.alignment = ParagraphAlignment.CENTER

        //Создание куска текста
        val run = par.createRun()

        //Установка содержимого текста
        run.setText("Дни, когда были записаны Бананы")
        //Жирность
        run.isBold = true
        //Шрифт
        run.fontFamily = "!GOST_A"
        //Размер шрифта
        run.fontSize = 20
        run.color = "4682B4"
    }

    //Стиль текста шапок таблицы
    private fun createHeaderCell(
            row: XWPFTableRow,
            pos: Int,
            text: String
    ) {
        val par: XWPFParagraph
        val run: XWPFRun
        val cell: XWPFTableCell
        cell = row.getCell(pos)
        par = cell.addParagraph()
        par.alignment = ParagraphAlignment.CENTER
        par.verticalAlignment = TextAlignment.BOTTOM
        run = par.createRun()
        //Установка содержимого текста
        run.setText(text)
        //Жирность
        run.isBold = true
        //Шрифт
        run.fontFamily = "Times New Roman"
        //Размер шрифта
        run.fontSize = 10
        //Цвет шрифта
        run.color = "5171D1"
    }

    //Стиль текста содержимого таблицы
    private fun createCell(
            row: XWPFTableRow,
            pos: Int,
            text: String
    ) {
        val par: XWPFParagraph
        val run: XWPFRun
        val cell: XWPFTableCell
        cell = row.getCell(pos)
        par = cell.addParagraph()
        par.alignment = ParagraphAlignment.CENTER
        par.verticalAlignment = TextAlignment.BOTTOM
        run = par.createRun()
        //Установка содержимого текста
        run.setText(text)
        //Жирность
        run.isBold = true
        //Шрифт
        run.fontFamily = "Times New Roman"
        //Размер шрифта
        run.fontSize = 10
    }

    /****************************************************************************************************
     * Создание таблицы с едой в файле-отчёте.                                                      *
     * @param document - заготовка файла-отчёта.                                                        *
     * @param datesList - даты, данные которых выводятся в отчёт.                                    *
     ***************************************************************************************************/
    private fun createTableNight(
            document: XWPFDocument,
            datesList: Iterable<IDateWithCounter>
    ) {

        val table = document.createTable(1, 2)
        table.width = 5 * 1900
        var row = table.getRow(0)
        createHeaderCell(row, 0, "Дата")
        createHeaderCell(row, 1, "Количество еды")

        //Создание строк с информацией по датам.

        for (date in datesList) {
            row = table.createRow()
            date.date.let { createCell(row, 0, it) }
            date.count.let { createCell(row, 1, it.toString()) }

        }

        //Прокраска границ таблицы. Необходимость надо проверять в MS Word.
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
    }

    private fun createTableDayN(
            document: XWPFDocument,
            foodList: List<CFood>
    ) {
        val table = document.createTable(1, 6)
        table.width = 5 * 1900
        var row = table.getRow(0)
        createHeaderCell(row, 0, "Дата")
        createHeaderCell(row, 1, "Временной промежуток")
        createHeaderCell(row, 2, "Начало")
        createHeaderCell(row, 3, "Конец")
        createHeaderCell(row, 4, "Еда")
        createHeaderCell(row, 5, "Калории")

        //Создание строк с информацией по еде.

        for (food in foodList) {
            var time: LocalTime
            var sTime: String

            row = table.createRow()
            if (food.date != null) {
                food.date!!.date?.let { createCell(row, 0, it.toString()) }
            } else {
                createCell(row, 0, "День не найден")
            }
            if (food.timeinterval != null) {
                food.timeinterval!!.name?.let { createCell(row, 1, it) }
            } else {
                createCell(row, 1, "Временной промежуток не найден")
            }
            if (food.timeinterval != null) {
                time = food.timeinterval!!.vrN!!
                sTime = String.format("%s", time)
                createCell(row, 2, sTime)
                time = food.timeinterval!!.vrK!!
                sTime = String.format("%s", time)
                createCell(row, 3, sTime)
                //food.timeinterval!!.vrN?.let { createCell(row, 2, it) }
            } else {
                createCell(row, 2, "Временной промежуток не найден")
                createCell(row, 3, "Временной промежуток не найден")
            }

            food.name?.let { createCell(row, 4, it) }
            food.kal.let { createCell(row, 5, it.toString()) }
        }

        //Прокраска границ таблицы. Необходимость надо проверять в MS Word.
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
    }


    override fun downloadWord(response: HttpServletResponse)
    {
        try {

            val b = ByteArrayOutputStream()
            val doc = XWPFDocument()

            //Заголовок
            createTitle(doc)
            //Таблица с датами.
            createTableNight(doc, serviceFoods.getWithMaxFoods())
            //Заголовок 2 таблицы
            createTitleDayN(doc)
            //Таблица датами еды Бананы.
            createTableDayN(doc, serviceFoods.getByDayN())

            doc.write(b) // doc должен быть XWPFDocument

            val inputStream: InputStream = ByteArrayInputStream(b.toByteArray())

            response.contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            org.apache.commons.io.IOUtils.copy(inputStream, response.outputStream);
            response.flushBuffer();
        } catch ( ex: IOException) {}
    }
}