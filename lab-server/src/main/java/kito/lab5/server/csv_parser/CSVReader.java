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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс, реализующий чтение данных из CSV файла, наследует абстрактный класс AbstractFileReader
 */
public class CSVReader extends AbstractFileReader {

    private Scanner scannerOfFile;
    private String[] parameters;
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
        readPeople();
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

    private void readStringsFromFile() {

        Connection conn = DriverManager.getConnection("studs.mkd")
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM studs");
        ResultSet table = statement.executeQuery();
        String anton = table.getString("x");

        for () {
            HashMap<String, String> newHuman = new HashMap<>();
            newHuman.put(parameters[i],table.getString("name"));
            newHuman.put(parameters[i],table.getString("x"));

            String[] humanInfo = peopleString.split(",", -1);

            for (int j = 0; j < parameters.length; j++) {
                newHuman.put(parameters[j], humanInfo[j]);
            }

            peopleInfo.add(newHuman);
        }



                // name,x,y
                // ..., ..., ...
                // ..., ..., ...


        ArrayList<String> stringsFromFile = new ArrayList<>();
        while (scannerOfFile.hasNextLine()) {
            stringsFromFile.add(scannerOfFile.nextLine());
        }
        parameters = stringsFromFile.get(0).split(",");
        for (int i = 1; i < stringsFromFile.size(); i++) {
            peopleStrings.add(stringsFromFile.get(i));
        }
    }

    private void readPeople() {
        readStringsFromFile();
        for (String peopleString : peopleStrings) {
            HashMap<String, String> newHuman = new HashMap<>();
            String[] humanInfo = peopleString.split(",", -1);
            for (int j = 0; j < parameters.length; j++) {
                newHuman.put(parameters[j], humanInfo[j]);
            }
            peopleInfo.add(newHuman);
        }
    }
}
