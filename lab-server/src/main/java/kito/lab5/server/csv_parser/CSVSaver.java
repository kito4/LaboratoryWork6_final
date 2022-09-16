package kito.lab5.server.csv_parser;

import kito.lab5.common.entities.HumanBeing;
import kito.lab5.server.CollectionManager;
import kito.lab5.server.Config;
import kito.lab5.server.utils.TextSender;

import java.io.FileWriter;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static java.lang.Class.forName;

/**
 * Класс, позволяющий сохранить информацию о коллекции в файл в формате CSV
 */
public class CSVSaver {

    private final String fileName;
    private String firstLineOfFile;

    /**
     * Конструктор класса, задающий поле класса fileName
     * @param fileName
     */
    public CSVSaver(String fileName) {
        this.fileName = fileName;
    }

    private void initializeFile() throws FileNotFoundException {
        File infoFile = new File(fileName);
        Scanner scannerOfFile = new Scanner(infoFile);
        firstLineOfFile = scannerOfFile.nextLine();
    }

    /**
     * Метод, сохраняющий значения всех элементов коллекции в файл
     * @param infoStrings
     * @throws IOException
     */
    public void saveToFile(List<String> infoStrings) throws IOException {
        initializeFile();
        try (FileWriter fw = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(firstLineOfFile + "\n");
            for (String infoString : infoStrings) {
                bw.write(infoString + "\n");
            }
        } catch (IOException e) {
//            TextSender.sendError("Ошибка при работе с файлом");
        }
    }

    public void saveToDb() {

        Connection conn = null;
        try {
//            class.forName("jdbc")
            conn = DriverManager.getConnection("jdbc:postgresql:lab-server//humans.mkd");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO studs VALUES (?,?,?,?,?)");       // TODO 15/09
            for (HumanBeing humanBeing : Config.getCollectionManager().getHumanQueue()) {
                ps.setString(1, "'" + humanBeing.getName() + "'");
                ps.setString(2, "'" + humanBeing.getCoordinates().getX() + "'");
                ps.setString(3, "'" + humanBeing.getName() + "'");
                ps.setString(4, "'" + humanBeing.getName() + "'");
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
