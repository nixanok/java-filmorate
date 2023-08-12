package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);

    void update(Film film);

    Film get(int id);

    List<Film> getAll();

    void delete(int id);
}
