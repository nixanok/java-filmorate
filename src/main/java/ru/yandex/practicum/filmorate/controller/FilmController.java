package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    Map<Integer, Film> films = new LinkedHashMap<>();
    private int nextId = 1;

    public static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    @PostMapping("/films")
    public Film createFilm(@RequestBody @Valid final Film film) throws ValidationException {
        try {
            validateFilm(film);
        } catch (ValidationException ex) {
            log.warn(ex.getMessage());
            throw ex;
        }

        film.setId(nextId++);
        films.put(film.getId(), film);
        log.info("Film successfully created. {}", film);
        return film.toBuilder().build();
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid final Film film) throws ValidationException {
        try {
            validateFilm(film);
        } catch (ValidationException ex) {
            log.warn(ex.getMessage());
            return Film.builder().build();
        }

        if (!films.containsKey(film.getId())) {
            log.warn("No film with this id. Id = {}", film.getId());
            throw new ValidationException("No film with this id.");
        }

        films.put(film.getId(), film);
        log.info("Film successfully put. {}", film);
        return film.toBuilder().build();
    }

    @GetMapping("/films")
    public ArrayList<Film> getFilms() {
        log.info("Getting films. Size = {}", films.size());
        return new ArrayList<>(films.values());
    }

    private void validateFilm(final Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(DATE_OF_FIRST_FILM)) {
            throw new ValidationException("Date of film is incorrect.");
        }

        if (film.getDuration() < 0) {
            throw new ValidationException("Duration cannot be negative.");
        }
    }

}
