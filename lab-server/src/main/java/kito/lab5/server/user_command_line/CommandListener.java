package kito.lab5.server.user_command_line;

import com.sun.net.httpserver.Authenticator;
import kito.lab5.common.entities.HumanBeing;
import kito.lab5.common.util.Request;
import kito.lab5.common.util.Serializer;
import kito.lab5.server.CollectionManager;
import kito.lab5.server.Config;
import kito.lab5.server.abstractions.AbstractMessage;
import kito.lab5.server.csv_parser.CSVReader;
import kito.lab5.server.utils.SmartSplitter;
import kito.lab5.server.utils.TextSender;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
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
    public void readCommands(CSVReader reader) {
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

                // TODO MANDATORY CHECK USER AND PASS

                if (request.getCommandNameAndArguments().equals("addfinal")) {
//                    Config.getCollectionManager().addHuman(request.getHuman());

                    Connection conn = null;
                    try {
//            class.forName("jdbc")
                        Random r = new Random();        // TODO remove
                        conn = DriverManager.getConnection("jdbc:postgresql:lab-server//humans.mkd");       // TODO CHANGE
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO studs VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setString(1, "'" + r.nextInt(1,10000) + "'");      // TODO CHANGE RAndom
                        ps.setString(2, "'" + request.getHuman().getName() + "'");
                        ps.setString(3, "'" + request.getHuman().getCoordinates().getX() + "'");
                        ps.setString(4, "'" + request.getHuman().getCoordinates().getY() + "'");
                        ps.setString(5, "'" + request.getHuman().getCreationDate() + "'");
                        ps.setString(6, "'" + request.getHuman().isHasToothpick() + "'");
                        ps.setString(7, "'" + request.getHuman().getImpactSpeed() + "'");
                        ps.setString(8, "'" + request.getHuman().getSoundtrackName() + "'");
                        ps.setString(9, "'" + request.getHuman().getMinutesOfWaiting() + "'");
                        ps.setString(10, "'" + request.getHuman().getWeaponType() + "'");
                        ps.setString(11, "'" + request.getHuman().getCar().isCool() + "'");
                        ps.setString(12, "'" + request.getHuman().getCar().getCarname() + "'");
                        ps.setString(13, "'" + request.getHuman().getRealHero() + "'");
                        ps.execute();

                        reader.parseFile();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // id,name,X,Y,creationDate,hasToothpick,impactSpeed,soundtrackName,MinutesOfWaiting,weaponType,cool,carname,RealHero,creator


                        sender.sendMessage("Человек успешно добавлен!");
                } else if (request.getCommandNameAndArguments().equals("updatefinal")) {
                    Connection conn = null;
                    try {
//            class.forName("jdbc")
                        conn = DriverManager.getConnection("jdbc:postgresql:lab-server//humans.mkd");
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO studs (name,x,y,creationDate,hasToothpick,impactSpeed, " +
                                "soundtrackName,minutesOfWaiting,weaponType,cool,carName,realHero) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) WHERE id = " + request.getHuman().getId());
                        ps.setString(2, "'" + request.getHuman().getName() + "'");
                        ps.setString(3, "'" + request.getHuman().getCoordinates().getX() + "'");
                        ps.setString(4, "'" + request.getHuman().getCoordinates().getY() + "'");
                        ps.setString(5, "'" + request.getHuman().getCreationDate() + "'");
                        ps.setString(6, "'" + request.getHuman().isHasToothpick() + "'");
                        ps.setString(7, "'" + request.getHuman().getImpactSpeed() + "'");
                        ps.setString(8, "'" + request.getHuman().getSoundtrackName() + "'");
                        ps.setString(9, "'" + request.getHuman().getMinutesOfWaiting() + "'");
                        ps.setString(10, "'" + request.getHuman().getWeaponType() + "'");
                        ps.setString(11, "'" + request.getHuman().getCar().isCool() + "'");
                        ps.setString(12, "'" + request.getHuman().getCar().getCarname() + "'");
                        ps.setString(13, "'" + request.getHuman().getRealHero() + "'");
                        ps.execute();

                        reader.parseFile();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
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
