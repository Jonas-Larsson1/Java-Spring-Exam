package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.User.User;
import com.Group3.JavaSpringExam.Util.OnCreate;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @PastOrPresent(message = "Invalid date!")
  private LocalDate loanDate;

  @Future(groups = OnCreate.class, message = "Due date must be in the future!")
  private LocalDate dueDate;

  @PastOrPresent(message = "Invalid date!")
  private LocalDate returnedDate;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonManagedReference
  private Book book;
}
