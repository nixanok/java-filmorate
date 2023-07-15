package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(int id) {
        super(String.format("User with id = %s already exist.", id));
    }
}
