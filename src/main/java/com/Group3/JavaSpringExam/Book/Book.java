package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Genre.Genre;

import com.Group3.JavaSpringExam.Loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.CascadeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
  @JsonIgnore
  private List<Loan> loan;

  private boolean available;
}
