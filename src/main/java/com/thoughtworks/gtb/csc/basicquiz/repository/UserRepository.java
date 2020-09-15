package com.thoughtworks.gtb.csc.basicquiz.repository;

import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserRepository {

    private static final Map<Integer, User> userMap = new ConcurrentHashMap<>();

    private static final AtomicInteger nextId = new AtomicInteger(1);

    public User save(User user) {
        if (!userMap.containsKey(user.getId())) {
            user.setId(nextId.getAndIncrement());
        }
        user.setEducationList(null);
        userMap.put(user.getId(), user);
        return user;
    }
}
