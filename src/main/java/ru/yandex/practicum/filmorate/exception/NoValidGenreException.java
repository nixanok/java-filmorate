package ru.yandex.practicum.filmorate.exception;

public class NoValidGenreException extends RuntimeException {
    public NoValidGenreException(int id) {
        super(String.format("Id = %s is incorrect.", id));
    }
}
