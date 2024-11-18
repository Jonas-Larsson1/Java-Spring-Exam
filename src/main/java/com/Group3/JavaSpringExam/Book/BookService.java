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


  //  public List<Book> advancedSearch(String title, String authorFirstName, String authorLastName, String genreName, Year publicationYear) {
  //
  //    // 1. Titel, författarens förnamn, efternamn och genre
  //    if (title != null && authorFirstName != null && authorLastName != null && genreName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(title, authorFirstName, authorLastName, genreName);
  //    }
  //    // 2. Titel, författarens förnamn, efternamn och publiceringsår
  //    else if (title != null && authorFirstName != null && authorLastName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(title, authorFirstName, authorLastName, publicationYear);
  //    }
  //    // 3. Titel, författarens förnamn, genre och publiceringsår
  //    else if (title != null && authorFirstName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(title, authorFirstName, genreName, publicationYear);
  //    }
  //    // 4. Titel, författarens efternamn, genre och publiceringsår
  //    else if (title != null && authorLastName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(title, authorLastName, genreName, publicationYear);
  //    }
  //    // 5. Författarens förnamn, efternamn, genre och publiceringsår
  //    else if (authorFirstName != null && authorLastName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(authorFirstName, authorLastName, genreName, publicationYear);
  //    }
  //    // 6. Titel, författarens förnamn, efternamn, genre och publiceringsår
  //    if (title != null && authorFirstName != null && authorLastName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(title, authorFirstName, authorLastName, genreName, publicationYear);
  //    }
  //
  //
  //    // 7. Titel, författarens förnamn och efternamn
  //    if (title != null && authorFirstName != null && authorLastName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(title, authorFirstName, authorLastName);
  //    }
  //    // 8. Titel, författarens förnamn och genre
  //    else if (title != null && authorFirstName != null && genreName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(title, authorFirstName, genreName);
  //    }
  //    // 9. Titel, författarens förnamn och publiceringsår
  //    else if (title != null && authorFirstName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCaseAndPublicationYear(title, authorFirstName, publicationYear);
  //    }
  //    // 10. Titel, författarens efternamn och genre
  //    else if (title != null && authorLastName != null && genreName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(title, authorLastName, genreName);
  //    }
  //    // 11. Titel, författarens efternamn och publiceringsår
  //    else if (title != null && authorLastName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(title, authorLastName, publicationYear);
  //    }
  //    // 12. Titel, genre och publiceringsår
  //    if (title != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(title, genreName, publicationYear);
  //    }
  //    // 13. Författarens förnamn, efternamn och genre
  //    else if (authorFirstName != null && authorLastName != null && genreName != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(authorFirstName, authorLastName, genreName);
  //    }
  //    // 14. Författarens förnamn, efternamn och publiceringsår
  //    else if (authorFirstName != null && authorLastName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCaseAndPublicationYear(authorFirstName, authorLastName, publicationYear);
  //    }
  //    // 15. Författarens förnamn, genre och publiceringsår
  //    else if (authorFirstName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(authorFirstName, genreName, publicationYear);
  //    }
  //    // 16. Författarens efternamn, genre och publiceringsår
  //    else if (authorLastName != null && genreName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCaseAndPublicationYear(authorLastName, genreName, publicationYear);
  //    }
  //
  //
  //    // 17. Titel och författarens förnamn
  //    if (title != null && authorFirstName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_FirstNameContainingIgnoreCase(title, authorFirstName);
  //    }
  //    // 18. Titel och författarens efternamn
  //    else if (title != null && authorLastName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(title, authorLastName);
  //    }
  //    // 19. Titel och genre
  //    else if (title != null && genreName != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(title, genreName);
  //    }
  //    // 20. Titel och publiceringsår
  //    else if (title != null && publicationYear != null) {
  //      return bookRepository.findByTitleContainingIgnoreCaseAndPublicationYear(title, publicationYear);
  //    }
  //    // 21. Författarens förnamn och efternamn
  //    else if (authorFirstName != null && authorLastName != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(authorFirstName, authorLastName);
  //    }
  //    // 22. Författarens förnamn och genre
  //    if (authorFirstName != null && genreName != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(authorFirstName, genreName);
  //    }
  //    // 23. Författarens förnamn och publiceringsår
  //    else if (authorFirstName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCaseAndPublicationYear(authorFirstName, publicationYear);
  //    }
  //    // 24. Författarens efternamn och genre
  //    else if (authorLastName != null && genreName != null) {
  //      return bookRepository.findByAuthor_LastNameContainingIgnoreCaseAndGenres_NameContainingIgnoreCase(authorLastName, genreName);
  //    }
  //    // 25. Författarens efternamn och publiceringsår
  //    else if (authorLastName != null && publicationYear != null) {
  //      return bookRepository.findByAuthor_LastNameContainingIgnoreCaseAndPublicationYear(authorLastName, publicationYear);
  //    }
  //    // 26. Genre och publiceringsår
  //    else if (genreName != null && publicationYear != null) {
  //      return bookRepository.findByGenres_NameContainingIgnoreCaseAndPublicationYear(genreName, publicationYear);
  //    }
  //
  //
  //    // 27. Bara titel
  //    if (title != null) {
  //      return bookRepository.findByTitleContainingIgnoreCase(title);
  //    }
  //    // 28. Bara författarens förnamn
  //    else if (authorFirstName != null) {
  //      return bookRepository.findByAuthor_FirstNameContainingIgnoreCase(authorFirstName);
  //    }
  //    // 29. Bara författarens efternamn
  //    else if (authorLastName != null) {
  //      return bookRepository.findByAuthor_LastNameContainingIgnoreCase(authorLastName);
  //    }
  //    // 30. Bara genre
  //    else if (genreName != null) {
  //      return bookRepository.findByGenres_NameContainingIgnoreCase(genreName);
  //    }
  //    // 31. Bara publiceringsår
  //    else if (publicationYear != null) {
  //      return bookRepository.findByPublicationYear(publicationYear);
  //    }
  //
  //    // Om inga kriterier är ifyllda, returnerar en tom lista
  //    return List.of(); // Returnerar en tom lista om inga kriterier anges
  //  }
}
