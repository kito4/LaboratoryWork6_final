package kito.lab5.server.user_command_line.Commands;


import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.SuccessMessage;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class History extends AbstractCommand {

    public History() {
        super("history", "Вывести информацию по последним 10 исполненным командам", 0);
    }

    @Override
    public Object execute(String[] args) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            ArrayDeque<AbstractCommand> listToReturn = Config.getCommandManager().getLastExecutedCommands();
            return new SuccessMessage(listToReturn.stream()
                    .map(AbstractCommand::getName)
                    .collect(Collectors.joining("\n")));
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }
}