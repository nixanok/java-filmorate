package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(long id) {
        super(String.format("Film with id = %s is not found.", id));
    }
}