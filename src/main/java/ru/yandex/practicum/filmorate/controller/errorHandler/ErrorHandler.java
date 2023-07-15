package ru.yandex.practicum.filmorate.controller.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
@Slf4j
public final class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleValidationError(final MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorResponse> errorResponses = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "BAD_REQUEST",
                    400,
                    String.format("Bad argument : %s. %s", fieldError.getField(), fieldError.getDefaultMessage()),
                    LocalDateTime.now().withNano(0)
            );
            errorResponses.add(errorResponse);
        }
        log.warn(fieldErrors.toString());
        return errorResponses;
    }

    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundError(final RuntimeException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Not Found", 404, e.getMessage(), LocalDateTime.now().withNano(0));
    }
}
