package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface UserService {
    User createUser(final User user);

    User updateUser(final User user);

    Set<User> getUsers();

    User getUser(int id);
}
