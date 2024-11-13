package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.Member.Member;
import jakarta.persistence.*;
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

  @Future(message = "Due date must be in the future!")
  private LocalDate dueDate;

  @PastOrPresent(message = "Invalid date!")
  private LocalDate returnedDate;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "book_id")
  private Book book;
}
