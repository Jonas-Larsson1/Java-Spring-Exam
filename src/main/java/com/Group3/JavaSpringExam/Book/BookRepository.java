package com.Group3.JavaSpringExam.Book;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor_FirstNameAndAuthor_LastName(String firstName, String lastName);
    List<Book> findByGenresName(String genreName);
    List<Book> findByTitleContaining(String keyword);
    List<Book> findByAvailable(boolean available, Sort sort);
    Book findFirstByTitleContaining(String keyword);
    boolean existsByTitle(String title);
    long countByAvailable(boolean available);
}
