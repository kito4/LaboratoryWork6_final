package kito.lab5.common.util;



import kito.lab5.common.entities.HumanBeing;

import java.io.Serializable;

public class Request implements Serializable {

    private String commandNameAndArguments;
    private HumanBeing human;

    public String getCommandNameAndArguments() {
        return commandNameAndArguments;
    }

    public void setCommandNameAndArguments(String commandNameAndArguments) {
        this.commandNameAndArguments = commandNameAndArguments;
    }

    public HumanBeing getHuman() {
        return human;
    }

    public void setHuman(HumanBeing human) {
        this.human = human;
    }
}
