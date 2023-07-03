package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldNotCreateFilmWithoutName() throws Exception {
        Film film1 = Film
                .builder()
                .description("asd")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .build();

        Film film2 = Film
                .builder()
                .name("")
                .description("asd")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .build();

        pushFilmAndExpect4xxCode(film1);
        pushFilmAndExpect4xxCode(film2);
    }

    @Test
    public void shouldNotCreateFilmWithDescriptionMoreThan200() throws Exception {
        Film film = Film
                .builder()
                .description("description".repeat(100))
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .build();
        pushFilmAndExpect4xxCode(film);
    }

    @Test
    public void shouldNotCreateFilmWhenDateBefore1895_28_01() throws Exception {
        Film film = Film
                .builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1894, 1, 1))
                .duration(123)
                .build();
        pushFilmAndExpect4xxCode(film);
    }

    @Test
    public void shouldNotCreateFilmDurationIsNegativeOrZero() throws Exception {
        Film film1 = Film
                .builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1894, 1, 1))
                .duration(-10)
                .build();

        Film film2 = Film
                .builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1894, 1, 1))
                .duration(0)
                .build();

        pushFilmAndExpect4xxCode(film1);
        pushFilmAndExpect4xxCode(film2);
    }

    private void pushFilmAndExpect4xxCode(Film film) throws Exception {
        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
