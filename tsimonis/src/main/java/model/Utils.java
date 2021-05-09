package model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.enums.Color;
import service.db.JdbcTemplate;

import javax.activation.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;

public class Utils {
    public static final Random RANDOM = new Random();
    public static final Charset charset = StandardCharsets.UTF_8;
    public static final JdbcTemplate jdbcTemplate = null; // new JdbcTemplate(new DataSource())
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static String readFile(String path, Charset charset) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, charset);
    }

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new ValidationException("Incorrect arguments were given to the " + object +
                    " (" + constraintViolations + ")");
        }
    }

    public static String getColorString(String string, Color color) {
        return color.getColor() + string + Color.ENDC.getColor();
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
