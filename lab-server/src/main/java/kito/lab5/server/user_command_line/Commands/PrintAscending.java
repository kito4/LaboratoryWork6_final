package kito.lab5.server.user_command_line.Commands;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.common.entities.HumanBeing;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.SuccessMessage;

import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending extends AbstractCommand {

    public PrintAscending() {
        super("print_descending", "Вывести коллекциию в порядке убывания", 0);
    }

    @Override
    public Object execute(String[] args) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            List<HumanBeing> listToReturn = Config.getCollectionManager().returnDescending()    ;
            return new SuccessMessage(listToReturn.stream()
                    .map(HumanBeing::toString)
                    .collect(Collectors.joining("\n")));
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }
}
