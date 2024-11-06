package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.Member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "loans")
@Data
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date loanDate;

  private Date dueDate;

  private Date returnedDate;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
