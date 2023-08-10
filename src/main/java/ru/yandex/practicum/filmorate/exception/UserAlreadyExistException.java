package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(long id) {
        super(String.format("User with id = %s already exist.", id));
    }
}
