package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserDBStorage implements  UserStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        int id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        if (!contains(user.getId())) {
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
    }

    @Override
    public boolean contains(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT user_id FROM users WHERE user_id = ?", id);
        return userRows.next();
    }

    @Override
    public User get(int id) {
        if (!contains(id)) {
            throw new UserNotFoundException(id);
        }
        String queryUser = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(queryUser, this::mapRowToUser, id);
    }

    @Override
    public Set<User> getAll() {
        SqlRowSet usersRows = jdbcTemplate.queryForRowSet("SELECT * FROM users ORDER BY user_id");
        Set<User> users = new LinkedHashSet<>();
        while (usersRows.next()) {
            User user = User
                    .builder()
                    .id(usersRows.getInt("user_id"))
                    .login(usersRows.getString("login"))
                    .name(usersRows.getString("name"))
                    .email(usersRows.getString("email"))
                    .birthday(Objects.requireNonNull(usersRows.getDate("birthday")).toLocalDate())
                    .build();
            users.add(user);
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

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
