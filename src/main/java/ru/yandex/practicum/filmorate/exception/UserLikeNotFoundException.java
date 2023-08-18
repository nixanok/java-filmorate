package ru.yandex.practicum.filmorate.exception;

public class UserLikeNotFoundException extends RuntimeException {
    public UserLikeNotFoundException(long filmId, long userId)  {
        super(String.format("User with id = %s like on film with id = %s is not found.", userId, filmId));
    }
}
