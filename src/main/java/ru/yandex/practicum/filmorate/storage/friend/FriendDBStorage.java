package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FriendsNotFoundException;
import ru.yandex.practicum.filmorate.exception.FriendsAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class FriendDBStorage implements  FriendStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final UserStorage userStorage;

    @Override
    public Set<User> getFriends(int id) {
        if (!userStorage.contains(id)) {
            throw new UserNotFoundException(id);
        }
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT second_id FROM friends " +
                                                          "WHERE first_id = ?", id);
        Set<User> friends = new LinkedHashSet<>();
        while (sqlRowSet.next()) {
            int friendId = sqlRowSet.getInt("second_id");
            User friend = userStorage.get(friendId);
            friends.add(friend);
        }
        return friends;
    }

    @Override
    public void addFriends(int fromId, int toId) {
        if (existFriends(fromId, toId)) {
            throw new FriendsAlreadyExistException(fromId, toId);
        }
        if (!userStorage.contains(fromId)) {
            throw new UserNotFoundException(fromId);
        }
        if (!userStorage.contains(toId)) {
            throw new UserNotFoundException(fromId);
        }
        jdbcTemplate.update("INSERT INTO friends (first_id, second_id) " +
                            "VALUES (?, ?)", fromId, toId);
    }

    @Override
    public void removeFriends(int fromId, int toId) {
        if (!existFriends(fromId, toId)) {
            throw new FriendsNotFoundException(fromId, toId);
        }
        jdbcTemplate.update("DELETE FROM friends WHERE first_id = ? AND second_id = ?", fromId, toId);
    }

    private boolean existFriends(int fromId, int toId) {
        SqlRowSet sqlRowSetForCheck = jdbcTemplate.queryForRowSet("SELECT first_id FROM friends " +
                "WHERE first_id = ? AND second_id = ?", fromId, toId);
        return sqlRowSetForCheck.next();
    }
}
