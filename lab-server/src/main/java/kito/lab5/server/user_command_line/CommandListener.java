package kito.lab5.server.user_command_line;

import com.sun.net.httpserver.Authenticator;
import kito.lab5.common.util.Request;
import kito.lab5.common.util.Serializer;
import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractMessage;
import kito.lab5.server.utils.SmartSplitter;
import kito.lab5.server.utils.TextSender;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс отвечающий за работу с пользователем в интерактивном режиме
 */
public class CommandListener {

    private boolean isRunning;
    private final InputStream commandsInputStream;                  // TODO 0709 REPLACED BY INPUTSTREAM
    private TextSender sender;

    /**
     * Конструктор
     */
    public CommandListener(InputStream inputStream, TextSender sender) {                   // TODO 0709 REPLACED BY INPUTSTREAM
//        TextSender.printText("Добро пожаловать в интерактивный режим работы с коллекцией, " +
//                "введите help, чтобы узнать информацию о доступных командах"); //   TODO edit 0709
        commandsInputStream = inputStream;
        this.sender = sender;
    }

    /**
     * Метод, читающий команды  до тех пор, пока не возникнет команда exit
     */
    public void readCommands() {
        isRunning = true;
        Scanner scanner = new Scanner(commandsInputStream);         // TODO ACTUALLY 0709 remove useless scanner
        while (isRunning) {
            try {

                byte[] objectBytes = new byte[4096];
                commandsInputStream.read(objectBytes);

                        // TODO 0709: below lines removed
//                    ByteArrayOutputStream buffer = new ByteArrayOutputStream(); // TODO
//                    int toRead;
//                    byte[] bytes = new byte[4096];
//                    while ((toRead = commandsInputStream.read(bytes,0,bytes.length)) != -1) {
//                        buffer.write(bytes,0,toRead);
//                    }
//                    byte[] objectBytes = buffer.toByteArray();
                Request request = Serializer.deSerializeRequest(objectBytes);
                System.out.println(request);
                if (request.getCommandNameAndArguments().equals("addfinal")) {
                    Config.getCollectionManager().addHuman(request.getHuman());
                    sender.sendMessage("Человек успешно добавлен!");
                } else {
                    String line = request.getCommandNameAndArguments();
                    System.out.println(line);
//                if ("exit".equals(line)) {
//                    isRunning = false;
//                    continue;
//                }

                    String[] inputString = SmartSplitter.smartSplit(line).toArray(new String[0]);
                    String commandName = inputString[0].toLowerCase();
                    String[] commandArgs = Arrays.copyOfRange(inputString, 1, inputString.length);
                    sender.sendMessage(((AbstractMessage) Config.getCommandManager().execute(commandName.toLowerCase(), commandArgs, sender)).getMessage());
                }
            } catch (NoSuchElementException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        scanner.close();
    }

}
