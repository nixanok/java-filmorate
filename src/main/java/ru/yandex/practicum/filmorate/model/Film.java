package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.CorrectFilmDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    public static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    private int id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotNull(message = "Description cannot be null.")
    @Size(max = 200, message = "Maximum description length - 200 characters.")
    private String description;

    @NotNull(message = "ReleaseDate cannot be null.")
    @CorrectFilmDate(message = "ReleaseDate cannot be before 1895.12.28.")
    private LocalDate releaseDate;

    @Positive(message = "Duration should be positive.")
    private long duration;
}
