package ru.yandex.practicum.filmorate.service.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendService {
    void addFriends(int firstId, int secondId);

    void removeFriends(int firstId, int secondId);

    Set<User> getFriends(int id);

    Set<User> getCommonFriends(int firstId, int secondId);
}
