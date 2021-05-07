package model.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class NotContainsNullValidator implements ConstraintValidator<NotContainsNull, Collection<?>> {
    @Override
    public void initialize(NotContainsNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext constraintContext) {
        if (collection == null) {
            return true;
        }
        boolean isValid = true;
        for (Object object : collection) {
            if (object == null) {
                isValid = false;
                break;
            }
        }
        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                    "constraintvalidatorcontext.NotContainsNull.message}"
            ).addConstraintViolation();
        }
        return isValid;
    }
}
