package kito.lab5.server.user_command_line;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.Commands.*;
import kito.lab5.server.utils.TextSender;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Map<String, AbstractCommand> commands = new HashMap<>();
    private final ArrayDeque<AbstractCommand> lastExecutedCommands = new ArrayDeque<>();
    private final int AMOUNT_OF_COMMANDS_TO_SAVE = 10;

    public CommandManager() {
        initMap();
    }

    private void initMap() {
        commands.put("add", new Add());
        commands.put("clear", new Clear());
        commands.put("info", new Info());
        commands.put("print_descending", new PrintAscending());
        commands.put("save", new Save(Config.getFilePath()));
        commands.put("show", new Show());
        commands.put("update", new Update());
        commands.put("help", new Help());
        commands.put("execute_script", new ExecuteScript());
        commands.put("remove_by_id", new RemoveByID());
        commands.put("history", new History());
        commands.put("add_if_max",new add_if_max());
        commands.put("add_if_min", new add_if_min());

    }

    public Object execute(String command, String[] args, TextSender sender) {
        if (commands.containsKey(command)) {
            lastExecutedCommands.addFirst(commands.get(command));
            if (lastExecutedCommands.size() == AMOUNT_OF_COMMANDS_TO_SAVE) {
                lastExecutedCommands.pollLast();
            }
            return commands.get(command).execute(args, sender);
        } else {
            return new ErrorMessage("Такой команды не существует, введите help для получения справки по командам");
        }
    }

    public ArrayDeque<AbstractCommand> getLastExecutedCommands() {
        return lastExecutedCommands;
    }
}
