package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

@Data
public class MPA {

    private int id;

    public MPA() {}

    public MPA(int id) {
        this.id = id;
    }

    public String getName() {
        switch (id) {
            case 1:
                return "G";
            case 2:
                return "PG";
            case 3:
                return "PG-13";
            case 4:
                return "R";
            case 5:
                return "NC_17";
            default:
                throw new IllegalArgumentException();
        }
    }
}






