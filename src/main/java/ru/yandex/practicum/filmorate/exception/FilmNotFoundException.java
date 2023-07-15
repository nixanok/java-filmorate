package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends IllegalArgumentException {
    public FilmNotFoundException(int id) {
        super(String.format("Film with id = %s is not found.", id));
    }
}