package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void add(Film film);
    Film update(Film film);
    Film put(Film film);
    Film get(int id);
    List<Film> getAll();
    boolean contains(int id);
    void delete(int id);
    void deleteAll();
}
