package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
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

    private final Set<Integer> userLikes = new HashSet<>();

    public void addUserLike(int id) {
        userLikes.add(id);
    }

    public void removeUserLike(int id) {
        if (!userLikes.contains(id)) {
            throw new UserLikeNotFoundException(this.id, id);
        }
        userLikes.remove(id);
    }

    public int getCountLikes() {
        return userLikes.size();
    }
}
