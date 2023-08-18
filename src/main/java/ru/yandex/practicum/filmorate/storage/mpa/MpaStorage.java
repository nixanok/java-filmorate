package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Set;


public interface MpaStorage {
    Mpa get(int id);

    Set<Mpa> getAll();
}
