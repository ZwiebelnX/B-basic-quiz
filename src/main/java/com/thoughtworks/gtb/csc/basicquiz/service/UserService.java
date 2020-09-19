package com.thoughtworks.gtb.csc.basicquiz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.model.exception.UserNotFoundException;
import com.thoughtworks.gtb.csc.basicquiz.repository.EducationRepository;
import com.thoughtworks.gtb.csc.basicquiz.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class UserService {

    private final EducationRepository educationRepository;

    private final UserRepository userRepository;

    public UserService(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    // GTB: + 从外部文件加载默认数据
    // GTB: - 加载默认数据的代码可以转移到 BasicquizApplication 中去，通过 ApplicationRunner 来做

    // GTB: - 如果@Transactional暂时不需要，就删掉
    @Transactional
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
                education.setUser(user);
                educationRepository.save(education);
            });
        });
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        // GTB: 可以再了解一下 Optional API，换种写法
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        return userOptional.get();
    }

    public List<Education> getUsersEducations(long userId) {
        return getUser(userId).getEducationList();
    }

    @Transactional
    public Education addUserEducations(long userId, Education education) {
        User user = getUser(userId);
        education.setUser(user);
        return educationRepository.save(education);
    }

}
