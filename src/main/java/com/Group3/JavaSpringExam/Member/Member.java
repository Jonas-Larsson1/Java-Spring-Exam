package com.Group3.JavaSpringExam.Member;

import com.Group3.JavaSpringExam.Loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "members")
@Data
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, message = "Please enter at least one initial.")
  @Pattern(regexp = "^[a-zA-Z. ]*$", message = "Names may only contain letters.")
  private String firstName;

  @Size(min = 2, max = 50, message = "Please enter a full surname.")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Names may only contain letters.")
  private String lastName;

  @Email
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
  private String password;

  @Column(unique = true)
  private Long memberNumber;

  @PostPersist
  private void assignNumericCode() {
    this.memberNumber = id + 10000000;
  }

  @OneToMany(mappedBy = "member")
  @JsonIgnore
  private List<Loan> loans;

  private final String role = "USER";
}
