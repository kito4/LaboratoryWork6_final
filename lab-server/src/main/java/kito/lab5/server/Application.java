package kito.lab5.server;

import kito.lab5.server.csv_parser.CSVReader;
import kito.lab5.server.user_command_line.CommandListener;
import kito.lab5.server.utils.TextSender;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Application {

    CSVReader collectionFileReader;
    CommandListener commandListener;

    public Application(InputStream dis) {               // TODO 0709 changed to inputstream
        collectionFileReader = new CSVReader();
        commandListener = new CommandListener(dis);
    }

    public void launchApplication() {
        try {
            collectionFileReader.initializeFile("humans.csv");               // TODO 0709 Config.getFilePath());
            collectionFileReader.parseFile();
            Config.getCollectionManager().fillWithArray(collectionFileReader.getInfoFromFile());
            commandListener.readCommands();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            TextSender.sendError("Файл: " + Config.getFilePath() + " не найден");
        } catch (NullPointerException e) {
            e.printStackTrace();
            TextSender.sendError("Пожалуйста проинциализируйте системную переменную HUMAN_INFO, " +
                    "содержащую путь до файла с информацией о коллекции");
        }
    }
}
