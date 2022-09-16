package kito.lab5.server.utils;


import kito.lab5.common.entities.HumanBeing;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class HumanValidator {

    static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    static Validator validator = validatorFactory.getValidator();

    public static boolean validateHuman(HumanBeing human) {
        Set<ConstraintViolation<HumanBeing>> validateResult = validator.validate(human);
        if (validateResult.size() > 0) {
            for (ConstraintViolation<HumanBeing> violation : validateResult) {
                System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
            }
            return false;
        }
        return true;
    }

    public static <T> boolean validateField(T t, String fieldName) {
        Set<ConstraintViolation<T>> validateResult = validator.validateProperty(t, fieldName);
        if (validateResult.size() > 0) {
            for (ConstraintViolation<T> violation : validateResult) {
                System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
            }
            return false;
        }
        return true;
    }
}
