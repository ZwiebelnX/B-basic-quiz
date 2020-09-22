package com.thoughtworks.gtb.csc.basicquiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.csc.basicquiz.model.Education;
import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_create_user_when_post_user_given_user_info() throws Exception {
        User user = User.builder()
            .name("chen")
            .age(22)
            .avatar("https://www.baidu.com")
            .description("test")
            .build();
        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
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
    }

    @Test
    public void should_get_users_education_list_when_get_educations_given_user_id() throws Exception {
        User user = User.builder().name("chen").age(22).avatar("https://www.baidu.com").description("test").build();
        String returnString = mockMvc.perform(
            post("/users").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        User returnUser = objectMapper.readValue(returnString, User.class);
        mockMvc.perform(get("/users/" + returnUser.getId() + "/educations"))
            .andExpect(status().isOk());
    }

    @Test
    public void should_get_user_when_get_user_given_user_id() throws Exception {
        User user = User.builder().name("chen").age(22).avatar("https://www.baidu.com").description("test").build();
        String returnString = mockMvc.perform(
            post("/users").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        User returnUser = objectMapper.readValue(returnString, User.class);
        mockMvc.perform(get("/users/" + returnUser.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("chen"));
    }

    @Test
    public void should_throw_exception_when_get_user_given_non_exist_user_id() throws Exception {
        mockMvc.perform(get("/users/1000"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
    }

    @Test
    public void should_throw_exception_when_get_educations_given_non_exist_user_id() throws Exception {
        mockMvc.perform(get("/users/1000/educations"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
    }

    @Test
    public void should_add_users_education_when_post_education_given_user_id_and_education_info()
        throws Exception {
        Education education = Education.builder()
            .title("test education")
            .description("test")
            .year(2008)
            .build();

        User user = User.builder().name("chen").age(22).avatar("https://www.baidu.com").description("test").build();
        String returnString = mockMvc.perform(
            post("/users").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        User returnUser = objectMapper.readValue(returnString, User.class);
        mockMvc.perform(post("/users/" + returnUser.getId() + "/educations").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(education)))
            .andExpect(status().isCreated());
    }

    @Test
    public void should_throw_exception_when_post_education_given_non_exist_user_id() throws Exception {
        Education education = Education.builder()
            .title("test education")
            .description("test")
            .year(2008)
            .build();

        mockMvc.perform(post("/users/1000/educations").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(education)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ID为<1000>的用户未找到"));
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
    }
}
