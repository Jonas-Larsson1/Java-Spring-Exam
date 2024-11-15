package com.Group3.JavaSpringExam.Book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BookServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getBookById() {
    }

    @Test
    void saveBook() {
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