package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NoValidIdException;
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
    public void add(User user) {
        if (users.containsKey(user.getId())) {
            throw new FilmAlreadyExistException();
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId()))
            throw new UserNotFoundException(user.getId());
        return users.put(user.getId(), user);
    }

    @Override
    public User put(User user) {
        if (user.getId() <= 0) {
            throw new NoValidIdException();
        }
        return users.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean contains(int id) {
        return users.containsKey(id);
    }

    @Override
    public void delete(int id) {
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
        nextId = 0;
    }
}
