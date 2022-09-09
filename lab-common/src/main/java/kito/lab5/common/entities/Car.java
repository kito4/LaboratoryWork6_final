package kito.lab5.common.entities;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Класс описывающий объект машина
 */
public class Car implements Serializable {
    @NotNull
    private String carname;
    private boolean cool; //Поле может быть null

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public boolean isCool() {
        return cool;
    }

    /**
     * @return Крутость машины
     */
    public boolean getCool() {
        return cool;
    }

    /**
     * Метод, позволяющий задать крутость машины
     * @param newCoolness Новая крутость машины
     */
    public void setCool(boolean newCoolness) {
        this.cool = newCoolness;
    }

    @Override
    public String toString() {
        return "CAR COOL: " + this.getCool();
    }
}
