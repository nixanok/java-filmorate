package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(int id) {
        super(String.format("User with id = %s is not found.", id));
    }
}
