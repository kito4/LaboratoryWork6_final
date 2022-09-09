package kito.lab5.server.utils;

import kito.lab5.common.entities.enums.WeaponType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringToTypeConverter {

    public static Object toObject(Class<?> requiredClass, String value) throws IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        if (Boolean.class == requiredClass || Boolean.TYPE == requiredClass) return Boolean.parseBoolean(value);
        if (Byte.class == requiredClass || Byte.TYPE == requiredClass) return Byte.parseByte(value);
        if (Short.class == requiredClass || Short.TYPE == requiredClass) return Short.parseShort(value);
        if (Integer.class == requiredClass || Integer.TYPE == requiredClass) return Integer.parseInt(value);
        if (Long.class == requiredClass || Long.TYPE == requiredClass) return Long.parseLong(value);
        if (Float.class == requiredClass || Float.TYPE == requiredClass) return Float.parseFloat(value);
        if (Double.class == requiredClass || Double.TYPE == requiredClass) return Double.parseDouble(value);
        if (WeaponType.class == requiredClass) return WeaponType.valueOf(value.toUpperCase());
        if (ZonedDateTime.class==requiredClass) return ZonedDateTime.parse(value);
        return value;
    }

}
