package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    void add(User user);
    User update(User user);
    User put(User user);
    User get(int id);
    List<User> getAll();
    boolean contains(int id);
    void delete(int id);
    void deleteAll();
}
