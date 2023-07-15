package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.NoValidIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @Override
    public void add(Film film) {
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistException();
        }
        film.setId(nextId++);
        films.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId()))
            throw new FilmNotFoundException(film.getId());
        return films.put(film.getId(), film);
    }

    @Override
    public Film put(Film film) {
        if (film.getId() <= 0) {
            throw new NoValidIdException();
        }
        return films.put(film.getId(), film);
    }

    @Override
    public Film get(int id) {
        return films.get(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public boolean contains(int id) {
        return films.containsKey(id);
    }

    @Override
    public void delete(int id) {
        films.remove(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
        nextId = 0;
    }
}
