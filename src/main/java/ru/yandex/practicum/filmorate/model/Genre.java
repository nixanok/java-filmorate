package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre {
    private int id;

    public Genre() {

    }

    public Genre(int id) {
        this.id = id;
    }

    public String getName() {
        switch (id) {
            case 1:
                return "Комедия";
            case 2:
                return "Драма";
            case 3:
                return "Мультфильм";
            case 4:
                return "Триллер";
            case 5:
                return "Документальный";
            case 6:
                return "Боевик";
            default:
                throw new IllegalArgumentException();
        }
    }

}

