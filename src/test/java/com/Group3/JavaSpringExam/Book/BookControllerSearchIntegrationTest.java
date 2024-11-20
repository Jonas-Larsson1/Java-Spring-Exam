package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Author.AuthorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional //inte nödvändig här men har med för att minnas
class BookControllerSearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
    }


    @Test
    void searchBooks() throws Exception {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        author1 = authorRepository.save(author1);

        Book book3 = new Book();
        book3.setTitle("Finally Test book 3");
        book3.setAuthor(author1);
        book3 = bookRepository.save(book3);

        mockMvc.perform(get("/book/search")
                        .param("searchKeywords", book3.getTitle()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].title").value("Finally Test book 3"));
    }

    @Test
    void advancedSearch() throws Exception {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        author1 = authorRepository.save(author1);

        Book book3 = new Book();
        book3.setTitle("Finally Test book 3");
        book3.setAuthor(author1);
        book3 = bookRepository.save(book3);

        mockMvc.perform(get("/book/advancedsearch")
                        .param("title", book3.getTitle()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].title").value("Finally Test book 3"));
    }
}