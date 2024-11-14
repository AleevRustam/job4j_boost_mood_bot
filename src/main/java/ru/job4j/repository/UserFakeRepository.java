package ru.job4j.repository;

import org.springframework.context.annotation.Profile;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("test")
public class UserFakeRepository implements UserRepository {
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findByClientId(Long clientId) {
        return userMap.get(clientId);
    }

    public void save(User user) {
        userMap.put(user.getClientId(), user);
    }
}
