package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(int id) {
        super(String.format("Genre with id = %s not found.", id));
    }
}
