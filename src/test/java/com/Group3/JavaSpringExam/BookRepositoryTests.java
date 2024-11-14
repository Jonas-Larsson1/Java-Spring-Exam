package com.Group3.JavaSpringExam;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.Book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByAuthorFirstNameAndLastNameIgnoreCase() {
        // Arrange: skapa en bok och författare för testet
        Author author = new Author();
        author.setFirstName("J.K.");
        author.setLastName("Rowling");

        Book book = new Book();
        book.setTitle("Harry Potter and the Philosopher's Stone");
        book.setAuthor(author);

        // Spara boken till databasen
        bookRepository.save(book);

        // Act: Utför sökningen med författarens namn
        List<Book> books = bookRepository.findByAuthor_FirstNameIgnoreCaseAndAuthor_LastNameIgnoreCase("j.k.", "rowling");

        // Assert: Kontrollera att vi fick tillbaka en bok och att titeln är korrekt
        assertEquals(1, books.size());
        assertEquals("Harry Potter and the Philosopher's Stone", books.get(0).getTitle());
    }
}
