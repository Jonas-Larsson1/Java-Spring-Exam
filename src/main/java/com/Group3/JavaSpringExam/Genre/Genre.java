package com.Group3.JavaSpringExam.Genre;

import com.Group3.JavaSpringExam.Book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

  private String name;

  @ManyToMany(mappedBy = "genres", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JsonIgnore
  private List<Book> books;
}
