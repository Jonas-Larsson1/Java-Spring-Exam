package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Author.AuthorRepository;
import com.Group3.JavaSpringExam.Author.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final ModelMapper modelMapper;
  private final AuthorRepository authorRepository;
  private final AuthorService authorService;

  @Autowired
  public BookService(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, AuthorService authorService) {
    this.bookRepository = bookRepository;
    this.modelMapper = modelMapper;
    this.authorRepository = authorRepository;
    this.authorService = authorService;
  }

  public Book addBook(Book book) {
    // a new book should always start off available (until a loan is created -
    // otherwise no-one will ever be able to borrow it)
    book.setAvailable(true);
    return bookRepository.save(book);
  }

  public Book addCompleteBook(Book book) {
    Author author = book.getAuthor();
    Optional<Author> existingAuthor = authorService.findByName(author);
      existingAuthor.ifPresentOrElse(book::setAuthor, () ->
        authorRepository.save(author));

    return bookRepository.save(book);
  }

  public Book getBook(Long id) {
    return bookRepository.findById(id).orElseThrow();
  }

  public List<Book> getBook() {
    return bookRepository.findAll();
  }

  public Book modifyBook(Long id, Book updateBookData) {
    Book existingBook = bookRepository.findById(id).orElseThrow();

    modelMapper.map(updateBookData, existingBook);
    return bookRepository.save(existingBook);
  }

  public List<Book> advancedSearch(String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear) {
    // Kollar om alla sökparametrar är ifyllda och anropar den mest specifika sökmetoden
    if (title != null && authorFirstName != null && authorLastName != null && genreName != null && publicationYear != null) {
      return bookRepository.findByTitleContainingAndAuthor_FirstNameContainingAndAuthor_LastNameContainingAndGenres_NameContainingAndPublicationYear(
              title, authorFirstName, authorLastName, genreName, publicationYear);
    }
    // Kollar om endast titel, författarens förnamn och efternamn är ifyllda
    else if (title != null && authorFirstName != null && authorLastName != null) {
      return bookRepository.findByTitleContainingAndAuthor_FirstNameContainingAndAuthor_LastNameContaining(
              title, authorFirstName, authorLastName);
    }
    // Kollar om endast titel och genre är ifyllda
    else if (title != null && genreName != null) {
      return bookRepository.findByTitleContainingAndGenres_NameContaining(title, genreName);
    }
    // Kollar om endast titel är ifyllt och söker på titel
    else if (title != null) {
      return bookRepository.findByTitleContaining(title);
    }
    // Kollar om endast författarens förnamn och efternamn är ifyllda och söker på dessa
    else if (authorFirstName != null && authorLastName != null) {
      return bookRepository.findByAuthor_FirstNameContainingAndAuthor_LastNameContaining(authorFirstName, authorLastName);
    }
    // Kollar om endast genre är ifyllt och söker på genre
    else if (genreName != null) {
      return bookRepository.findByGenresName(genreName);
    }
    // Kollar om endast publiceringsår är ifyllt och söker på publiceringsår
    else if (publicationYear != null) {
      return bookRepository.findByYear(publicationYear);
    }
    // Om inga kriterier är ifyllda, returnerar en tom lista
    return List.of(); // Returnerar en tom lista om inga kriterier anges
  }


}
