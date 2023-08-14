package ru.yandex.practicum.filmorate.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

@RequiredArgsConstructor
@Service
@Slf4j
public class DefaultLikeService implements LikeService {

    @Autowired
    private final LikeStorage likeStorage;

    @Override
    public int getCount(int id) {
        int count = likeStorage.getCount(id);
        log.info("Getting film count likes with id = {}. Count = {}", id, count);
        return count;
    }

    @Override
    public void add(int filmId, int userId) {
        likeStorage.add(filmId, userId);
        log.info("Like on film with id = {} by user with id = {} added.", filmId, userId);
    }

    @Override
    public void remove(int filmId, int userId) {
        likeStorage.remove(filmId, userId);
        log.info("Like on film with id = {} by user with id = {} removed.", filmId, userId);
    }
}
