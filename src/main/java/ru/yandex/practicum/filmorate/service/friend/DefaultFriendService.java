package ru.yandex.practicum.filmorate.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFriendService implements FriendService {

    @Autowired
    private final FriendStorage friendStorage;

    @Override
    public void addFriends(int fromId, int toId) {
        friendStorage.addFriends(fromId, toId);
        log.info("Adding friends. Sending id = {}. Receiving id = {}", fromId, toId);
    }

    @Override
    public void removeFriends(int fromId, int toId) {
        friendStorage.removeFriends(fromId, toId);
        log.info("Removing friends. First id = {}. Second id = {}", fromId, toId);
    }

    @Override
    public Set<User> getFriends(int id) {
        Set<User> friends = friendStorage.getFriends(id);
        log.info("Getting friends of id = {}. Size = {}", id, friends.size());
        return friends;
    }

    @Override
    public Set<User> getCommonFriends(int firstId, int secondId) {
        Set<User> firstFriends = friendStorage.getFriends(firstId);
        Set<User> secondFriends = friendStorage.getFriends(secondId);

        Set<User> commonFriends = new HashSet<>(firstFriends);
        commonFriends.retainAll(secondFriends);

        log.info("Getting common friends of id = {} and id = {}. Size = {}", firstId, secondId, commonFriends.size());
        return commonFriends;
    }
}
