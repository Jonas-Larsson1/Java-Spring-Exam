package com.Group3.JavaSpringExam.Book;


import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createBookWithAuthor() throws Exception {
        String dummyBook = "{\"book\": {\"title\": \"Processen\", \"publicationYear\": 1925}, \"author\" : {\"firstName\": \"Franz\", \"lastName\": \"Kafka\", \"birthDate\" : \"1883-06-03\"}}";

                mockMvc.perform(post("/book/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dummyBook))
                        .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("Processen"))
                        .andExpect(jsonPath("$.publicationYear").value(1925))
                        .andExpect(jsonPath("$.available").value(true))
                        .andExpect(jsonPath("$.author.firstName").value("Franz"))
                        .andExpect(jsonPath("$.author.lastName").value("Kafka"))
                        .andExpect(jsonPath("$.author.birthDate").value("1883-06-03"));
    }

    private Long createBookAndReturnId() throws Exception {
        String dummyBook = "{\"book\": {\"title\": \"Processen\", \"publicationYear\": 1925}, \"author\" : {\"firstName\": \"Franz\", \"lastName\": \"Kafka\", \"birthDate\" : \"1883-06-03\"}}";

        MvcResult testResult =  mockMvc.perform(post("/book/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummyBook))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andReturn();

        return JsonPath.parse(testResult.getResponse().getContentAsString()).read("$.id", Long.class);
    }


    @Test
    void deleteBook() throws Exception {
        Long id = createBookAndReturnId();

        mockMvc.perform(delete("/book/{id}" , id))
                .andExpect(status().isNoContent());
    }

}