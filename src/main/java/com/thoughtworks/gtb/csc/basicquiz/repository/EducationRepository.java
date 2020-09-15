package com.thoughtworks.gtb.csc.basicquiz.repository;

import com.thoughtworks.gtb.csc.basicquiz.model.Education;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class EducationRepository {

    private static final Map<Long, Education> educationMap = new ConcurrentHashMap<>();

    private static final AtomicLong nextId = new AtomicLong(1);

    public Education save(Education education) {
        if (!educationMap.containsKey(education.getId())) {
            education.setId(nextId.getAndIncrement());
        }
        educationMap.put(education.getId(), education);
        return education;
    }

    public List<Education> findByUserId(long userId) {
        return educationMap.values()
            .stream()
            .filter(education -> education.getUserId() == userId)
            .collect(Collectors.toList());
    }
}
