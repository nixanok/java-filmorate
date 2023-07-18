package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyExistException extends RuntimeException {
    public FilmAlreadyExistException(int id) {
        super(String.format("Film with id = %s already exist.", id));
    }
}
