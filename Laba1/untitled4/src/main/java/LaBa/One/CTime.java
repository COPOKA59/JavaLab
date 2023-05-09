package LaBa.One;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.*;

/******************************************
 * Класс дата.                            *
 * @autor Макарова П.Ф. ПМИ-2 21.12.2022. *
 ******************************************/


public class CTime {

    /**************************
     * Идендификатор.         *
     **************************/


    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**************************
     * Дата записи.           *
     **************************/

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate (LocalDate date) {
        this.date = date;
    }

    /**********************************
     * День недели.                   *
     * Вычисления дня недели по дате. *
     **********************************/
    private String day; //

    // В интеренете присутствует вывод дня недели английскими буквами и на некоторые другие языки.
    // Вывод на русский я не нашла, поэтому сделала через цикл и номер дня недели
    public String getDay() {
        DayOfWeek dayNumber = date.getDayOfWeek();
        if (dayNumber.getValue() == 1) {
            day = "Пн";
        } else {if (dayNumber.getValue() == 2) {
            day = "Вт";
        } else {if (dayNumber.getValue() == 3) {
            day = "Ср";
        } else {if (dayNumber.getValue() == 4) {
            day = "Чт";
        } else {if (dayNumber.getValue() == 5) {
            day = "Пт";
        } else {if (dayNumber.getValue() == 6) {
            day = "Сб";
        } else {if (dayNumber.getValue() == 7) {
            day = "Вс";
        }}}}}}};
        return day;
    }

    public void setDay(String day) {
        this.day = day;}

    /**************************
     * Вывод записи.          *
     **************************/

    @Override
    public String toString() {
        System.out.println((date == null)? "Нет записи" : "Дата: " + date + ";" + " День: "+ getDay() + ";");
        if (foods.size() != 0){
            int i;
            for (i = 0; i < foods.size(); ) {
                System.out.println(foods.remove(i));
            }}
        else{
            System.out.println("Нет записей");
        };
        return "";
    }

    /***********************************************
     * Список блюд, съеденных в определённый день. *
     ***********************************************/

    private List<CFood> foods;

    public List<CFood> getFoods() {
        return foods;
    }

    public void setFoods(List<CFood> foods) {
        this.foods = foods;
    };

    /**************************
     * Конструктор.           *
     **************************/
    public CTime() {
        foods = new ArrayList<>();
    }
}
