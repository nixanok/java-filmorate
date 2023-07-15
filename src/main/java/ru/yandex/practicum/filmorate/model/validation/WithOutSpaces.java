package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = WithOutSpacesValidator.class)
@Documented
public @interface WithOutSpaces{

    String message() default "{ru.yandex.practicum.filmorate.model.annotation.WithOutSpaces.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}