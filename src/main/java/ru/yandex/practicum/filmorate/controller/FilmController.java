package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
@Slf4j
public final class FilmController {

    @Autowired
    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@RequestBody @Valid final Film film) {
        log.debug("Request \"createFilm\"is called");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid final Film film) {
        log.debug("Request \"updateFilm\"is called");
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Set<Film> getFilms() {
        log.debug("Request \"getFilms\"is called");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        log.debug("Request \"getFilm\"is called");
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.debug("Request \"getMostPopularFilms\"is called");
        return filmService.getMostLikedFilms(count);
    }
}
