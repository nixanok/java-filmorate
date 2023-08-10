package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.*;

@Component
@Qualifier("InMemory")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @Override
    public Film add(Film film) {
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistException(film.getId());
        }
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void update(Film film) {
        if (!films.containsKey(film.getId()))
            throw new FilmNotFoundException(film.getId());
        films.put(film.getId(), film);
    }

    @Override
    public Film get(int id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(id);
        }
        return films.get(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void delete(int id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(id);
        }
        films.remove(id);
    }
}
