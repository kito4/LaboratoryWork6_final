package kito.lab5.server;

import kito.lab5.server.csv_parser.CSVReader;
import kito.lab5.server.utils.TextSender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public final class Server {


    CSVReader collectionFileReader;

    public static void main(String[] args) {
        Server server = new Server();
//        TextSender.changePrintStream(server.objectOutputStream);      // removed 1609
//        Application application = new Application(server.is);            // TODO 0709 REPLACED  .objectInputStream);
//        System.out.println(System.getenv("HUMAN_INFO"));
//        application.launchApplication();
    }
    public Server(){
        collectionFileReader = new CSVReader();
        setUpConnection();
    }
    public void setUpConnection(){
        ServerSocket ss=null;
        try {

            collectionFileReader.initializeFile("humans.csv");               // TODO 0709 Config.getFilePath());
            collectionFileReader.parseFile();
            Config.getCollectionManager().fillWithArray(collectionFileReader.getInfoFromFile());

            ss = new ServerSocket(4550);
//            Socket s= ss.accept();

            while (true) {
                new ConnectionManager(ss.accept());
            }

//            TextSender.os = os;     // TODO 0709 added



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Файл: " + Config.getFilePath() + " не найден");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Пожалуйста проинциализируйте системную переменную HUMAN_INFO, " +
                    "содержащую путь до файла с информацией о коллекции");
    }
    }
}
