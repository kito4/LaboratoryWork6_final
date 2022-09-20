package kito.lab5.common.util;



import kito.lab5.common.entities.HumanBeing;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {

    String message;
    boolean isObjectNeeded;
    ArrayList<HumanBeing> human = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    private String[] args;
    boolean isUpdate;


    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isObjectNeeded() {
        return isObjectNeeded;
    }

    public void setUpdate() {
        isUpdate = true;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setObjectNeeded(boolean objectNeeded) {
        isObjectNeeded = objectNeeded;
    }

    public void addRouteToSend(HumanBeing human) {
        this.human.add(human);
    }

    public void addStringToSend(String string) {
        this.strings.add(string);
    }

    public ArrayList<HumanBeing> getHuman() {
        return human;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setHuman(ArrayList<HumanBeing> human) {
        this.human = human;
    }

    public String[] getArgs() {
        return args;
    }
}
