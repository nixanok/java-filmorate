package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
@Slf4j
public final class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleValidationDataError(final MethodArgumentNotValidException e) {
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

    @ExceptionHandler(NoValidIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoValidIdError(final NoValidIdException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("BAD_REQUEST", 404, e.getMessage(), LocalDateTime.now().withNano(0));
    }

    @ExceptionHandler({FilmNotFoundException.class,
            UserNotFoundException.class,
            UserLikeNotFoundException.class,
            FriendsNotFoundException.class,
            GenreNotFoundException.class,
            MpaNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundError(final RuntimeException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("NOT_FOUND", 404, e.getMessage(), LocalDateTime.now().withNano(0));
    }

    @ExceptionHandler({FilmAlreadyExistException.class,
            UserAlreadyExistException.class,
            FriendsAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistError(final RuntimeException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("CONFLICT", 409, e.getMessage(), LocalDateTime.now().withNano(0));
    }
}
