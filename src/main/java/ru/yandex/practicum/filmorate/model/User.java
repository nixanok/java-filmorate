package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    private int id;

    @NotBlank(message = "Login cannot be blank.")
    private String login;

    private String name;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @PastOrPresent(message = "Birthday should be in the past or present.")
    private final LocalDate birthday;
}
