package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class MpaDBStorage implements MpaStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa get(int id) {
        String sqlQuery = "SELECT * FROM mpa WHERE mpa_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rows.next()) {
            throw new MpaNotFoundException(id);
        }
        return new Mpa(rows.getInt("mpa_id"));
    }

    @Override
    public Set<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM mpa";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery);
        Set<Mpa> allMpa = new LinkedHashSet<>();
        while (rows.next()) {
            Mpa mpa = new Mpa(rows.getInt("mpa_id"));
            allMpa.add(mpa);
        }
        return allMpa;
    }
}
