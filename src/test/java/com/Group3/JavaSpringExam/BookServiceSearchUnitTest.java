package com.Group3.JavaSpringExam;

import com.Group3.JavaSpringExam.Author.Author;

import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.Book.BookRepository;
import com.Group3.JavaSpringExam.Book.BookService;
import com.Group3.JavaSpringExam.Genre.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceSearchUnitTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch() {
        // Mockdata
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setPublicationYear(Year.of(1997));
        Author author1 = new Author();
        author1.setFirstName("J.K.");
        author1.setLastName("Rowling");
        book1.setAuthor(author1);
        Genre genre1 = new Genre();
        genre1.setName("Fantasy");
        book1.setGenres(List.of(genre1));

        Book book2 = new Book();
        book2.setTitle("The Hobbit");
        book2.setPublicationYear(Year.of(1937));
        Author author2 = new Author();
        author2.setFirstName("J.R.R.");
        author2.setLastName("Tolkien");
        book2.setAuthor(author2);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        System.out.println("Mockade böcker: " + bookRepository.findAll());


        // Kör metoden vi testar
        List<Book> result = bookService.search("Harry");

        // Debugging-utskrifter
        System.out.println("Resultatstorlek: " + result.size());
        result.forEach(book -> System.out.println("Hittad bok: " + book.getTitle()));

        // Verifiering
        assertEquals(1, result.size(), "Sökresultatet borde innehålla exakt en bok.");
        assertEquals("Harry Potter", result.get(0).getTitle(), "Fel bok returnerades.");
    }



    @Test
    void testSearchMultipleKeywords() {
        // Mockdata
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setPublicationYear(Year.of(1997));
        Author author1 = new Author();
        author1.setFirstName("J.K.");
        author1.setLastName("Rowling");
        book1.setAuthor(author1);

        Book book2 = new Book();
        book2.setTitle("The Hobbit");
        book2.setPublicationYear(Year.of(1937));
        Author author2 = new Author();
        author2.setFirstName("J.R.R.");
        author2.setLastName("Tolkien");
        book2.setAuthor(author2);

        // Mocka repository-svaret
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Kör metoden vi testar
        List<Book> result = bookService.search("Harry Tolkien");

        // Verifiera att inga böcker returneras (ingen bok matchar båda sökorden)
        assertEquals(0, result.size());
    }


}
