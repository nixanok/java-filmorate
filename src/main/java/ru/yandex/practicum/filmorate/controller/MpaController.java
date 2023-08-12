package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
@Slf4j
public class MpaController {

    @Autowired
    private final MpaService mpaService;

    @GetMapping("/{id}")
    public Mpa get(@PathVariable int id) {
        log.debug("Request \"getMpa\" is called.");
        return mpaService.get(id);
    }

    @GetMapping
    public List<Mpa> getAll() {
        log.debug("Request \"getAllMpa\" is called.");
        return mpaService.getAll();
    }
}
