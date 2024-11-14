package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Author.AuthorRepository;
import com.Group3.JavaSpringExam.Author.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
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
    return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
  }

  public List<Book> getBook() {
    return bookRepository.findAll();
  }

  public Book modifyBook(Long id, Book updateBookData) {
    Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

    modelMapper.map(updateBookData, existingBook);
    return bookRepository.save(existingBook);
  }

  public Boolean removeBook(Long id){
    Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
    if(existingBook.isAvailable()){
      bookRepository.delete(existingBook);
      return true;
    }else{
      return false;
    }
  }

  public List<Book> advancedSearch(String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear) {
    // Kollar om alla sökparametrar är ifyllda och anropar den mest specifika sökmetoden
    if (title != null && authorFirstName != null && authorLastName != null && genreName != null && publicationYear != null) {
      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(
              title, authorFirstName, authorLastName, genreName, publicationYear);
    }
    // Kollar om endast författarens förnamn är ifyllt
    else if (authorFirstName != null) {
      return bookRepository.findByAuthor_FirstNameContainingIgnoreCase(authorFirstName);
    }
    // Kollar om endast författarens efternamn är ifyllt
    else if (authorLastName != null) {
      return bookRepository.findByAuthor_LastNameContainingIgnoreCase(authorLastName);
    }
    // Kollar om endast författarens förnamn och efternamn är ifyllda och söker på dessa
    else if (authorFirstName != null && authorLastName != null) {
      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(authorFirstName, authorLastName);
    }
    // Kollar om endast titel, författarens förnamn och efternamn är ifyllda
    else if (title != null && authorFirstName != null && authorLastName != null) {
      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(
              title, authorFirstName, authorLastName);
    }
    // Kollar om endast titel och genre är ifyllda
    else if (title != null && genreName != null) {
      return bookRepository.findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(title, genreName);
    }
    // Kollar om endast titel är ifyllt och söker på titel
    else if (title != null) {
      return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    // Kollar om endast genre är ifyllt och söker på genre
    else if (genreName != null) {
      return bookRepository.findByGenresNameIgnoreCase(genreName);
    }
    // Kollar om endast publiceringsår är ifyllt och söker på publiceringsår
    else if (publicationYear != null) {
      return bookRepository.findByPublicationYear(publicationYear);
    }
    // Om inga kriterier är ifyllda, returnerar en tom lista
    return List.of(); // Returnerar en tom lista om inga kriterier anges
  }


}
