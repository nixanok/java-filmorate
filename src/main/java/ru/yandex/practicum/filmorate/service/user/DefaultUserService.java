package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public final class DefaultUserService implements UserService {
    @Autowired
    private final UserStorage userStorage;

    @Override
    public User createUser(final User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.add(user);
        log.info("User created successfully. {}", user);
        return user;
    }

    @Override
    public User updateUser(final User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.update(user);
        log.info("User updated successfully. {}", user);
        return user;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> users = userStorage.getAll();
        log.info("Getting users. Size = {}", users.size());
        return users;
    }

    @Override
    public User getUser(int id) {
        User user = userStorage.get(id);
        log.info("Getting user. Id = {}", id);
        return user;
    }
}
