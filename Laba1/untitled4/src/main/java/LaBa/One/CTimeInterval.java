package LaBa.One;

import java.util.UUID;

/******************************************
 * Класс временной интервал.              *
 * @autor Макарова П.Ф. ПМИ-2 21.12.2022. *
 ******************************************/


public class CTimeInterval {

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

    /**************************
     * Временной промежуток.  *
     **************************/

    private String vr;

    public String getVr() {
        return vr;
    }

    public void setVr(String vr) {
        this.vr = vr;
    }

    /**************************
     * Вывод записи.          *
     **************************/

    @Override
    public String toString() {
        return "Интервал: " + name + ";";
    }
}
