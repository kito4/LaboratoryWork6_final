package kito.lab5.server;

import kito.lab5.server.csv_parser.CSVReader;
import kito.lab5.server.user_command_line.CommandListener;
import kito.lab5.server.utils.TextSender;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class Application {

    CommandListener commandListener;

    public Application(InputStream dis, TextSender sender, CSVReader reader) {               // TODO 0709 changed to inputstream
        commandListener = new CommandListener(dis, sender);
        commandListener.readCommands(reader);
    }

}
