package ru.yandex.practicum.filmorate.exception;

public class UserLikeNotFoundException extends RuntimeException {
    public UserLikeNotFoundException(int filmId, int userId)  {
        super(String.format("User with id = %s like on film with id = %s is not found.", userId, filmId));
    }
}
