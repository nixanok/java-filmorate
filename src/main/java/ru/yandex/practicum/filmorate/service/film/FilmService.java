package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmService {
    Film createFilm(final Film film);

    Film updateFilm(final Film film);

    Film getFilm(int id);

    List<Film> getFilms();

    List<Film> getMostLikedFilms(int count);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
