package LaBa.One;

import java.util.UUID;

/******************************************
 * Класс блюдо(еда).                      *
 * @autor Макарова П.Ф. ПМИ-2 21.12.2022. *
 ******************************************/


public class CFood {

    /***************************
     * Идендификатор.          *
     ***************************/

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /***************************
     * Название.               *
     ***************************/

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /***************************
     * Калории.                *
     ***************************/

    private double cal;

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    /***************************
     * Вывод записи.           *
     ***************************/

    @Override
    public String toString() {
        return ((name==null) ? "Нет записи" : timeinterval + " Блюдо - " + name + "; Калории: " + cal);
    }

    /*****************************************************
     * Дата, когда еда записна в дневнике(т.е. съедена). *
     *****************************************************/
    private CTime time;

    public CTime getTime() {
        return time;
    }

    public void setTime(CTime time) {
        this.time = time;
    }

    /*******************************************************************
     * Интервал времени, когда еда зааписана в дневнике(т.е. съедена). *
     *******************************************************************/
    private CTimeInterval timeinterval;

    public CTimeInterval getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(CTimeInterval timeinterval) {
        this.timeinterval = timeinterval;
    }
}
