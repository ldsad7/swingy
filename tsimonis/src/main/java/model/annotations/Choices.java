package model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ChoicesValidator.class)
@Documented
public @interface Choices {
    String message() default "The value is not in the list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value();

    @Target({FIELD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Choices[] value();
    }
}
