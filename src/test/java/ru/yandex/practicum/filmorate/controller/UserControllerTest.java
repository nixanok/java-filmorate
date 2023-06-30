package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
public class UserControllerTest {

    private UserController userController;
    @BeforeEach
    public void initController() {
        userController = new UserController();
    }
    @Test
    public void shouldCreateUserWithCorrectData() {
        User user = User
                .builder()
                .email("user@email.ru")
                .login("exampleLogin")
                .name("exampleName")
                .birthday(LocalDate.of(2001,  9, 24))
                .build();

        User loadedUser =  userController.createUser(user);

        assertEquals(loadedUser, user);
    }

}
