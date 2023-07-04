package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Creating user with incorrect email.")
    public void shouldNotCreateUserWithIncorrectEmail() throws Exception {
        User user1 = User
                .builder()
                .email("myEmail_gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        User user2 = User
                .builder()
                .email("")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        pushUserAndExpect4xxCode(user1);
        pushUserAndExpect4xxCode(user2);
    }

    @Test
    @DisplayName("Creating user with incorrect login.")
    public void shouldNotCreateUserWithIncorrectLogin() throws Exception {
        User user1 = User
                .builder()
                .email("myEmail@gmail.com")
                .login("")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        User user2 = User
                .builder()
                .email("myEmail@gmail.com")
                .login("log in")
                .name("name")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        pushUserAndExpect4xxCode(user1);
        pushUserAndExpect4xxCode(user2);
    }

    @Test
    @DisplayName("Creating user with birthday in future.")
    public void shouldNotCreateUserWithBirthdayInFuture() throws Exception {
        User user = User
                .builder()
                .email("myEmail@gmail.com")
                .login("")
                .name("name")
                .birthday(LocalDate.of(2044, 9, 24))
                .build();

        pushUserAndExpect4xxCode(user);
    }

    @Test
    @DisplayName("Creating user with empty and null login.")
    public void shouldNameIsLoginWhenNameIsEmptyOrNull() throws Exception {
        User user1 = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .name("")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        User user2 = User
                .builder()
                .email("myEmail@gmail.com")
                .login("login")
                .birthday(LocalDate.of(2001, 9, 24))
                .build();

        User loadedUser1 = pushUserAndResponseUser(user1);
        User loadedUser2 = pushUserAndResponseUser(user2);

        assertEquals(loadedUser1.getLogin(), loadedUser1.getName());
        assertEquals(loadedUser2.getLogin(), loadedUser2.getName());
    }

    private User pushUserAndResponseUser(User user1) throws Exception {
        MvcResult mvcResult = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andDo(print())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
    }

    private void pushUserAndExpect4xxCode(User user) throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
