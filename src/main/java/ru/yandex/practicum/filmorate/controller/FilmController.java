package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
@Slf4j
public final class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@RequestBody @Valid final Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid final Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

}
