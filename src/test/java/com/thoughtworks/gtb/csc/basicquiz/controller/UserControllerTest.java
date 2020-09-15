package com.thoughtworks.gtb.csc.basicquiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_create_user_when_post_user_given_user_info() throws Exception {
        User user = User.builder().name("chen").age(22).avatar("https://www.baidu.com").description("test").build();
        mockMvc.perform(
            post("/users").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void should_throw_exception_when_post_user_given_illegal_user_info() throws Exception {
        User user = User.builder().name("chen").age(1).avatar("https://www.baidu.com").description("test").build();
        mockMvc.perform(
            post("/users").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.message").value("年龄需要在17 - 99岁之间"));
    }

    @Test
    public void should_get_users_education_list_when_get_educations_given_user_id() throws Exception {
        mockMvc.perform(get("/users/1/educations")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_throw_exception_when_get_educations_given_non_exist_user_id() throws Exception {
        mockMvc.perform(get("/users/1000/educations"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
    }
}
