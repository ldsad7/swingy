package model.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChoicesValidator implements ConstraintValidator<Choices, Integer> {
    private int[] collection;

    @Override
    public void initialize(Choices constraintAnnotation) {
        this.collection = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer object, ConstraintValidatorContext constraintContext) {
        boolean isValid = false;
        for (int value : collection) {
            if (value == object) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                    "constraintvalidatorcontext.Choices.message}"
            ).addConstraintViolation();
        }
        return isValid;
    }
}
