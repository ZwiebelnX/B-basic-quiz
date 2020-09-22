package com.thoughtworks.gtb.csc.basicquiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;
import com.thoughtworks.gtb.csc.basicquiz.model.exception.UserNotFoundException;
import com.thoughtworks.gtb.csc.basicquiz.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    public void reset() {
        Mockito.reset(userService);
    }

    @Test
    public void should_create_user_when_post_user_given_user_info() throws Exception {
        User user = User.builder()
            .name("chen")
            .age(22)
            .avatar("https://www.baidu.com")
            .description("test")
            .build();
        when(userService.createUser(user)).thenReturn(user);
        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        verify(userService).createUser(user);
    }

    @Test
    public void should_throw_exception_when_post_user_given_illegal_user_info() throws Exception {
        User user = User.builder()
            .name("chen")
            .age(1)
            .avatar("https://www.baidu.com")
            .description("test")
            .build();
        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.message").value("年龄需要在17 - 99岁之间"));
        verify(userService, times(0)).getUser(anyLong());
    }

    @Test
    public void should_get_users_education_list_when_get_educations_given_user_id() throws Exception {
        List<Education> educationList = new ArrayList<>();
        educationList.add(Education.builder().title("test education 1").build());
        educationList.add(Education.builder().title("test education 2").build());
        when(userService.getUsersEducations(anyLong())).thenReturn(educationList);
        mockMvc.perform(get("/users/1/educations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
        verify(userService).getUsersEducations(anyLong());
    }

    @Test
    public void should_get_user_when_get_user_given_user_id() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(
            User.builder().id(1L).name("Chen Sicong").age(22).avatar("").description("test").build());
        mockMvc.perform(get("/users/1/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Chen Sicong"));
        verify(userService).getUser(anyLong());
    }

    @Test
    public void should_throw_exception_when_get_user_given_non_exist_user_id() throws Exception {
        when(userService.getUser(1000)).thenThrow(new UserNotFoundException(1000));

        mockMvc.perform(get("/users/1000"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
        verify(userService, times(1)).getUser(anyLong());
    }

    @Test
    public void should_throw_exception_when_get_educations_given_non_exist_user_id() throws Exception {
        when(userService.getUsersEducations(anyLong())).thenThrow(new UserNotFoundException(1000L));
        mockMvc.perform(get("/users/1000/educations"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
        verify(userService, times(1)).getUsersEducations(anyLong());
    }

    @Test
    public void should_add_users_education_when_post_education_given_user_id_and_education_info()
        throws Exception {
        Education education = Education.builder()
            .title("test education")
            .description("test")
            .year(2008)
            .build();

        when(userService.addUserEducations(anyLong(), any(Education.class))).thenReturn(education);

        mockMvc.perform(post("/users/1/educations").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(education)))
            .andExpect(status().isCreated());

        verify(userService).addUserEducations(anyLong(), any(Education.class));
    }

    @Test
    public void should_throw_exception_when_post_education_given_non_exist_user_id() throws Exception {
        Education education = Education.builder()
            .title("test education")
            .description("test")
            .year(2008)
            .build();

        when(userService.addUserEducations(anyLong(), any(Education.class))).thenThrow(new UserNotFoundException(1000L));

        mockMvc.perform(post("/users/1000/educations").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(education)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));

        verify(userService, times(1)).addUserEducations(anyLong(), any(Education.class));
    }

    @Test
    public void should_throw_exception_when_post_education_given_illegal_education_info() throws Exception {
        Education education = Education.builder()
            .title("test education")
            .description("test")
            .year(-1)
            .build();

        mockMvc.perform(post("/users/1/educations").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(education)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.message").value("年份需要在1900 - 2100年之间"));

        verify(userService, times(0)).addUserEducations(anyLong(), any(Education.class));
    }

}
