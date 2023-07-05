package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.responseError.ResponseError;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    public static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    @PostMapping
    public ResponseEntity<?> createFilm(@RequestBody @Valid final Film film) {
        try {
            validateFilm(film);
        } catch (ValidationException ex) {
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(400)
                    .error("Bad Request")
                    .path("/films")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        film.setId(nextId++);
        films.put(film.getId(), film);
        log.info("Film successfully created. {}", film);
        return new ResponseEntity<>(film.toBuilder().build(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateFilm(@RequestBody @Valid final Film film) {
        try {
            validateFilm(film);
        } catch (ValidationException ex) {
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(400)
                    .error("Bad Request")
                    .path("/films")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (!films.containsKey(film.getId())) {
            log.warn("No film with this id. Id = {}", film.getId());
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(404)
                    .error("Bad Request")
                    .path("/films")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        films.put(film.getId(), film);
        log.info("Film successfully put. {}", film);
        return new ResponseEntity<>(film.toBuilder().build(), HttpStatus.OK);
    }

    @GetMapping
    public ArrayList<Film> getFilms() {
        log.info("Getting films. Size = {}", films.size());
        return new ArrayList<>(films.values());
    }

    private void validateFilm(final Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(DATE_OF_FIRST_FILM)) {
            log.warn("Date of film is incorrect.");
            throw new ValidationException("Date of film is incorrect.");
        }
    }

}
