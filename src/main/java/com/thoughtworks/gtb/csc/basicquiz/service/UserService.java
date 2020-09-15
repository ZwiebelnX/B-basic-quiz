package com.thoughtworks.gtb.csc.basicquiz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.repository.EducationRepository;
import com.thoughtworks.gtb.csc.basicquiz.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final EducationRepository educationRepository;

    private final UserRepository userRepository;

    public UserService(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void insertDefaultInfo() {
        List<User> userList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("default-info.json");
            userList = Arrays.asList(objectMapper.readValue(file, User[].class));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        userList.forEach(user -> {
            List<Education> educationList = user.getEducationList();
            userRepository.save(user);
            educationList.forEach(education -> {
                education.setUserId(user.getId());
                educationRepository.save(education);
            });
        });
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
