package com.Group3.JavaSpringExam.Book;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Mock
    private BookDTO bookDTO;



    @Test
    void createBookWithAuthor() throws Exception {
        Book book = new Book();
        book.setId(1L);
        bookDTO.setBook(book);
        when(bookDTO.returnCompleteBook()).thenReturn(book);
        when(bookService.addCompleteBook(book)).thenReturn(book);

                mockMvc.perform(post("/book/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"book\": {\"id\": 1}}"))
                        .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(bookService).addCompleteBook(any(Book.class));
    }


    @Test
    void readBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void createBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        when(bookService.addBook(book)).thenReturn(book);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        verify(bookService).addBook(any(Book.class));
    }
}