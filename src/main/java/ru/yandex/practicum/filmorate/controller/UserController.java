package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public final class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid final User user) {
        log.debug("Request \"createUser\"is called");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid final User user) {
        log.debug("Request \"updateUser\"is called");
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.debug("Request \"getUsers\"is called");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.debug("Request \"getUser\"is called");
        return userService.getUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Request \"addFriends\"is called");
        userService.addFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriends(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Request \"removeFriends\"is called");
        userService.removeFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.debug("Request \"getFriends\"is called");
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug("Request \"getCommonFriends\"is called");
        return userService.getCommonFriends(id, otherId);
    }

}
