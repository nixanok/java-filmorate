package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User add(User user);

    void update(User user);

    User get(int id);

    List<User> getAll();

    void delete(int id);

}
