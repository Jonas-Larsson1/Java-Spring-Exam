package com.Group3.JavaSpringExam.Book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
    this.bookRepository = bookRepository;
    this.modelMapper = modelMapper;
  }

  public Book addBook(Book book) {
    // a new book should always start off available (until a loan is created -
    // otherwise no-one will ever be able to borrow it)
    book.setAvailable(true);
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

}
