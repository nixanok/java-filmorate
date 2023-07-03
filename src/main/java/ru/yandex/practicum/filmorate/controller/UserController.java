package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.responseError.ResponseError;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid final User user) {
        try{
            validateUser(user);
        } catch (ValidationException ex) {
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(400)
                    .error("Bad Request")
                    .path("/users")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
        log.info("User successfully created. {}", user);
        return new ResponseEntity<>(user.toBuilder().build(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid final User user) {
        try{
            validateUser(user);
        } catch (ValidationException ex) {
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(400)
                    .error("Bad Request")
                    .path("/users")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (!users.containsKey(user.getId())) {
            log.warn("No user with this id. id = {}", user.getId());
            ResponseError error = ResponseError
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .status(500)
                    .error("Bad Request")
                    .path("/users")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        users.put(user.getId(), user);
        log.info("User successfully put. {}", user);
        return new ResponseEntity<>(user.toBuilder().build(), HttpStatus.OK);
    }

    @GetMapping
    public ArrayList<User> getUsers() {
        log.info("Getting users. Size = {}", users.size());
        return new ArrayList<>(users.values());
    }

    private void validateUser(final User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Login cannot have spaces.");
            throw new ValidationException("Login cannot have spaces.");
        }
    }
}
