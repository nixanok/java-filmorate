package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMpaService implements MpaService {

    @Autowired
    private final MpaStorage mpaStorage;

    @Override
    public Mpa get(int id) {
        Mpa mpa = mpaStorage.get(id);
        log.info("Getting mpa. Id = {}", id);
        return mpa;
    }

    @Override
    public List<Mpa> getAll() {
        List<Mpa> allMpa = mpaStorage.getAll();
        log.info("Getting allMpa. Size = {}", allMpa.size());
        return allMpa;
    }
}
