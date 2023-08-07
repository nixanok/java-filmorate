package ru.yandex.practicum.filmorate.exception;

public class FriendAlreadyExistException extends RuntimeException {
    public FriendAlreadyExistException(int id) {
        super (String.format("Friend with id = %s already exist.", id));
    }
}
