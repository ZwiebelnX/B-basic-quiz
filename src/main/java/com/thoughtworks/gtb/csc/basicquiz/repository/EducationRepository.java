package com.thoughtworks.gtb.csc.basicquiz.repository;

import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EducationRepository {

    private static final Map<Integer, Education> educationMap = new ConcurrentHashMap<>();

    private static final AtomicInteger nextId = new AtomicInteger(1);

    public Education save(Education education) {
        if (!educationMap.containsKey(education.getId())) {
            education.setId(nextId.getAndIncrement());
        }
        educationMap.put(education.getId(), education);
        return education;
    }
}
