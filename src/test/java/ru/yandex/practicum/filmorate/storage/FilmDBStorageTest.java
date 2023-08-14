package ru.yandex.practicum.filmorate.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDBStorage;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDBStorageTest {
    private final FilmDBStorage filmDBStorage;

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testCrateAndGetFilm() {
        Film film = Film
                .builder()
                .description("asd")
                .name("name")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .mpa(new Mpa(1))
                .genres(new HashSet<>())
                .build();
        film = filmDBStorage.add(film);
        assertEquals(film, filmDBStorage.get(1));
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testUpdateFilm() {
        Film film = Film
                .builder()
                .description("asd")
                .name("name")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .mpa(new Mpa(1))
                .genres(new HashSet<>())
                .build();
        filmDBStorage.add(film);
        Film newFilm = Film
                .builder()
                .description("123")
                .name("nam123e")
                .releaseDate(LocalDate.of(2000, 10, 1))
                .duration(12)
                .mpa(new Mpa(2))
                .genres(new HashSet<>())
                .build();
        newFilm.setId(1);
        filmDBStorage.update(newFilm);
        assertEquals(newFilm, filmDBStorage.get(1));
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testGetAllFilms() {
        Set<Film> films = new LinkedHashSet<>();
        Film film = Film
                .builder()
                .description("asd")
                .name("name")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .mpa(new Mpa(1))
                .genres(new HashSet<>())
                .build();
        film = filmDBStorage.add(film);
        films.add(film);
        film = Film
                .builder()
                .description("123")
                .name("nam123e")
                .releaseDate(LocalDate.of(2000, 10, 1))
                .duration(12)
                .mpa(new Mpa(2))
                .genres(new HashSet<>())
                .build();
        film = filmDBStorage.add(film);
        films.add(film);
        assertEquals(films, filmDBStorage.getAll());
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testDeleteFilm() {
        Film film = Film
                .builder()
                .description("asd")
                .name("name")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(123)
                .mpa(new Mpa(1))
                .genres(new HashSet<>())
                .build();
        filmDBStorage.add(film);
        filmDBStorage.delete(1);
        assertThrows(FilmNotFoundException.class, () -> filmDBStorage.get(1));
    }

}
