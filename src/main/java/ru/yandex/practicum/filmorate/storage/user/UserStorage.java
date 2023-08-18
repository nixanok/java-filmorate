package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface UserStorage {
    User add(User user);

    void update(User user);

    boolean contains(int id);

    User get(int id);

    Set<User> getAll();

    void delete(int id);

}
