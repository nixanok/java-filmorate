package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Primary
public class FilmDBStorage implements FilmStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        String sqlQueryFilm = "INSERT INTO FILMS(name, description, release_date, duration, mpa_id) " +
                          "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getReleaseDate().toString());
            stmt.setLong(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        addGenresFromFilm(film);
        return film;
    }

    @Override
    public void update(Film film) {
        String sqlQuery = "SELECT film_id FROM films WHERE film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, film.getId());
        if (!filmRows.next()) {
            throw new FilmNotFoundException(film.getId());
        }
        String sqlQueryForUpdate = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_id = ?" +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQueryForUpdate,
                           film.getName(),
                           film.getDescription(),
                           film.getReleaseDate(),
                           film.getDuration(),
                           film.getMpa().getId(),
                           film.getId()
        );
        if (film.getGenres() != null) {
            jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", film.getId());
            addGenresFromFilm(film);
        }

        film.getUserLikes().addAll(getLikes(film.getId()));
        if (!film.getUserLikes().isEmpty()) {
            jdbcTemplate.update("DELETE FROM users_likes_films WHERE film_id = ?", film.getId());
            for (Integer userId : film.getUserLikes())
                jdbcTemplate.update("INSERT INTO users_likes_films (film_id, user_id) VALUES (?, ?)", film.getId(), userId);
        }
    }

    @Override
    public Film get(int id) {
        String sqlQuery = "SELECT * FROM films WHERE film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!filmRows.next()) {
            throw new FilmNotFoundException(id);
        }
        Film film = Film.builder()
                .id(filmRows.getInt("film_id"))
                .name(filmRows.getString("name"))
                .description(filmRows.getString("description"))
                .releaseDate(filmRows.getDate("release_date").toLocalDate())
                .duration(filmRows.getLong("duration"))
                .mpa(new Mpa(filmRows.getInt("mpa_id")))
                .build();
        for (Genre genre : getGenres(id)) {
            film.addGenre(genre);
        }
        film.initGenres();
        film.getUserLikes().addAll(getLikes(id));
        return film;
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT film_id FROM films ORDER BY film_id";
        SqlRowSet idRows = jdbcTemplate.queryForRowSet(sqlQuery);
        List<Film> films = new ArrayList<>();
        while (idRows.next()) {
            int filmId = idRows.getInt("film_id");
            films.add(get(filmId));
        }
        return films;
    }

    @Override
    public void delete(int id) {
        String sqlCheckQuery = "SELECT film_id FROM films WHERE film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlCheckQuery, id);
        if (!filmRows.next())
            throw new FilmNotFoundException(id);
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", id);
        jdbcTemplate.update("DELETE FROM films WHERE film_id = ?", id);
    }

    private void addGenresFromFilm(Film film) {
        String sqlQueryFilmGenre = "INSERT INTO FILM_GENRE(film_id, genre_id) " +
                "VALUES (?, ?)";
        if (film.getGenres() == null) {
            return;
        }
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQueryFilmGenre,
                    film.getId(),
                    genre.getId());
        }
    }

    private Set<Genre> getGenres(int id) {
        String sqlQuery = "SELECT genre_id FROM film_genre WHERE film_id = ?";
        SqlRowSet genresRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        Set<Genre> genres = new HashSet<>();
        while (genresRows.next()) {
            Genre genre = new Genre();
            genre.setId(genresRows.getInt("genre_id"));
            genres.add(genre);
        }
        return genres;
    }

    private Set<Integer> getLikes(int id) {
        SqlRowSet likesRows = jdbcTemplate.queryForRowSet("SELECT user_id FROM users_likes_films WHERE film_id = ?", id);
        Set<Integer> likes = new HashSet<>();
        while (likesRows.next()) {
            likes.add(likesRows.getInt("user_id"));
        }
        return likes;
    }

}
