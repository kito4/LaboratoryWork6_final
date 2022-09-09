package kito.lab5.server.utils;




import kito.lab5.common.entities.Coordinates;

import java.util.Scanner;

/**
 * Class for getting the fields from the terminal
 */

public class ScannerFieldsGetter implements FieldsGetterInterface {
    Scanner scanner;

    public ScannerFieldsGetter(Scanner sc) {
        this.scanner = sc;
    }

    @Override
    public Coordinates getCoordinates() {
        System.out.println("Введите координаты x (Float) и y (long)");
        while (true) {
            try {
                String[] splittedCoordinates = scanner.nextLine().split(" ");
                if (splittedCoordinates.length == 2) {
                    return new Coordinates();
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("формат данных не соотвествует требованиям!");
            }
        }
    }





    public float getDistance() {
        System.out.println("Введите дистанцию (float)");
        while (true) {
            try {
                String[] splittedDistance = scanner.nextLine().split(" ");
                if (splittedDistance.length == 1) {
                    if (Float.parseFloat(splittedDistance[0]) > 1) {
                        return Float.parseFloat(splittedDistance[0]);
                    } else throw new NumberFormatException();
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("формат данных не соотвествует требованиям!");
            }
        }
    }

    public String getName() {
        System.out.println("Введите имя");
        while (true) {
            String name = scanner.nextLine();
            if (!name.equals("\\s+") && !name.equals("")) {
                return name;
            } else {
                System.out.println("Имя должно содержать хотя бы один символ");
            }
        }
    }
}
