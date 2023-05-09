package LaBa.One;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/********************************************
 * Дневник еды (данная предметная область). *
 * Основной класс программы.                *
 * @autor Макарова П.Ф. ПМИ-2 21.12.2022.   *
 ********************************************/


public class Main {
    private static final Map<UUID, CTimeInterval> timeintervals = new TreeMap<>();
    private static final Map<UUID, CFood> foods = new TreeMap<>();
    private static final Map<UUID, CTime> times = new TreeMap<>();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    /****************************************************
     * Открытие электронной таблицы с входными данными. *
     * @return - рабочая книга с данными.               *
     ****************************************************/
    private static XSSFWorkbook openExcel() {
        XSSFWorkbook wb = null;
        try(FileInputStream fis = new FileInputStream("timeintervals.xlsx")) {
            wb = new XSSFWorkbook(fis);
        }
        catch(FileNotFoundException e) {
            System.out.println("Не удалось открыть файл timeintervals.xlsx");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("Не удалось прочитать информацию из файла timeintervals.xlsx");
            e.printStackTrace();
        }

        return wb;
    }
    /***********************************************************************
     * Загрузка информации о временных промежутков из электронной таблицы. *
     * Результат в карте timeintervals.                                    *
     * @param wb - рабочая книга с данными.                                *
     ***********************************************************************/
    private static void loadTimeInterval(XSSFWorkbook wb) {
        // Берётся 2(считая их от 0) лист(таблица) из файла .xlsx.
        Sheet sheet = wb.getSheetAt(2);

        Row row;
        Cell cell;
        int i;
        int nRows = sheet.getLastRowNum();
        String tiUUID, name, vr;
        UUID id;
        CTimeInterval timeInterval;
        // Перебираются строки 1 таблицы в файле .xlsx.
        for (i = 0; i <= nRows; i++) {
            row = sheet.getRow(i);
            // Просматривается есть ли данные в ряду.
            if (row == null)
                continue;
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.getLastCellNum() < 3)
                continue;
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0);
            tiUUID = cell.getStringCellValue();
            if (tiUUID.length() == 0)
                continue;

            // Создаётся новый объект класса.
            timeInterval = new CTimeInterval();
            id = UUID.fromString(tiUUID);
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            timeInterval.setId(id);
            cell = row.getCell(1);
            name = cell.getStringCellValue();
            timeInterval.setName(name);
            cell = row.getCell(2);
            vr = cell.getStringCellValue();
            timeInterval.setVr(vr);
            // Объект помещается в карточку timeintervals.
            timeintervals.put(id, timeInterval);
        }
    }
    /******************************************************
     * Загрузка информации о дате из электронной таблицы. *
     * Результат в карте times.                           *
     * @param wb - рабочая книга с данными.               *
     ******************************************************/
    private static void loadTime(XSSFWorkbook wb) {
        // Берётся 1(считая их от 0) лист(таблица) из файла .xlsx.
        Sheet sheet = wb.getSheetAt(1);

        Row row;
        Cell cell;
        int i;
        int nRows = sheet.getLastRowNum();
        String tUUID;
        UUID id;
        CTime time;
        LocalDate date;
        // Перебираются строки 2 таблицы в файле .xlsx.
        for (i = 0; i <= nRows; i++) {
            row = sheet.getRow(i);
            // Просматривается есть ли данные в ряду.
            if (row == null)
                continue;
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.getLastCellNum() < 2)
                continue;
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0);
            tUUID = cell.getStringCellValue();
            if (tUUID.length() == 0)
                continue;

            // Создаётся новый объект класса.
            time = new CTime();
            id = UUID.fromString(tUUID);
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            time.setId(id);
            cell = row.getCell(1);
            date = cell.getLocalDateTimeCellValue().toLocalDate();
            time.setDate(date);
            // Объект помещается в карточку times.
            times.put(id, time);
        }
    }
    /*******************************************************
     * Загрузка информации об еде из электронной таблицы.  *
     * Результат в карте foods.                            *
     * @param wb - рабочая книга с данными.                *
     *******************************************************/
    private static void loadFood(XSSFWorkbook wb) {
        // Берётся 3(считая их от 0) лист(таблица) из файла .xlsx.
        Sheet sheet = wb.getSheetAt(3);

        Row row;
        Cell cell;
        int i;
        int nRows = sheet.getLastRowNum();
        String fUUID, name;
        double kalors;
        UUID id;
        CFood food;
        // Перебираются строки 3 таблицы в файле .xlsx.
        for (i = 0; i < nRows; i++) {
            row = sheet.getRow(i);
            // Просматривается есть ли данные в ряду.
            if (row == null)
                continue;
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.getLastCellNum() < 5)
                continue;
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0);
            fUUID = cell.getStringCellValue();
            if (fUUID.length() == 0)
                continue;

            // Создаётся новый объект класса.
            food = new CFood();
            id = UUID.fromString(fUUID);
            // Заполняется в объекте UUID из 1 ячейки в сторе.
            food.setId(id);
            // Заполняется в объекте Название из 2 ячейки в строке.
            cell = row.getCell(1);
            name = cell.getStringCellValue();
            food.setName(name);
            // Заполняется в объекте Калории из 3 ячейки в строке.
            cell = row.getCell(2);
            kalors = cell.getNumericCellValue();
            food.setCal(kalors);
            // Объект помещается в карточку foods.
            foods.put(id, food);
        }
    }
    /************************************************************
     * Загрузка информации о связях еды из электронной таблицы. *
     * Результат в объектах карт foods, timeintervals, times.   *
     * @param wb - рабочая книга с данными.                     *
     ************************************************************/
    private static void loadFoodRelations(XSSFWorkbook wb) {
        // Берётся 3(считая их он 0) лист(таблица) из файла .xlsx.
        Sheet sheet = wb.getSheetAt(3);

        Row row;
        Cell cell;
        int i;
        int nRows = sheet.getLastRowNum();
        String sId, sTimeIntervalId, sFoodId, sTimeId;
        UUID id, timeintervalId, foodId, timeId;
        CFood food;
        CTimeInterval timeinterval;
        CTime time;
        // Перебираются строки 3 таблицы в файле .xlsx.
        for (i = 0; i < nRows; i++) {
            row = sheet.getRow(i);
            // Просматривается есть ли данные в ряду.
            if (row == null)
                continue;
            // Просматривается все ли заполнены ячейки в ряду.
            if (row.getLastCellNum() < 5)
                continue;
            // Достаётся UUID из 1 ячейки.
            cell = row.getCell(0);
            sId = cell.getStringCellValue();
            if (sId.length() == 0)
                continue;

            // Достаётся объект класса CFOOD по UUID из карточки foods
            id = UUID.fromString(sId);
            food = foods.get(id);

            // Связываем еду с датой, в дате в список добавляем еду.
            cell = row.getCell(3); // Берём id даты из таблицы еды.
            sTimeId = cell.getStringCellValue();
            timeId = UUID.fromString(sTimeId);
            time = times.get(timeId);
            if (time!=null) {
                food.setTime(time);
                time.getFoods().add(food);
            }
            // Связываем еду с временным интервалом.
            cell = row.getCell(4); // Берём id интервала из таблицы еды.
            sTimeIntervalId = cell.getStringCellValue();
            timeintervalId = UUID.fromString(sTimeIntervalId);
            timeinterval = timeintervals.get(timeintervalId);
            if (timeinterval!=null) {
                food.setTimeinterval(timeinterval);
            }

        }
    }
    /***************************************************************************
     * Первый этап загрузки данных из электронной таблицы - создание объектов. *
     * Результат в картах foods, timeintervals, times.                         *
     * @param wb - рабочая книга с данными.                                    *
     ***************************************************************************/
    private static void loadStage1(XSSFWorkbook wb) {
        loadTime(wb);
        loadFood(wb);
        loadTimeInterval(wb);
        return;
    }
    /*****************************************************************************************
     * Второй этап загрузки данных из электронной таблицы - создание связей между объектами. *
     * Результат в картах foods, timeintervals, times.                                       *
     * @param wb - рабочая книга с данными.                                                  *
     *****************************************************************************************/
    private static void loadStage2(XSSFWorkbook wb) {
        loadFoodRelations(wb);
        return;
    }
    /***************************************************
     * Загрузка данных из электронной таблицы.         *
     * Результат в картах foods, timeintervals, times. *
     ***************************************************/
    private static void load() {
        try (XSSFWorkbook wb = openExcel()) {
            if (wb==null)
                return;
            loadStage1(wb);
            loadStage2(wb);
        }
        catch(Exception e) {
            System.out.println("Формат файла не поддерживается!");
            e.printStackTrace();
            return;
        }
    }
    /******************************
     * Вывод данных в консоль.    *
     ******************************/
    private static void outData() {
        for (Map.Entry<UUID, CTime> entry : times.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
    /**************************************************************
     * Создание заголовка для файла-отчёта.                       *
     * @param document - заготовка файла-отчёта.                  *
     * @param time - дата, по которой будет создавваться таблица. *
     **************************************************************/
    private static void createTitle(
            XWPFDocument document,
            CTime time
    ) {
        // Создание параграфа.
        XWPFParagraph par = document.createParagraph();
        // Центрирование параграфа.
        par.setAlignment(ParagraphAlignment.CENTER);

        // Создание куска текста.
        XWPFRun run = par.createRun();
        // Установка содержимого текста.
        run.setText("Расписание приема пищи на %s".formatted(time.getDate()));
        // Жирность.
        run.setBold(true);
        // Шрифт.
        run.setFontFamily("Times New Roman");
        // Размер шрифта.
        run.setFontSize(20);
        return;
    }
    /**************************************************************
     * Создание верхней ячейки таблицы в файле-отчёте.            *
     * @param row - положение строки(от 0).                       *
     * @param pos - положение столбца(от 0).                      *
     * @param text - текст, который будет находится в ячейке.     *
     **************************************************************/
    private static void createHeaderCell(
            XWPFTableRow row,
            int pos,
            String text) {
        XWPFParagraph par;
        XWPFRun run;
        XWPFTableCell cell;
        cell = row.getCell(pos);
        par = cell.addParagraph();
        par.setAlignment(ParagraphAlignment.CENTER);
        par.setVerticalAlignment(TextAlignment.BOTTOM);
        run = par.createRun();
        // Установка содержимого текста.
        run.setText(text);
        // Жирность.
        run.setBold(true);
        // Шрифт.
        run.setFontFamily("Times New Roman");
        // Размер шрифта.
        run.setFontSize(14);
    }

    /**************************************************************
     * Создание таблицы с едой в файле-отчёте.                    *
     * @param document - заготовка файла-отчёта.                  *
     * @param time - дата, по которой будет создавваться таблица. *
     **************************************************************/
    private static void createTable(
            XWPFDocument document,
            CTime time
    ) {
        XWPFTable table = document.createTable(1,3);
        table.setWidth(5*1900);

        // Создаём верх таблицы, первую строку
        XWPFTableRow row = table.getRow(0);
        createHeaderCell(row, 0, "Интервал");
        createHeaderCell(row, 1, "Еда");
        createHeaderCell(row, 2, "Калории");
        LocalDate date;
        String sDate;
        // Создание строк с информацией по дате.
        for (CFood food : time.getFoods()) {
            row = table.createRow();
            row.getCell(0).setText(food.getTimeinterval().getName()); // Добавляем интервал в первый столбец.
            row.getCell(1).setText(food.getName()); // Добавляем название еды во второй столбец.
            row.getCell(2).setText(String.valueOf(food.getCal())); // Добавляем каллории еды в третий столбец.
        }
        // Прокраска границ таблицы. Необходимость надо проверять в MS Word.
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000");
    }
    /**************************************************************
     * Создание дневника еды в формате электронного документа.    *
     * @param time - дата, по которой будет создавваться таблица. *
     **************************************************************/
    private static void createReport(CTime time) {
        try(XWPFDocument document = new XWPFDocument()) {
            // Заголовок.
            createTitle(document, time);
            // Таблица с датой.
            createTable(document, time);
            // Сохранение информации в файл.
            File report = new File("output.docx");
            try(FileOutputStream fos = new FileOutputStream(report)) {
                document.write(fos);
            }
            catch(IOException e) {
                System.out.println("Ошибка при записи файла на диск!");
                e.printStackTrace();
            }

        }
        catch(IOException e)
        {
            System.out.println("Ошибка при сохранении данных в файл!");
            e.printStackTrace();
        }
    }
    //
    /********************************************************
     * Основная функция программы.                          *
     * @param args - параметры вызова программы из консоли. *
     ********************************************************/
    public static void main(String[] args) {
        // Загрузка.
        load();
        // Фильтрация данных. Здесь возвращается первый попавшийся.
        CTime t;
        Iterator<CTime> it = times.values().iterator();
        if (it.hasNext()) {
            t = it.next();
            // Построение карточки по выбранной дате.
            createReport(t);
        }
        else {
            System.out.println("В дневнике нет записей!");
        }
        // Вывод данных в консоль для проверки.
        outData();
    }
}