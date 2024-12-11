package com.Group3.JavaSpringExam.Book;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

  private final BookService bookService;


  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
    Book createdBook = bookService.addBook(book);
    return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
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

  @PreAuthorize("hasRole('LIBRARIAN')")
  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
    return bookService.modifyBook(id, book);
  }

  @PreAuthorize("hasRole('LIBRARIAN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable Long id) {

    if(bookService.removeBook(id)) {
      return new ResponseEntity<>("Book succesfully deleted", HttpStatus.NO_CONTENT);
    }else{
      return new ResponseEntity<>("Book is not available", HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/search")
  public List<Book> searchBooks(
          @RequestParam(name = "searchKeywords", required = false) String searchKeywords) {
    return bookService.search(searchKeywords);
  }

  @GetMapping("/advancedsearch")
  public ResponseEntity<List<Book>> advancedSearch(
          @RequestParam(name = "title", required = false) String title,
          @RequestParam(name = "authorFirstName", required = false) String authorFirstName,
          @RequestParam(name = "authorLastName", required = false) String authorLastName,
          @RequestParam(name = "genreName", required = false) String genreName,
          @RequestParam(name = "publicationYear", required = false) Year publicationYear) {

    List<Book> books = bookService.advancedSearch(title, authorFirstName, authorLastName, genreName, publicationYear);

    if (books.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(books);
    }
    return ResponseEntity.ok(books);
  }


}
