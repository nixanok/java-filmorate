package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    int id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    final String name;

    @NotNull(message = "description cannot be null")
    @Size(max = 200, message = "maximum description length - 200 characters")
    final String description;

    @NotNull(message = "releaseDate cannot be null")
    final LocalDate releaseDate;

    final long duration;
}
