package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.like.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public final class DefaultFilmService implements FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    @Autowired
    private final LikeService likeService;

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
    public Set<Film> getAll() {
        final Set<Film> films = filmStorage.getAll();
        log.info("Getting films. Size = {}", films.size());
        return films;
    }

    @Override
    public List<Film> getMostLikedFilms(int count) {
        final List<Film> sortedFilms = filmStorage.getSortedByLikes(count);
        log.info("Getting sorted by likes films. Size = {}", sortedFilms.size());
        return sortedFilms;
    }

    private int getLikesCount(Film film) {
        return likeService.getCount(film.getId());
    }
}
