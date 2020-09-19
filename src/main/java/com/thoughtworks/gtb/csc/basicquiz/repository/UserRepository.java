package com.thoughtworks.gtb.csc.basicquiz.repository;

import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRepository {

    // GTB: + 考虑了并发编程的情况：ConcurrentHashMap、AtomicLong
    private static final Map<Long, User> userMap = new ConcurrentHashMap<>();

    private static final AtomicLong nextId = new AtomicLong(1);

    public User save(User user) {
        if (!userMap.containsKey(user.getId())) {
            user.setId(nextId.getAndIncrement());
        }
        user.setEducationList(null);
        userMap.put(user.getId(), user);
        return user;
    }

    public User findById(long userId) {
        return userMap.get(userId);
    }

    public boolean existsById(long userId) {
        return userMap.containsKey(userId);
    }
}
