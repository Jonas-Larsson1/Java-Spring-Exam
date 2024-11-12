package com.Group3.JavaSpringExam.Book;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor_FirstNameAndAuthor_LastName(String firstName, String lastName);
    List<Book> findByGenresName(String genreName);
    List<Book> findByTitleContaining(String keyword);
    List<Book> findByAvailable(boolean available, Sort sort);
    List<Book> findByYear(Year publicationYear);
    Book findFirstByTitleContaining(String keyword);
    boolean existsByTitle(String title);
    long countByAvailable(boolean available);

    List<Book> findByTitleContainingAndAuthor_FirstNameContainingAndAuthor_LastNameContaining(
            String title, String authorFirstName, String authorLastName);

    List<Book> findByTitleContainingAndGenres_NameContaining(String title, String genreName);

    List<Book> findByTitleContainingAndAuthor_FirstNameContainingAndAuthor_LastNameContainingAndGenres_NameContainingAndPublicationYear(
            String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear);
}
