package ru.yandex.practicum.filmorate.model.validation;

import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CorrectFilmDateValidator implements ConstraintValidator<CorrectFilmDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !(value.isBefore(Film.DATE_OF_FIRST_FILM));
    }
}
