package ru.yandex.practicum.filmorate.service.like;

public interface LikeService {
    int getCount(int id);

    void add(int filmId, int userId);

    void remove(int filmId, int userId);
}
