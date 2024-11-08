package com.Group3.JavaSpringExam.Author;

import com.Group3.JavaSpringExam.Book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, message = "Please enter at least one initial.")
  @Pattern(regexp = "^[a-zA-Z. ]*$", message = "Names may only contain letters.")
  private String firstName;

  @Size(min = 2, max = 50, message = "Please enter a full surname.")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Names may only contain letters.")
  private String lastName;

  @Past(message = "Date of birth must be in the past!")
  private LocalDate birthDate;

  @OneToMany(mappedBy = "author")
  @JsonIgnore
  private List<Book> books;
}
