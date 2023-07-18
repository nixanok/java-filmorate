package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class ErrorResponse {
    private final String error;
    private final int code;
    private final String description;
    private final LocalDateTime timestamp;
}
