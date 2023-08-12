package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public final class DefaultFilmService implements FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    @Override
    public Film createFilm(final Film film) {
        filmStorage.add(film);
        log.info("Film created  successfully. {}", film);
        return film;
    }

    @Override
    public Film updateFilm(final Film film) {
        filmStorage.update(film);
        log.info("Film updated successfully. {}", film);
        return film;
    }

    @Override
    public Film getFilm(int id) {
        Film film = filmStorage.get(id);
        log.info("Getting film. Id = {}", id);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        final List<Film> films = filmStorage.getAll();
        log.info("Getting films. Size = {}", films.size());
        return films;
    }

    @Override
    public List<Film> getMostLikedFilms(int count) {
        final List<Film> sortedFilms = filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparing(Film::getCountLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Getting sorted by likes films. Size = {}", sortedFilms.size());
        return sortedFilms;
    }

    @Override
    public void addLike(int filmId, int userId) {
        Film film = filmStorage.get(filmId);
        film.addUserLike(userId);
        filmStorage.update(film);
        log.info("Like on film with id = {} by user with id = {} added.", filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.get(filmId);
        film.removeUserLike(userId);
        filmStorage.update(film);
        log.info("Like on film with id = {} by user with id = {} removed.", filmId, userId);
    }
}
