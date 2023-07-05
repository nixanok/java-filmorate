package ru.yandex.practicum.filmorate.controller.responseError;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseError {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String path;
}
