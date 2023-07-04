package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    private int id;

    @NotBlank(message = "Name cannot be blank.")
    private final String name;

    @NotNull(message = "Description cannot be null.")
    @Size(max = 200, message = "Maximum description length - 200 characters.")
    private final String description;

    @NotNull(message = "ReleaseDate cannot be null.")
    private final LocalDate releaseDate;

    @Positive(message = "Duration should be positive.")
    private final long duration;
}
