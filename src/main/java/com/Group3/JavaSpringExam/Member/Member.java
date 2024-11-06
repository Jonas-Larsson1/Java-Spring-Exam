package com.Group3.JavaSpringExam.Member;

import com.Group3.JavaSpringExam.Loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "members")
@Data
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Email
  private String email;

  private String memberNumber;

  @OneToMany(mappedBy = "member")
  @JsonIgnore
  private List<Loan> loans;
}
