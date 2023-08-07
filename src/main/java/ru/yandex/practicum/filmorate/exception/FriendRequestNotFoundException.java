package ru.yandex.practicum.filmorate.exception;

public class FriendRequestNotFoundException extends RuntimeException {
    public FriendRequestNotFoundException(int id) {
        super (String.format("Friend request with id = %s not found.", id));
    }
}
