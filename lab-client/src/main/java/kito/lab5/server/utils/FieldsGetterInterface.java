package kito.lab5.server.utils;

import kito.lab5.server.exceptions.EndOfFileException;
import kito.lab5.common.entities.Coordinates;


public interface FieldsGetterInterface {
    Coordinates getCoordinates() throws EndOfFileException;

    String getName() throws EndOfFileException;
}
