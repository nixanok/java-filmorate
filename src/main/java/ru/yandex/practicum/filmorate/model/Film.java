package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.NoValidGenreException;
import ru.yandex.practicum.filmorate.model.validation.CorrectFilmDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Film {
    public static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);
    private int id;

    @NotBlank(message = "Name cannot be blank.")
    private final String name;

    @NotNull(message = "Description cannot be null.")
    @Size(max = 200, message = "Maximum description length - 200 characters.")
    private final String description;

    @NotNull(message = "ReleaseDate cannot be null.")
    @CorrectFilmDate(message = "ReleaseDate cannot be before 1895.12.28.")
    private final LocalDate releaseDate;

    @Positive(message = "Duration should be positive.")
    private final long duration;

    private final Mpa mpa;

    private Set<Genre> genres;

    public void addGenre(Genre genre) {
        initGenres();
        if (genre.getId() <= 0) {
            throw new NoValidGenreException(genre.getId());
        }
        genres.add(genre);
    }

    public void initGenres() {
        if (genres == null) {
            genres = new HashSet<>();
        }
    }

    public void removeGenre(Genre genre) {
        if (!genres.contains(genre)) {
            throw new GenreNotFoundException(genre.getId());
        }
        genres.remove(genre);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }
}
