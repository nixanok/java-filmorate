package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    int id;

    @NotNull(message = "login cannot be null")
    @NotBlank(message = "login cannot be blank")
    String login;

    String name;

    @NotNull
    @Email(message = "Email should be valid")
    String email;

    @Past(message = "birthday should be in the past")
    final LocalDate birthday;
}
