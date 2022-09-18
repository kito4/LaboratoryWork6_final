package kito.lab5.server.user_command_line.Commands;



import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.HumanInfoInput;
import kito.lab5.server.user_command_line.SuccessMessage;
import kito.lab5.server.utils.TextSender;

public class add_if_min extends AbstractCommand {

    public add_if_min() {
        super("add_if_max", "Добавить элемент в коллекцию, принимает на вход [Имя, наличие героизма(true/false), наличие зубочистки(true/false), скорость удара,ожидание]", 5);
    }

    @Override
    public Object execute(String[] args, TextSender sender) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            try {
                HumanInfoInput humanInfoInput = new HumanInfoInput(args);
                humanInfoInput.inputHuman();

                System.out.println(Config.getCollectionManager().returnDescending().get(0).getImpactSpeed());
                if (humanInfoInput.getNewHumanToInput().getImpactSpeed()<Config.getCollectionManager().returnDescending().get(0).getImpactSpeed()) {
                    Config.getCollectionManager().addHuman(humanInfoInput.getNewHumanToInput());
                }else {
                    return new SuccessMessage("Объект не добавлен в коллекцию так как не наибольшая скорость удара");
                }
                return new SuccessMessage("Объект успешно добавлен в коллекцию");
            } catch (IllegalArgumentException e) {
                return new ErrorMessage(e.getMessage());
            }
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }
}

