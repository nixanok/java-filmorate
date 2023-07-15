package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public final class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid final User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid final User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
