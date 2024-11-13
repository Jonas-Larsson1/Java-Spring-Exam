package com.Group3.JavaSpringExam.Book;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
  private final BookRepository bookRepository;


  @Autowired
  public BookController(BookService bookService, BookRepository bookRepository) {
    this.bookService = bookService;
    this.bookRepository = bookRepository;
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

  // Sök efter böcker med exakt titel
  @GetMapping("/searchByTitle")
  public List<Book> searchBooksByTitle(@RequestParam String title) {
    return bookRepository.findByTitle(title);
  }

  // Sök efter böcker baserat på författarens förnamn och efternamn
  @GetMapping("/searchByAuthor")
  public List<Book> searchBooksByAuthor(@RequestParam String firstName, @RequestParam String lastName) {
    return bookRepository.findByAuthor_FirstNameAndAuthor_LastName(firstName, lastName);
  }

  // Sök efter böcker baserat på genre
  @GetMapping("/searchByGenre")
  public List<Book> searchBooksByGenre(@RequestParam String genreName) {
    return bookRepository.findByGenresName(genreName);
  }

  // Sök efter böcker som innehåller ett nyckelord i titeln
  @GetMapping("/searchByKeyword")
  public List<Book> searchBooksByKeyword(@RequestParam String keyword) {
    return bookRepository.findByTitleContaining(keyword);
  }

  // Sök efter böcker som är tillgängliga (eller ej) och sortera resultatet
  @GetMapping("/searchByAvailability")
  public List<Book> searchBooksByAvailability(@RequestParam boolean available,
                                              @RequestParam(required = false) String sortBy) {
    Sort sort = Sort.by(Sort.Direction.ASC, sortBy != null ? sortBy : "title");
    return bookRepository.findByAvailable(available, sort);
  }

  // Hämta den första boken som matchar ett nyckelord i titeln
  @GetMapping("/firstBookByKeyword")
  public Book getFirstBookByKeyword(@RequestParam String keyword) {
    return bookRepository.findFirstByTitleContaining(keyword);
  }

  // Kontrollera om en bok med en viss titel finns
  @GetMapping("/existsByTitle")
  public boolean checkBookExistsByTitle(@RequestParam String title) {
    return bookRepository.existsByTitle(title);
  }

  // Räkna antalet tillgängliga eller utlånade böcker
  @GetMapping("/countByAvailability")
  public long countBooksByAvailability(@RequestParam boolean available) {
    return bookRepository.countByAvailable(available);
  }

  @GetMapping("/advancedsearch") //kolla upp grejen med response entity
  public List<Book> advancedSearch(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String authorFirstName,
          @RequestParam(required = false) String authorLastName,
          @RequestParam(required = false) String genreName,
          @RequestParam(required = false) Year publicationYear) {

    return bookService.advancedSearch(title, authorFirstName, authorLastName, genreName, publicationYear);
  }

//  @DeleteMapping
//  public Book deleteBook() {
//
//  }

}
