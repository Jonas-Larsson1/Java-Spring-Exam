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

  @Pattern(regexp = "[0-9]{8}")
  private String memberNumber;

  @OneToMany(mappedBy = "member")
  @JsonIgnore
  private List<Loan> loans;
}
