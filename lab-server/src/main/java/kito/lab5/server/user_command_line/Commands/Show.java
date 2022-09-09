package kito.lab5.server.user_command_line.Commands;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.SuccessMessage;

public class Show extends AbstractCommand {

    public Show() {
        super("show", "Вывести все элементы коллекции", 0);
    }
    @Override
    public Object execute(String[] args) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            return new SuccessMessage(Config.getCollectionManager().getStringForShowing());
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }
}
