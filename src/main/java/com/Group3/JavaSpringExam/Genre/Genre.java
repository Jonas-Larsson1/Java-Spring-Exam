package com.Group3.JavaSpringExam.Genre;

import com.Group3.JavaSpringExam.Book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 2, max = 50, message = "Please enter a name for this genre.")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Genre names may only contain letters.")
  private String name;

  @ManyToMany(mappedBy = "genres")
  @JsonIgnore
  private List<Book> books;
}
