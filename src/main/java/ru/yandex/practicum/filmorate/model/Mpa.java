package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Mpa {
    private int id;

    public Mpa() {}

    public Mpa(int id) {
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
                return "NC-17";
            default:
                throw new IllegalArgumentException();
        }
    }
}






