package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public final class FilmService {
    private final FilmStorage filmStorage;

    public Film createFilm(final Film film) {
        filmStorage.add(film);
        log.info("Film created  successfully. {}", film);
        return film;
    }

    public Film updateFilm(final Film film) {
        filmStorage.update(film);
        log.info("Film updated successfully. {}", film);
        return film;
    }

    public List<Film> getFilms() {
        final List<Film> films = filmStorage.getAll();
        log.info("Getting films. Size = {}", films.size());
        return films;
    }
}
