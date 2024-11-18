package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Author.AuthorRepository;
import com.Group3.JavaSpringExam.Author.AuthorService;
import com.Group3.JavaSpringExam.Loan.LoanDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

  public List<Book> search(String searchKeywords) {
    // Dela upp sökord i en lista
    String[] keywords = searchKeywords.toLowerCase().split("\\s+");

    // Hämta alla böcker först och filtrera sedan
    List<Book> books = bookRepository.findAll();
    for (String keyword : keywords) {
      books = books.stream()
              .filter(book ->
                      book.getTitle().toLowerCase().contains(keyword) ||
                              book.getAuthor().getFirstName().toLowerCase().contains(keyword) ||
                              book.getAuthor().getLastName().toLowerCase().contains(keyword) ||
                              book.getGenres().stream().anyMatch(genre -> genre.getName().toLowerCase().contains(keyword)) ||
                              (book.getPublicationYear() != null && book.getPublicationYear().toString().contains(keyword))
              )
              .collect(Collectors.toList());
    }
    return books;
  }

  public List<Book> advancedSearch(String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear) {
    // Hämta alla böcker från databasen
    List<Book> books = bookRepository.findAll();

    // Filtrera dynamiskt baserat på parametrarna
    return books.stream()
            .filter(book -> {
              boolean matches = true;
              if (title != null && !book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matches = false;
              }
              if (authorFirstName != null && !book.getAuthor().getFirstName().toLowerCase().contains(authorFirstName.toLowerCase())) {
                matches = false;
              }
              if (authorLastName != null && !book.getAuthor().getLastName().toLowerCase().contains(authorLastName.toLowerCase())) {
                matches = false;
              }
              if (genreName != null && book.getGenres().stream()
                      .noneMatch(genre -> genre.getName().toLowerCase().contains(genreName.toLowerCase()))) {
                matches = false;
              }
              if (publicationYear != null && !publicationYear.equals(book.getPublicationYear())) {
                matches = false;
              }
              return matches;
            })
            .collect(Collectors.toList());
  }

  // Metod som skapar en BookAdminDTO från en Book
  public BookAdminDTO getBookAdminDTO(Book book) {
    // Konvertera lån till LoanDTO
    List<LoanDTO> loanDTOs = book.getLoan().stream()
            .map(loan -> new LoanDTO(loan))  // Använd den anpassade konstruktorn i LoanDTO
            .collect(Collectors.toList());

    // Skapa BookAdminDTO och sätt alla värden
    BookAdminDTO bookAdminDTO = new BookAdminDTO();
    bookAdminDTO.setId(book.getId());
    bookAdminDTO.setTitle(book.getTitle());
    bookAdminDTO.setAuthorFirstName(book.getAuthor().getFirstName());
    bookAdminDTO.setAuthorLastName(book.getAuthor().getLastName());
    bookAdminDTO.setGenres(book.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toList()));
    bookAdminDTO.setPublicationYear(book.getPublicationYear());
    bookAdminDTO.setLoans(loanDTOs);  // Sätt listan med LoanDTO
    bookAdminDTO.setAvailable(book.isAvailable());

    return bookAdminDTO;
  }

}
