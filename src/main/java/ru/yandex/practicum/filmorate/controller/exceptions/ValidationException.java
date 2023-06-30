package ru.yandex.practicum.filmorate.controller.exceptions;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
