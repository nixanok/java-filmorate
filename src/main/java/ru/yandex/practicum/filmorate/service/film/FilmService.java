package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmService {
    Film createFilm(final Film film);

    Film updateFilm(final Film film);

    Film getFilm(int id);

    Set<Film> getAll();

    List<Film> getMostLikedFilms(int count);
}
