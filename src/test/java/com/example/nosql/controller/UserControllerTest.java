package com.example.nosql.controller;

import com.example.nosql.dto.UserDto;
import com.example.nosql.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.example.nosql.controller.UserController.USER_URL;
import static com.example.nosql.jwt.JwtUtils.BEARER_PREFIX;
import static com.example.nosql.util.ConstantsHolder.SECRET;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractWebTests {

    private static final String USER = "USER";
    private static final String ADMIN_TEST = "admin_test";
    private static final String INVALID_USER_NAME = "marina_test";

    @Test
    void testAddUserWithExistUsername() throws Exception {
        String data = gson.toJson(new UserDto(ADMIN_TEST, USER_PASSWORD, USER));

        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))

            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUserWithExistUsername() throws Exception {
        String data = gson.toJson(new UserDto(ADMIN_TEST, USER_PASSWORD, USER));

        this.mockMvc.perform(put(USER_URL + "/3")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))

            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testFindAllUsers() throws Exception {
        this.mockMvc.perform(get(USER_URL)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username", is("auditor_test")))
            .andExpect(jsonPath("$[1].username", is("admin_test")));
    }

    @Test
    void testFindUserById() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/admin_test")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void testWithNullUsername() throws Exception {
        String data = gson.toJson(new UserDto("", USER_PASSWORD, USER));

        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testWithNullPassword() throws Exception {
        String data = gson.toJson(new UserDto(INVALID_USER_NAME, "", USER));

        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testWithNullRole() throws Exception {
        String data = gson.toJson(new UserDto(INVALID_USER_NAME, USER_PASSWORD, ""));

        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testWhenUnauthorized() throws Exception {
        this.mockMvc.perform(get(USER_URL))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testWithNonFoundRole() throws Exception {
        String data = gson.toJson(new UserDto(INVALID_USER_NAME, USER_PASSWORD, "asd"));

        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testExpiredToken() throws Exception {
        String expiredToken = JwtUtils.generateToken(ADMIN_TEST, SECRET, 1);
        this.mockMvc.perform(get(USER_URL)
                                 .header(AUTHORIZATION, BEARER_PREFIX + expiredToken))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }
    @Test
    void testWhenNotAdmin() throws Exception {
        this.mockMvc.perform(get(USER_URL)
                                 .header(AUTHORIZATION, BEARER_PREFIX + AUDITOR_TOKEN))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

   /* todo Need to fix
    @Test
    void testAddValidUser() throws Exception {
        String data = gson.toJson(new UserDto(USER_NAME, USER_PASSWORD, USER));
        this.mockMvc.perform(post(USER_URL)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(USER_NAME)));
    }
        @Test
    void testUpdateUser() throws Exception {
        String data = gson.toJson(new UserDto(USER_NAME_FOR_UPDATE, USER_PASSWORD, USER));

        this.mockMvc.perform(put(USER_URL + "/3")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(USER_NAME_FOR_UPDATE)));
    }
    */


}
