package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithOutSpacesValidator implements ConstraintValidator<WithOutSpaces, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !value.contains(" ");
    }
}
