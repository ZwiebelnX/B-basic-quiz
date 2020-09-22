package com.thoughtworks.gtb.csc.basicquiz.service;

import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.model.exception.UserNotFoundException;
import com.thoughtworks.gtb.csc.basicquiz.repository.EducationRepository;
import com.thoughtworks.gtb.csc.basicquiz.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EducationRepository educationRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
         user = User.builder()
            .name("chen")
            .age(1)
            .avatar("https://www.baidu.com")
            .description("test")
            .build();

    }

    @Test
    public void should_insert_default_info_when_start() {
        userService.insertDefaultInfo();

        verify(userRepository, atLeastOnce()).save(any(User.class));
        verify(educationRepository, atLeastOnce()).save(any(Education.class));
    }

    @Test
    public void should_save_user_when_create_user_given_user_info() {
        userService.createUser(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void should_return_user_when_get_user_given_user_id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User returnUser = userService.getUser(1L);

        assertEquals(user, returnUser);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throw_exception_when_given_illegal_id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    public void should_get_user_educations_when_get_user_educations() {
        List<Education> educationList = new ArrayList<>();
        educationList.add(Education.builder().title("test education 1").build());
        educationList.add(Education.builder().title("test education 2").build());
        user.setEducationList(educationList);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        List<Education> returnList = userService.getUsersEducations(1L);

        assertEquals(educationList, returnList);
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void should_add_user_education_when_add_user_education_given_education_info() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Education education = Education.builder().title("test education 2").build();
        when(educationRepository.save(any())).thenReturn(education);

        Education returnEducation = userService.addUserEducations(1L, education);

        assertEquals(education, returnEducation);
        verify(educationRepository).save(any(Education.class));
    }


}
