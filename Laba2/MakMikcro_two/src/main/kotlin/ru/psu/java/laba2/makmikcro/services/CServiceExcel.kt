package ru.psu.java.laba2.makmikcro.services

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CFood
import ru.psu.java.laba2.makmikcro.model.CTimeInterval
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryFoods
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryDates
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryTimeIntervals
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/***************************************************
 * Загрузка из Excel.
 ***************************************************/
@Service
class CServiceExcel (
        val repositoryDates: IRepositoryDates,
        val repositoryFoods: IRepositoryFoods,
        val repositoryTimeIntervals: IRepositoryTimeIntervals
) : IServiceExcel
{
    override fun uploadExcel(file: MultipartFile) {
        val wb = XSSFWorkbook(file.inputStream)
        try {

            val loadDate = loadDate(wb)
            repositoryDates.saveAll(loadDate.values)
            val loadTimeInterval = loadTimeInterval(wb)
            repositoryTimeIntervals.saveAll(loadTimeInterval.values)
            val loadFood = loadFood(wb)
            loadFoodRelations(wb, loadFood, loadDate, loadTimeInterval)
            repositoryFoods.saveAll(loadFood.values)
        } catch (e: Exception) {
            println("Формат файла не поддерживается!")
            e.printStackTrace()
        }

    }

    /************************************************************
     * Загрузка информации о связях еды из электронной таблицы. *
     * Результат в объектах карт foods, timeintervals, dates.   *
     * @param wb - рабочая книга с данными.                     *
     ************************************************************/
    private fun loadFoodRelations(
            wb: XSSFWorkbook,
            foods: LinkedHashMap<UUID, CFood>,
            dates: LinkedHashMap<UUID, CDate>,
            timeintervals: LinkedHashMap<UUID, CTimeInterval>
    ) {
        val sheet: Sheet = wb.getSheetAt(3)
        var row: Row?
        var cell: Cell
        val nRows = sheet.lastRowNum
        var sId: String
        var sDateId: String?
        var sTimeIntervalId: String?
        var id: UUID?
        var dateId: UUID?
        var timeintervalId: UUID?
        var date: CDate
        var timeinterval: CTimeInterval
        var food: CFood
        var i = 0
        while (i < nRows) {
            row = sheet.getRow(i)
            if (row == null) {
                i++
                continue
            }
            if (row.lastCellNum < 5) {
                i++
                continue
            }
            cell = row.getCell(0)
            sId = cell.stringCellValue
            if (sId.isEmpty()) {
                i++
                continue
            }
            id = UUID.fromString(sId)
            food = foods[id]!!
            cell = row.getCell(3)
            if (cell.cellType != CellType.BLANK) {
                sDateId = cell.stringCellValue
                dateId = UUID.fromString(sDateId)
                date = dates[dateId]!!
                food.date = date
            }
            cell = row.getCell(4)
            if (cell.cellType != CellType.BLANK) {
                sTimeIntervalId = cell.stringCellValue
                timeintervalId = UUID.fromString(sTimeIntervalId)
                timeinterval = timeintervals[timeintervalId]!!
                food.timeinterval = timeinterval
            }
            i++
        }
    }
    /***********************************************************************
     * Загрузка информации о временных промежутков из электронной таблицы. *
     * Результат в карте timeintervals.                                    *
     * @param wb - рабочая книга с данными.                                *
     ***********************************************************************/

    private fun loadTimeInterval(wb: XSSFWorkbook) : LinkedHashMap<UUID, CTimeInterval>
    {
        val timeintervals = LinkedHashMap<UUID, CTimeInterval>()
        val sheet: Sheet = wb.getSheetAt(2)
        var row: Row?
        var cell: Cell
        var i: Int
        val nRows = sheet.lastRowNum
        var tiUUID: String
        var name: String?
        var vr: String?
        var vrN: LocalTime?
        var vrK: LocalTime?
        var id: UUID?
        var timeInterval: CTimeInterval
        // Перебираются строки 1 таблицы в файле .xlsx.
        i = 0
        while (i <= nRows) {
            row = sheet.getRow(i)
            // Просматривается есть ли данные в ряду.
            if (row == null) {
                i++
                continue
            }
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.lastCellNum < 3) {
                i++
                continue
            }
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0)
            tiUUID = cell.stringCellValue
            if (tiUUID.length == 0) {
                i++
                continue
            }

            // Создаётся новый объект класса.
            timeInterval = CTimeInterval()
            id = UUID.fromString(tiUUID)
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            timeInterval.id = id
            cell = row.getCell(1)
            name = cell.stringCellValue
            timeInterval.name = name
            //cell = row.getCell(2)
            //vr = cell.stringCellValue
            //timeInterval.vr = vr
            cell = row.getCell(2)
            timeInterval.vrN = cell.localDateTimeCellValue.toLocalTime()
            cell = row.getCell(3)
            timeInterval.vrK = cell.localDateTimeCellValue.toLocalTime()


            // Объект помещается в карточку timeintervals.
            timeintervals[id] = timeInterval
            i++
        }
        return timeintervals;
    }

    /******************************************************
     * Загрузка информации о дате из электронной таблицы. *
     * Результат в карте dates.                           *
     * @param wb - рабочая книга с данными.               *
     ******************************************************/
    private fun loadDate(wb: XSSFWorkbook) : LinkedHashMap<UUID, CDate> {
        val dates = LinkedHashMap<UUID, CDate>()
        val sheet: Sheet = wb.getSheetAt(1)
        var row: Row?
        var cell: Cell
        var i: Int
        val nRows = sheet.lastRowNum
        var tUUID: String
        var id: UUID?
        var time: CDate
        var date: LocalDate?
        // Перебираются строки 2 таблицы в файле .xlsx.
        i = 0
        while (i <= nRows) {
            row = sheet.getRow(i)
            // Просматривается есть ли данные в ряду.
            if (row == null) {
                i++
                continue
            }
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.lastCellNum < 2) {
                i++
                continue
            }
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0)
            tUUID = cell.stringCellValue
            if (tUUID.length == 0) {
                i++
                continue
            }

            // Создаётся новый объект класса.
            time = CDate()
            id = UUID.fromString(tUUID)
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            time.id = id
            cell = row.getCell(1)
            date = cell.localDateTimeCellValue.toLocalDate()
            time.date = date

            val dayNumber = date!!.dayOfWeek
            if (dayNumber.value == 1) {
                time.day = "Пн"
            } else { if (dayNumber.value == 2) {
                time.day = "Вт"
            } else { if (dayNumber.value == 3) {
                time.day = "Ср"
            } else { if (dayNumber.value == 4) {
                time.day = "Чт"
            } else { if (dayNumber.value == 5) {
                time.day = "Пт"
            } else { if (dayNumber.value == 6) {
                time.day = "Сб"
            } else { if (dayNumber.value == 7) {
                time.day = "Вс"
            } } } } } } }

            // Объект помещается в карточку times.
            dates[id] = time
            i++
        }
        return dates;
    }

    /*******************************************************
     * Загрузка информации об еде из электронной таблицы.  *
     * Результат в карте foods.                            *
     * @param wb - рабочая книга с данными.                *
     *******************************************************/
    private fun loadFood(wb: XSSFWorkbook) : LinkedHashMap<UUID, CFood> {
        val foods = LinkedHashMap<UUID, CFood>()
        val sheet: Sheet = wb.getSheetAt(3)
        var row: Row?
        var cell: Cell
        var i: Int
        val nRows = sheet.lastRowNum
        var fUUID: String
        var name: String?
        var kalors: Double
        var id: UUID?
        var food: CFood
        // Перебираются строки 3 таблицы в файле .xlsx.
        i = 0
        while (i < nRows) {
            row = sheet.getRow(i)
            // Просматривается есть ли данные в ряду.
            if (row == null) {
                i++
                continue
            }
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.lastCellNum < 5) {
                i++
                continue
            }
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0)
            fUUID = cell.stringCellValue
            if (fUUID.length == 0) {
                i++
                continue
            }

            // Создаётся новый объект класса.
            food = CFood()
            id = UUID.fromString(fUUID)
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            food.id = id
            // Заполняется в объекте Название из 2 ячейки в строке.
            cell = row.getCell(1)
            name = cell.stringCellValue
            food.name = name
            // Заполняется в объекте Калории из 3 ячейки в строке.
            cell = row.getCell(2)
            kalors = cell.numericCellValue
            food.kal = kalors//food.kal = kalors.toString() //food.kal = kalors
            // Объект помещается в карточку foods.
            foods[id] = food
            i++
        }
        return foods;
    }

}