package ru.yandex.practicum.filmorate.exception;

public class FriendsNotFoundException extends RuntimeException {
    public FriendsNotFoundException(int firstId, int secondId) {
        super(String.format("Friends with id = %s and id = %s not found.", firstId, secondId));
    }
}
