package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {
    void add(int filmId, int userId);

    void remove(int filmId, int userId);

    int getCount(int filmId);

}
