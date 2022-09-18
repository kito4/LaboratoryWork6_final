package kito.lab5.server.user_command_line.Commands;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.HumanInfoInput;
import kito.lab5.server.user_command_line.SuccessMessage;
import kito.lab5.server.utils.TextSender;

import java.util.Arrays;

public class Update extends AbstractCommand {

    public Update() {
        super("update", "Обновить элемент коллекции по его ID, принимает на вход [ID, Имя, наличие героизма(true/false), наличие зубочистки(true/false), скорость удара,минуты ожидания     ]", 6);
    }

    @Override
    public Object execute(String[] args, TextSender sender) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            try {
                int id = Integer.parseInt(args[0]);
//                HumanInfoInput humanInfoInput = new HumanInfoInput(Config.getCollectionManager().getHumanById(id), Arrays.copyOfRange(args, 1, args.length));
//                humanInfoInput.inputHuman();
//
//
//                Config.getCollectionManager().setHumanById(id, humanInfoInput.getNewHumanToInput());
                return new SuccessMessage("Объект успешно добавлен в коллекцию");
            } catch (IllegalArgumentException e) {
                return new ErrorMessage(e.getMessage());
            }
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }

}
