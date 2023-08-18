package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendStorage {
    Set<User> getFriends(int id);

    void addFriends(int fromId, int toId);

    void removeFriends(int fromId, int toId);
}
