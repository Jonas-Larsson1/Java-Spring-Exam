package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Author.AuthorRepository;
import com.Group3.JavaSpringExam.Author.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    book.setAvailable(true);

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

}
