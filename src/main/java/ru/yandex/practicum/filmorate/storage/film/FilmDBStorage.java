package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class FilmDBStorage implements FilmStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        int id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        film.setId(id);
        if (film.getGenres() != null) {
            putGenres(film.getId(), film.getGenres());
        }
        return film;
    }

    @Override
    public void update(Film film) {
        if (!contains(film.getId())) {
            throw new FilmNotFoundException(film.getId());
        }
        String sqlQueryForUpdate = "UPDATE films " +
                                   "SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
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
            putGenres(film.getId(), film.getGenres());
        }
    }

    @Override
    public boolean contains(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT film_id FROM films WHERE film_id = ?", id);
        return filmRows.next();
    }

    @Override
    public Film get(int id) {
        if (!contains(id)) {
            throw new FilmNotFoundException(id);
        }
        String queryFilm = "SELECT * FROM films WHERE film_id = ?";
        Film film = jdbcTemplate.queryForObject(queryFilm, this::mapRowToFilm, id);
        film.initGenres();
        for (Genre genre : getGenres(id)) {
            film.addGenre(genre);
        }
        return film;
    }

    @Override
    public List<Film> getSortedByLikes(int count) {
        String sqlQuery = "SELECT f.film_id, " +
                "f.name, " +
                "f.description, " +
                "f.release_date, " +
                "f.duration, " +
                "f.mpa_id, " +
                "fg.genre_id  " +
                "FROM films AS f " +
                "LEFT OUTER JOIN film_genre AS fg ON f.film_id=fg.film_id " +
                "WHERE f.film_id IN (SELECT f.film_id " +
                "FROM FILMS AS f " +
                "LEFT OUTER JOIN users_likes_films AS ulf ON f.film_id=ulf.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(ulf.film_id) DESC) " +
                "LIMIT ?";
        SqlRowSet sqlRowFilms = jdbcTemplate.queryForRowSet(sqlQuery, count);
        return new ArrayList<>(extractFilmsFromRows(sqlRowFilms));
    }

    @Override
    public Set<Film> getAll() {
        String sqlQuery = "SELECT * FROM films AS f " +
                          "LEFT OUTER JOIN film_genre AS fg ON f.film_id=fg.film_id " +
                          "ORDER BY film_id";
        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(sqlQuery);
        return extractFilmsFromRows(filmsRows);
    }

    private HashSet<Film> extractFilmsFromRows(SqlRowSet filmsRows) {
        HashMap<Integer, Film> films = new LinkedHashMap<>();
        while (filmsRows.next()) {
            int id = filmsRows.getInt("film_id");
            if (films.containsKey(id)) {
                Film film = films.get(id);
                extractGenre(filmsRows, film);
            } else {
                Film film = Film.builder()
                        .id(id)
                        .name(filmsRows.getString("name"))
                        .description(filmsRows.getString("description"))
                        .releaseDate(Objects.requireNonNull(filmsRows.getDate("release_date")).toLocalDate())
                        .duration(filmsRows.getInt("duration"))
                        .mpa(new Mpa(filmsRows.getInt("mpa_id")))
                        .build();
                film.initGenres();
                extractGenre(filmsRows, film);
                films.put(id, film);
            }
        }
        return new HashSet<>(films.values());
    }

    private void extractGenre(SqlRowSet filmsRows, Film film) {
        if (filmsRows.getString("genre_id") != null) {
            int genreId = Integer.parseInt(Objects.requireNonNull(filmsRows.getString("genre_id")));
            film.addGenre(new Genre(genreId));
        }
    }

    @Override
    public void delete(int id) {
        if (!contains(id)) {
            throw new FilmNotFoundException(id);
        }
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", id);
        jdbcTemplate.update("DELETE FROM films WHERE film_id = ?", id);
    }

    private void putGenres(int filmId, Set<Genre> genres) {
        String sqlQueryFilmGenre = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        for (Genre genre : genres) {
            jdbcTemplate.update(sqlQueryFilmGenre,
                    filmId,
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

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(new Mpa(resultSet.getInt("mpa_id")))
                .build();
    }

}
