package ru.yandex.practicum.filmorate.exception;

public class FriendNotFoundException extends RuntimeException {
    public FriendNotFoundException(int id) {
        super(String.format("Friend with id = %s not found.", id));
    }
}
