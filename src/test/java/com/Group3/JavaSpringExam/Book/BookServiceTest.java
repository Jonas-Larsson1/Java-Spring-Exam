package com.Group3.JavaSpringExam.Book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BookServiceTest {

    private AutoCloseable mocks;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void addBook() {
    }

    @Test
    void addCompleteBook() {
    }

    @Test
    void getBook() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book result = bookService.getBook(1L);

        assertEquals(1L, result.getId());

        verify(bookRepository).findById(1L);
    }

    @Test
    void testGetBook() {
    }

    @Test
    void modifyBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        Book modifiedBook = new Book();
        modifiedBook.setTitle("New Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(modifiedBook);

        Book bookResult = bookService.modifyBook(1L, modifiedBook);

        assertEquals("New Title", bookResult.getTitle());
        verify(bookRepository).save(book);
    }
}