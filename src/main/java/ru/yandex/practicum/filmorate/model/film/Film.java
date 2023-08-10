package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.NoValidGenreException;
import ru.yandex.practicum.filmorate.exception.NoValidIdException;
import ru.yandex.practicum.filmorate.exception.UserLikeNotFoundException;
import ru.yandex.practicum.filmorate.model.validation.CorrectFilmDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
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

    private final MPA mpa;

    private final Set<Genre> genres = new HashSet<>();

    @JsonIgnore
    private final Set<Long> userLikes = new HashSet<>();

    public void addUserLike(long id) {
        if (id <= 0) {
            throw new NoValidIdException(id);
        }
        userLikes.add(id);
    }

    public void removeUserLike(long id) {
        if (!userLikes.contains(id)) {
            throw new UserLikeNotFoundException(this.id, id);
        }
        userLikes.remove(id);
    }

    public void addGenre(Genre genre) {
        if (genre.getId() <= 0) {
            throw new NoValidGenreException(genre.getId());
        }
        genres.add(genre);
    }

    public void addGenres(Set<Genre> genres) {
        this.genres.addAll(genres);
    }

    public void removeGenre(Genre genre) {
        if (!genres.contains(genre)) {
            throw new GenreNotFoundException(genre.getId());
        }
        genres.remove(genre);
    }

    public int getCountLikes() {
        return userLikes.size();
    }
}
