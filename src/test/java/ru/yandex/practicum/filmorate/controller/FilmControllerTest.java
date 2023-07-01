package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    public void initController() {
        filmController = new FilmController();
    }

    @Test
    public void shouldCreateUserWithCorrectData() {
        Film film = Film
                .builder()
                .name("asd")
                .description("asd")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .build();

        Film loadedFilm =  filmController.createFilm(film);

        assertEquals(loadedFilm, film);
    }

}
