package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @Override
    public User add(User user) {
        if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException(user.getId());
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(User user) {
        if (!users.containsKey(user.getId()))
            throw new UserNotFoundException(user.getId());
        users.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        if (!users.containsKey(id))
            throw new UserNotFoundException(id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void delete(int id) {
        if (!users.containsKey(id))
            throw new UserNotFoundException(id);
        users.remove(id);
    }
}
