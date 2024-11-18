package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Genre.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BookServiceSearchUnitTest {

    private AutoCloseable mocks;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private List<Book> mockBooks;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);

        Author mockAuthor1 = new Author();
        mockAuthor1.setFirstName("J.K.");
        mockAuthor1.setLastName("Rowling");

        Author mockAuthor2 = new Author();
        mockAuthor2.setFirstName("Stephen");
        mockAuthor2.setLastName("King");

        Genre mockGenre1 = new Genre();
        mockGenre1.setName("Fantasy");

        Genre mockGenre2 = new Genre();
        mockGenre2.setName("Horror");

        Book book1 = new Book();
        book1.setTitle("Harry Potter and the Sorcerer's Stone");
        book1.setAuthor(mockAuthor1);
        book1.setPublicationYear(Year.of(1999));
        book1.setGenres(List.of(mockGenre1));

        Book book2 = new Book();
        book2.setTitle("Harry Potter and the Chamber of Secrets");
        book2.setAuthor(mockAuthor1);
        book2.setPublicationYear(Year.of(1999));
        book2.setGenres(List.of(mockGenre1));

        Book book3 = new Book();
        book3.setTitle("Cujo");
        book3.setAuthor(mockAuthor2);
        book3.setPublicationYear(Year.of(1999));
        book3.setGenres(List.of(mockGenre2));

        List<Book> mockBooks = List.of(book1, book2, book3);
        when(bookRepository.findAll()).thenReturn(mockBooks);

    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void search() {
        //act
        List<Book> books = bookService.search("horror");
        //assert
        assertEquals(1, books.size());
    }

    @Test
    void advancedSearch() {
        List<Book> books = bookService.advancedSearch("harry","","king","", null);
        assertEquals(0, books.size());
    }
}