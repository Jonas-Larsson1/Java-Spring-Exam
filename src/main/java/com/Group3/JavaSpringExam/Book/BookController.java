package com.Group3.JavaSpringExam.Book;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

  private final BookService bookService;


  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public Book createBook(@RequestBody @Valid Book book) {
    return bookService.addBook(book);
  }

  @PostMapping("/author")
  public Book createBookWithAuthor(@RequestBody BookDTO bookDTO) {
    return bookService.addCompleteBook(bookDTO.returnCompleteBook());
  }

  @GetMapping
  public List<Book> readAllBooks() {
    return bookService.getBook();
  }

  @GetMapping("/{id}")
  public Book readBook(@PathVariable Long id) {
    return bookService.getBook(id);
  }

//  @GetMapping
//  metod för att ta emot olika parametrar, t.ex. titel, författare, osv, använd parameters

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
    return bookService.modifyBook(id, book);
  }

//  @DeleteMapping
//  public Book deleteBook() {
//
//  }

}
