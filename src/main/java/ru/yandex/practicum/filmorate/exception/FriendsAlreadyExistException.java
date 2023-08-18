package ru.yandex.practicum.filmorate.exception;

public class FriendsAlreadyExistException extends RuntimeException {
    public FriendsAlreadyExistException(int firstId, int secondId) {
        super(String.format("Friends with id = %s and id = %s already exist.", firstId, secondId));
    }
}
