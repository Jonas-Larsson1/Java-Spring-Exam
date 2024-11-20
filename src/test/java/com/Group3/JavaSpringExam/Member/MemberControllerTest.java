package com.Group3.JavaSpringExam.Member;

import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addMember() throws Exception {
        String dummyMember = "{\"firstName\": \"Bob\", \"lastName\": \"Jones\", \"email\": \"bob@jones.com\" }";

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummyMember))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Jones"))
                .andExpect(jsonPath("$.email").value("bob@jones.com"));
    }

    private Long addMemberAndGetId() throws Exception {
        String dummyMember = "{\"firstName\": \"Bob\", \"lastName\": \"Jones\", \"email\": \"bob@jones.com\" }";
        MvcResult result = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummyMember))
                .andExpect(jsonPath("$.id").value(notNullValue()))  // Check that ID is present
                .andReturn();

        return JsonPath.parse(result.getResponse().getContentAsString()).read("$.id", Long.class);
    }

    @Test
    void updateMember() throws Exception {
        Long id = addMemberAndGetId();

        String dummyMember = "{\"firstName\": \"Bobby\", \"lastName\": \"Barnes\", \"email\": \"bobby@barnes.org\" }";

        mockMvc.perform(put("/members/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(dummyMember))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bobby"))
                .andExpect(jsonPath("$.lastName").value("Barnes"))
                .andExpect(jsonPath("$.email").value("bobby@barnes.org"));

    }
}