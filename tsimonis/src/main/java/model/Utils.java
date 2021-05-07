package model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class Utils {
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new ValidationException("Incorrect arguments were given to the " + object +
                    " (" + constraintViolations + ")");
        }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
