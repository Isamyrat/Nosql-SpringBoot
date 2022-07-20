package com.example.nosql.controller;

import com.example.nosql.jwt.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.nosql.util.ConstantsHolder.SECRET;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class AbstractWebTests {
    @Autowired
    protected Gson gson;

    @Autowired
    protected MockMvc mockMvc;

    protected final String AUDITOR_TOKEN = JwtUtils.generateToken("auditor_test", SECRET, Integer.MAX_VALUE);
    protected final String ADMIN_TOKEN = JwtUtils.generateToken("admin_test", SECRET, Integer.MAX_VALUE);

    protected final String USER_PASSWORD = "ac8fd58as6dgf584";

}
