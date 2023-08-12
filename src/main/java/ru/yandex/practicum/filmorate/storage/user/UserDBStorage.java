package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Primary
public class UserDBStorage implements  UserStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        String sqlQueryUser = "INSERT INTO users (login, name, email, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryUser, new String[]{"user_id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getBirthday().toString());
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public void update(User user) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT user_id FROM users WHERE user_id = ?", user.getId());
        if (!userRows.next()) {
            throw new UserNotFoundException(user.getId());
        }
        String sqlQueryForUpdate = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlQueryForUpdate,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId()
        );
        jdbcTemplate.update("DELETE FROM friends WHERE first_id = ?", user.getId());
        for (Integer idFriend : user.getFriends())
            jdbcTemplate.update("INSERT INTO friends (first_id, second_id) VALUES (?, ?)", user.getId(), idFriend);
    }

    @Override
    public User get(int id) {
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!userRows.next()) {
            throw new UserNotFoundException(id);
        }
        User user = User.builder()
                    .id(userRows.getInt("user_id"))
                    .login(userRows.getString("login"))
                    .name(userRows.getString("name"))
                    .email(userRows.getString("email"))
                    .birthday(userRows.getDate("birthday").toLocalDate())
                    .build();

        String sqlQueryFriends = "SELECT second_id FROM friends WHERE first_id = ?";
        SqlRowSet friendsRows = jdbcTemplate.queryForRowSet(sqlQueryFriends, id);
        while (friendsRows.next()) {
            int friendId = friendsRows.getInt("second_id");
            user.addFriend(friendId);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "SELECT user_id FROM users ORDER BY user_id";
        SqlRowSet idRows = jdbcTemplate.queryForRowSet(sqlQuery);
        List<User> users = new ArrayList<>();
        while (idRows.next()) {
            int userId = idRows.getInt("user_id");
            users.add(get(userId));
        }
        return users;
    }

    @Override
    public void delete(int id) {
        String sqlCheckQuery = "SELECT user_id FROM users WHERE user_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlCheckQuery, id);
        if (!userRows.next())
            throw new UserNotFoundException(id);
        jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }
}
