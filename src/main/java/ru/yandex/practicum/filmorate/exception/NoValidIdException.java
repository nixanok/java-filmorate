package ru.yandex.practicum.filmorate.exception;

public class NoValidIdException extends RuntimeException {
    public NoValidIdException(int id) {
        super(String.format("Id = %s is incorrect.", id));
    }
}
