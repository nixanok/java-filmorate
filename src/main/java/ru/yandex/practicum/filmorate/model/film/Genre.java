package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

@Data
public class Genre {
    private int id;
    public String getName() {
        switch (id) {
            case 1:
                return "Comedy";
            case 2:
                return "Drama";
            case 3:
                return "Cartoon";
            case 4:
                return "Thriller";
            case 5:
                return "Documentary";
            case 6:
                return "Action";
            default:
                throw new IllegalArgumentException();
        }
    }

}

