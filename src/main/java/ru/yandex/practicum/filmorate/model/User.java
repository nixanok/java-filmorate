package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.WithOutSpaces;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class User {
    private int id;

    @NotBlank(message = "Login cannot be blank.")
    @WithOutSpaces(message = "Login cannot have spaces.")
    private final String login;

    private String name;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email cannot be blank.")
    private final String email;

    @PastOrPresent(message = "Birthday should be in the past or present.")
    private final LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();

    public void addFriend(int id) {
        friends.add(id);
    }

    public void removeFriend(int id) {
        friends.remove(id);
    }
}
