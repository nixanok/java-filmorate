package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultGenreService implements GenreService {

    @Autowired
    private final GenreStorage genreStorage;

    @Override
    public Genre get(int id) {
        Genre genre = genreStorage.get(id);
        log.info("Getting genre. Id = {}", id);
        return genre;
    }

    @Override
    public Set<Genre> getAll() {
        Set<Genre> genres = genreStorage.getAll();
        log.info("Getting genres. Size = {}", genres.size());
        return genres;
    }
}
