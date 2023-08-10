package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyExistException extends RuntimeException {
    public FilmAlreadyExistException(long id) {
        super(String.format("Film with id = %s already exist.", id));
    }
}
