package com.Group3.JavaSpringExam.Book;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor_FirstNameContainingIgnoreCase(String firstName);
    List<Book> findByAuthor_LastNameContainingIgnoreCase(String firstName);
    List<Book> findByAuthor_FirstNameIgnoreCaseAndAuthor_LastNameIgnoreCase(String firstName, String lastName);

    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String firstName, String lastName);
    List<Book> findByGenresNameIgnoreCase(String genreName);
    List<Book> findByTitleContainingIgnoreCase(String keyword);
    List<Book> findByAvailable(boolean available, Sort sort);
    List<Book> findByPublicationYear(Year publicationYear);
    Book findFirstByTitleContainingIgnoreCase(String keyword);
    boolean existsByTitle(String title);
    long countByAvailable(boolean available);

    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(
            String title, String authorFirstName, String authorLastName);

    List<Book> findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String title, String genreName);

    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(
            String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear);
}
