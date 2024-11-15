package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Genre.Genre;

import com.Group3.JavaSpringExam.Loan.Loan;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 2, max = 50, message = "Please enter a valid title.")
  private String title;

  @PastOrPresent(message = "Year of publication must be this year or earlier!")
  private Year publicationYear;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private @Valid Author author;

  // removed CascadeType.PERSIST as this caused errors
  // when posting new books with existing genres
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "books_genres",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id")
  )
  private List<@Valid Genre> genres;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Loan> loan;

  private boolean available;
}
