package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new LinkedHashMap<>();
    private int nextId = 1;

    @PostMapping("/users")
    public User createUser(@RequestBody @Valid final User user) throws ValidationException {
        try {
            validateUser(user);
        } catch (ValidationException ex) {
            log.warn(ex.getMessage());
            throw ex;
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
        log.info("User successfully created. {}", user);
        return user.toBuilder().build();
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody @Valid final User user) throws ValidationException {
        try {
            validateUser(user);
        } catch (ValidationException ex) {
            log.warn(ex.getMessage());
            throw ex;
        }

        if (!users.containsKey(user.getId())) {
            log.warn("No user with this id. id = {}", user.getId());
            throw new ValidationException("No user with this id.");
        }

        users.put(user.getId(), user);
        log.info("User successfully put. {}", user);
        return user.toBuilder().build();
    }

    @GetMapping("/users")
    public ArrayList<User> getUsers() {
        log.info("Getting users. Size = {}", users.size());
        return new ArrayList<>(users.values());
    }

    private void validateUser(final @NotNull User user) throws ValidationException {
        if (!user.getLogin().matches("^\\S+$")) {
            throw new ValidationException("Login cannot have spaces.");
        }
    }
}
