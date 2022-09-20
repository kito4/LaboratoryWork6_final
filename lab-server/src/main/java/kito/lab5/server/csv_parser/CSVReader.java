package kito.lab5.server.csv_parser;

import kito.lab5.server.abstractions.AbstractFileReader;
import kito.lab5.common.entities.HumanBeing;
import kito.lab5.server.utils.HumanValidator;
import kito.lab5.server.utils.StringToTypeConverter;
import kito.lab5.server.utils.TextSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс, реализующий чтение данных из CSV файла, наследует абстрактный класс AbstractFileReader
 */
public class CSVReader extends AbstractFileReader {

    private Scanner scannerOfFile;
    private String[] parameters = {"id","name","X","Y","creationDate","hasToothpick","impactSpeed","soundtrackName",};

    // id,name,X,Y,creationDate,hasToothpick,impactSpeed,soundtrackName,MinutesOfWaiting,weaponType,cool,carname,RealHero,creator
    private final ArrayList<String> peopleStrings = new ArrayList<>();
    private final ArrayList<HashMap<String, String>> peopleInfo = new ArrayList<>();
    private final ArrayList<HumanBeing> humanArray = new ArrayList<>();
    private final Field[] humanBeingFields;

    /**
     * Конструктор класса CSVReader, при инициализации с помощью рефлексии задает значение humanBeingFields
     */
    public CSVReader() {
        humanBeingFields = HumanBeing.class.getDeclaredFields();
    }

    /**
     * Метод, возвращающий массив прочитанных элементов коллекции из файла
     */
    @Override
    public ArrayList<HumanBeing> getInfoFromFile() {
        return humanArray;
    }

    /**
     * Метод, заполняющий массив элементов коллекции, читая информацию о них из файла
     */
    @Override
    public void parseFile() {
//        readPeople();
        readStringsFromTable();
        for (HashMap<String, String> humanInfo : peopleInfo) {
            HumanBeing newHuman = createHuman(humanInfo);
//            if (HumanValidator.validateHuman(newHuman)) {     // TODO HumanValidator
                humanArray.add(newHuman);
//            } else {
//                TextSender.sendError("Ошибка при валидации данных, прочитанных из файла");
//                System.exit(2);
//            }
        }
    }

    /**
     * Метод, инициализирующий файл для чтения, получающий в качестве параметра имя этого файла
     * @param fileName имя файла
     * @throws FileNotFoundException
     */
    @Override
    public void initializeFile(String fileName) throws FileNotFoundException {
        File infoFile = new File("C:\\Users\\nikit\\OneDrive\\Рабочий стол\\LaboratoryWork5_just_rename_folder-main_0907\\lab-server\\humans.csv");        // TODO 0709 added file
        scannerOfFile = new Scanner(infoFile);
    }

    private HumanBeing createHuman(HashMap<String, String> humanInfo) {
        HumanBeing newHuman = new HumanBeing(true);
        for (Map.Entry<String, String> element : humanInfo.entrySet()) {
            for (Field field: humanBeingFields) {
                Class<?> cl = field.getType();
                if (field.getName().equals(element.getKey())) {
                    try {

                        Method setter = HumanBeing.class.getDeclaredMethod("set"
                                + field.getName().substring(0, 1).toUpperCase()
                                + field.getName().substring(1), field.getType());

                        setter.invoke(newHuman, ("null".equals(element.getValue()) ? null : StringToTypeConverter.toObject(field.getType(), element.getValue())));


                    } catch (Exception e) {
                        e.printStackTrace();

                        System.out.println("Ошибка при чтении файла");
                        System.exit(2);
                    }
                } else {
                    Field[] innerFields = cl.getDeclaredFields();
                    for (Field innerField : innerFields) {
                        if (innerField.getName().equals(element.getKey())) {
                            try {

                                Method innerSetter = cl.getDeclaredMethod("set"
                                        + innerField.getName().substring(0, 1).toUpperCase()
                                        + innerField.getName().substring(1), innerField.getType());

                                Method getter = HumanBeing.class.getDeclaredMethod("get"
                                        + cl.getSimpleName().substring(0, 1).toUpperCase()
                                        + cl.getSimpleName().substring(1));
                                Method outerSetter = HumanBeing.class.getDeclaredMethod("set"
                                        + cl.getSimpleName().substring(0, 1).toUpperCase()
                                        + cl.getSimpleName().substring(1), cl);
                                if ("".equals(element.getValue())) {

                                    outerSetter.invoke(newHuman, (Object) null);
                                } else if (getter.invoke(newHuman) != null) {

                                    innerSetter.invoke(getter.invoke(newHuman), ("null".equals(element.getValue()) ? null : StringToTypeConverter.toObject(innerField.getType(), element.getValue())));//раскомментировал
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Ошибка при чтении файла");
                                System.exit(2);
                            }
                        }
                    }
                }
            }
        }
        return newHuman;
    }

    private void readStringsFromTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs");
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM s334582;");
            ResultSet table = statement.executeQuery();

            HashMap<String, String> newHuman;

            peopleInfo.clear();
            do {
                newHuman = new HashMap<>();
                newHuman.put("name", table.getString("name"));
                newHuman.put("x", table.getString("x"));
                newHuman.put("y", table.getString("y"));
                newHuman.put("creationDate", table.getString("creationDate"));
                newHuman.put("hasToothpick", table.getString("hasToothpick"));
                newHuman.put("impactSpeed", table.getString("impactSpeed"));
                newHuman.put("soundtrackName", table.getString("soundtrackName"));
                newHuman.put("minutesOfWaiting", table.getString("minutesOfWaiting"));
                newHuman.put("weaponType", table.getString("weaponType"));
                newHuman.put("cool", table.getString("cool"));
                newHuman.put("carName", table.getString("carName"));
                newHuman.put("realHero", table.getString("realHero"));
                newHuman.put("creator", table.getString("creator"));

//            String[] humanInfo = peopleString.split(",", -1);     // TODO 1509 deprecated
//
//            for (int j = 0; j < parameters.length; j++) {
//                newHuman.put(parameters[j], humanInfo[j]);
//            }

                peopleInfo.add(newHuman);
            } while (table.next());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // name - Anton
        // hasCar - false
        // cool - true


                // id,name,X,Y,creationDate,hasToothpick,impactSpeed,soundtrackName,MinutesOfWaiting,weaponType,cool,carname,RealHero,creator
                // ..., ..., ...
                // ..., ..., ...


//        ArrayList<String> stringsFromFile = new ArrayList<>();
//        while (scannerOfFile.hasNextLine()) {
//            stringsFromFile.add(scannerOfFile.nextLine());
//        }
//        parameters = stringsFromFile.get(0).split(",");
//        for (int i = 1; i < stringsFromFile.size(); i++) {
//            peopleStrings.add(stringsFromFile.get(i));
//        }
    }

//    private void readPeople() {
//        readStringsFromFile();
//        for (String peopleString : peopleStrings) {
//            HashMap<String, String> newHuman = new HashMap<>();
//            String[] humanInfo = peopleString.split(",", -1);
//            for (int j = 0; j < parameters.length; j++) {
//                newHuman.put(parameters[j], humanInfo[j]);
//            }
//            peopleInfo.add(newHuman);
//        }
//    }
}
