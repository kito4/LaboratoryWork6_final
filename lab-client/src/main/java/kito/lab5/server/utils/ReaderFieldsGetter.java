package kito.lab5.server.utils;


import kito.lab5.server.exceptions.EndOfFileException;
import kito.lab5.common.entities.Coordinates;

/**
 * class for getting the fields from the file
 */

public class ReaderFieldsGetter implements FieldsGetterInterface {

    private DataReader reader;

    public ReaderFieldsGetter(DataReader reader) {
        this.reader = reader;
    }

    @Override
    public Coordinates getCoordinates() throws EndOfFileException {
        while (true) {
            try {
                String coordinates = reader.readline();

                if (coordinates == null) {
                    throw new EndOfFileException();
                }

                String[] splittedCoordinates = coordinates.split(" ");

                if (splittedCoordinates.length == 2) {
                    return new Coordinates();
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
            }
        }
    }


    public String getName() throws EndOfFileException {
        while (true) {
            try {
                String name = reader.readline();
                if (name == null) {
                    throw new EndOfFileException();
                }
                if (!name.equals("") && !name.equals("\\s+")) {
                    return name;
                }
            } catch (NumberFormatException e) {
            }
        }
    }
}