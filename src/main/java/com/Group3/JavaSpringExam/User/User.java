package com.Group3.JavaSpringExam.User;

import com.Group3.JavaSpringExam.Loan.Loan;
import com.Group3.JavaSpringExam.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String email;

  @JsonIgnore
  private String password;

  @Column(unique = true)
  private Long memberNumber;

  @PostPersist
  private void assignNumericCode() {
    if (this.getRole().getName().equals("ROLE_MEMBER")) {
      this.memberNumber = id + 10000000;
    }

  }

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Loan> loans;

  @ManyToOne(optional = false)
  private Role role;

}
