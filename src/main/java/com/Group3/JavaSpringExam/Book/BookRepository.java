package com.Group3.JavaSpringExam.Book;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // allt det här kan undvikas med JpaSpecificationExecutor
    // Bara titel
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Bara författarens förnamn
    List<Book> findByAuthor_FirstNameContainingIgnoreCase(String authorFirstName);

    // Bara författarens efternamn
    List<Book> findByAuthor_LastNameContainingIgnoreCase(String authorLastName);

    // Bara genre
    List<Book> findByGenres_NameContainingIgnoreCase(String genreName);

    // Bara publiceringsår
    List<Book> findByPublicationYear(Year publicationYear);

    // Titel och författarens förnamn
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCase(String title, String authorFirstName);

    // Titel och författarens efternamn
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String title, String authorLastName);

    // Titel och genre
    List<Book> findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String title, String genreName);

    // Titel och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndPublicationYear(String title, Year publicationYear);

    // Författarens förnamn och efternamn
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String authorFirstName, String authorLastName);

    // Författarens förnamn och genre
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String authorFirstName, String genreName);

    // Författarens förnamn och publiceringsår
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndPublicationYear(String authorFirstName, Year publicationYear);

    // Författarens efternamn och genre
    List<Book> findByAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String authorLastName, String genreName);

    // Författarens efternamn och publiceringsår
    List<Book> findByAuthor_LastNameContainingIgnoreCaseAndPublicationYear(String authorLastName, Year publicationYear);

    // Genre och publiceringsår
    List<Book> findByGenres_NameContainingIgnoreCaseAndPublicationYear(String genreName, Year publicationYear);

    // Titel, författarens förnamn och efternamn
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String title, String authorFirstName, String authorLastName);

    // Titel, författarens förnamn och genre
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String title, String authorFirstName, String genreName);

    // Titel, författarens förnamn och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndPublicationYear(String title, String authorFirstName, Year publicationYear);

    // Titel, författarens efternamn och genre
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String title, String authorLastName, String genreName);

    // Titel, författarens efternamn och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(String title, String authorLastName, Year publicationYear);

    // Titel, genre och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String title, String genreName, Year publicationYear);

    // Författarens förnamn, efternamn och genre
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String authorFirstName, String authorLastName, String genreName);

    // Författarens förnamn, efternamn och publiceringsår
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(String authorFirstName, String authorLastName, Year publicationYear);

    // Författarens förnamn, genre och publiceringsår
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String authorFirstName, String genreName, Year publicationYear);

    // Författarens efternamn, genre och publiceringsår
    List<Book> findByAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String authorLastName, String genreName, Year publicationYear);

    // Titel, författarens förnamn, efternamn och genre
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(String title, String authorFirstName, String authorLastName, String genreName);

    // Titel, författarens förnamn, efternamn och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(String title, String authorFirstName, String authorLastName, Year publicationYear);

    // Titel, författarens förnamn, genre och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String title, String authorFirstName, String genreName, Year publicationYear);

    // Titel, författarens efternamn, genre och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String title, String authorLastName, String genreName, Year publicationYear);

    // Författarens förnamn, efternamn, genre och publiceringsår
    List<Book> findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(String authorFirstName, String authorLastName, String genreName, Year publicationYear);

    // Titel, författarens förnamn, efternamn, genre och publiceringsår
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(
            String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear);



    List<Book> findByAvailable(boolean available, Sort sort);

    long countByAvailable(boolean available);
}
