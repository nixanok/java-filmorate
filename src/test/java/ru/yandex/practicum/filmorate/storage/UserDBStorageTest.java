package ru.yandex.practicum.filmorate.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDBStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDBStorageTest {
    private final UserDBStorage userStorage;

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testCrateAndGetUser() {
        User user = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();
        user = userStorage.add(user);
        assertEquals(user, userStorage.get(1));
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testUpdateUser() {
        User user = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();
        user = userStorage.add(user);
        user.setName("asd");
        userStorage.update(user);
        assertEquals(user, userStorage.get(1));
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testGetAllUsers() {
        Set<User> users = new LinkedHashSet<>();
        User user = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();
        user = userStorage.add(user);
        users.add(user);
        user = User
                .builder()
                .email("testEmail@gmail.com")
                .login("i_am_freeman")
                .name("name")
                .birthday(LocalDate.of(2000, 9, 24))
                .build();
        user = userStorage.add(user);
        users.add(user);
        assertEquals(users, userStorage.getAll());
    }

    @Test
    @Sql({"/sql/schema.sql", "/sql/data.sql"})
    public void testDeleteUser() {
        User user = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();
        user = userStorage.add(user);
        userStorage.delete(user.getId());
        assertThrows(UserNotFoundException.class, () -> userStorage.get(1));
    }

}
