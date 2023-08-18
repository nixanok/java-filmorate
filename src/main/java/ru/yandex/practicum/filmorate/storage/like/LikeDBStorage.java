package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserLikeNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Repository
@RequiredArgsConstructor
public class LikeDBStorage implements LikeStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final UserStorage userStorage;

    @Autowired
    private final FilmStorage filmStorage;

    @Override
    public void add(int filmId, int userId) {
        if (!filmStorage.contains(filmId)) {
            throw new FilmNotFoundException(filmId);
        }
        if (!userStorage.contains(userId)) {
            throw new UserNotFoundException(userId);
        }
        jdbcTemplate.update("INSERT INTO users_likes_films (film_id, user_id) " +
                                "VALUES (?, ?)", filmId, userId);
    }

    @Override
    public void remove(int filmId, int userId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT film_id, user_id FROM users_likes_films " +
                                                              "WHERE film_id = ? AND user_id = ?", filmId, userId);
        if (!sqlRowSet.next()) {
            throw new UserLikeNotFoundException(filmId, userId);
        }
        jdbcTemplate.update("DELETE FROM users_likes_films " +
                                "WHERE film_id = ? AND user_id = ?", filmId, userId);
    }

    @Override
    public int getCount(int filmId) {
        if (!filmStorage.contains(filmId)) {
            throw new FilmNotFoundException(filmId);
        }
        return jdbcTemplate.queryForObject("SELECT COUNT(film_id) FROM users_likes_films " +
                                               "WHERE film_id = ?", Integer.class, filmId);
    }
}
