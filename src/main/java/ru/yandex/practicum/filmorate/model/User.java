package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.FriendAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NoValidIdException;
import ru.yandex.practicum.filmorate.exception.FriendNotFoundException;
import ru.yandex.practicum.filmorate.exception.FriendRequestNotFoundException;
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

    @JsonIgnore
    private final Set<Integer> friends = new HashSet<>();

    @JsonIgnore
    private final Set<Integer> requestFriends = new HashSet<>();

    public void addRequestFriend(int id) {
        if (id <= 0) {
            throw new NoValidIdException(id);
        }
        if (requestFriends.contains(id)) {
            throw new FriendAlreadyExistException(id);
        }
        requestFriends.add(id);
    }

    public boolean isHasRequest(int id) {
        return requestFriends.contains(id);
    }

    public void confirmRequestFriend(int id) {
        if (!isHasRequest(id)) {
            throw new FriendRequestNotFoundException(id);
        }
        requestFriends.remove(id);
        friends.add(id);
    }


    public void removeFriend(int id) {
        if (!friends.contains(id)) {
            throw new FriendNotFoundException(id);
        }
        friends.remove(id);
    }
}
