package kito.lab5.server.user_command_line.Commands;

import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractCommand;
import kito.lab5.server.csv_parser.CSVSaver;
import kito.lab5.server.user_command_line.ErrorMessage;
import kito.lab5.server.user_command_line.SuccessMessage;

import java.io.IOException;

public class Save extends AbstractCommand {
    
    private final String filePath;

    public Save(String filePath) {
        super("save", "Сохранить коллекцию в файл", 0);
        this.filePath = filePath;
    }

    @Override
    public Object execute(String[] args) {
        if (args.length == getAMOUNT_OF_ARGS()) {
            try {
                CSVSaver saver = new CSVSaver(this.filePath);
                saver.saveToFile(Config.getCollectionManager().getArrayOfInfo());
                return new SuccessMessage("Коллекция успешно сохранена");
            } catch (IOException e) {
                return new ErrorMessage("Файл не найден");
            }
        } else {
            return new ErrorMessage("Передано неправильное количество аргументов");
        }
    }
}
