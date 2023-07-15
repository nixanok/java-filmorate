package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public final class UserService {
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
}
