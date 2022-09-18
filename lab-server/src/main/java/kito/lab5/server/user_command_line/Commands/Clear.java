package kito.lab5.server.user_command_line.Commands;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.SuccessMessage;
import kito.lab5.server.utils.TextSender;

public class Clear extends AbstractCommand {

    public Clear() {
        super("clear", "Очистить коллекцию", 0);
    }

    @Override
    public Object execute(String[] args, TextSender sender) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            Config.getCollectionManager().clearCollection();
            return new SuccessMessage("Коллекция успешно очищена");
        } else {
            return new ErrorMessage("Передано неверное количество аргументов");
        }
    }
}
