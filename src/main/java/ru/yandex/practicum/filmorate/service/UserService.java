package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public final class UserService {

    @Autowired
    private final UserStorage userStorage;

    public User createUser(final User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.add(user);
        log.info("User created successfully. {}", user);
        return user;
    }

    public User updateUser(final User user) {
        userStorage.update(user);
        log.info("User updated successfully. {}", user);
        return user;
    }

    public List<User> getUsers() {
        List<User> users = userStorage.getAll();
        log.info("Getting users. Size = {}", users.size());
        return users;
    }

    public User getUser(int id) {
        User user = userStorage.get(id);
        log.info("Getting user. Id = {}", id);
        return user;
    }

    public void addFriends(int firstId, int secondId) {
        User firstUser = userStorage.get(firstId);
        User secondUser = userStorage.get(secondId);
        firstUser.addFriend(secondId);
        secondUser.addFriend(firstId);
        log.info("Adding friends. First id = {}. Second id = {}", firstId, secondId);
    }

    public void removeFriends(int firstId, int secondId) {
        User firstUser = userStorage.get(firstId);
        User secondUser = userStorage.get(secondId);
        firstUser.removeFriend(secondId);
        secondUser.removeFriend(firstId);
        log.info("Removing friends. First id = {}. Second id = {}", firstId, secondId);
    }

    public List<User> getFriends(int id) {
        User user = userStorage.get(id);
        List<User> friends = user.getFriends()
                .stream()
                .map(userStorage::get)
                .collect(Collectors.toList());
        log.info("Getting friends of id = {}. Size = {}", id, friends.size());
        return friends;
    }

    public List<User> getCommonFriends(int firstId, int secondId) {
        Set<Integer> friendsIdFirst = userStorage.get(firstId).getFriends();
        Set<Integer> secondsIdSecond = userStorage.get(secondId).getFriends();

        Set<Integer> commonIds = new HashSet<>(friendsIdFirst);
        commonIds.retainAll(secondsIdSecond);

        List<User> friends = commonIds
                .stream()
                .map(userStorage::get)
                .collect(Collectors.toList());
        log.info("Getting common friends of id = {} and id = {}. Size = {}", firstId, secondId, friends.size());
        return friends;
    }

}
