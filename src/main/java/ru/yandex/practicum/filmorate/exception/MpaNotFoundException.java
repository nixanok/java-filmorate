package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException(int id) {
        super(String.format("Mpa with id = %s not found.", id));
    }
}
