package ru.yandex.practicum.filmorate.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDBStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDBStorageTest {
    private final UserDBStorage userStorage;
    @Test
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
        user.addFriend(1);
        userStorage.update(user);
        assertEquals(user, userStorage.get(1));
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
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
