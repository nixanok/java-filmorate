package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class GenreDBStorage implements GenreStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(int id) {
        String sqlQuery = "SELECT * FROM genre WHERE genre_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rows.next()) {
            throw new GenreNotFoundException(id);
        }
        return new Genre(rows.getInt("genre_id"));
    }

    @Override
    public Set<Genre> getAll() {
        String sqlQuery = "SELECT * FROM genre";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery);
        Set<Genre> genres = new LinkedHashSet<>();
        while (rows.next()) {
            Genre genre = new Genre(rows.getInt("genre_id"));
            genres.add(genre);
        }
        return genres;
    }
}
