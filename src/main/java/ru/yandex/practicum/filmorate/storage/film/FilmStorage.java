package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    Film add(Film film);

    void update(Film film);

    boolean contains(int id);

    Film get(int id);

    List<Film> getSortedByLikes(int count);

    Set<Film> getAll();

    void delete(int id);
}
