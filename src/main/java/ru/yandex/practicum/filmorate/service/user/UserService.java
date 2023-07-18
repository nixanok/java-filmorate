package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User createUser(final User user);

    User updateUser(final User user);

    List<User> getUsers();

    User getUser(int id);

    void addFriends(int firstId, int secondId);

    void removeFriends(int firstId, int secondId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int firstId, int secondId);
}
