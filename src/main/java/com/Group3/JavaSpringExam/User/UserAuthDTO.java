package com.Group3.JavaSpringExam.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAuthDTO {

  @Size(min = 1, message = "Please enter at least one initial.")
  @Pattern(regexp = "^[a-zA-Z. ]*$", message = "Names may only contain letters.")
  private String firstName;

  @Size(min = 2, max = 50, message = "Please enter a full surname.")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Names may only contain letters.")
  private String lastName;

  @Email
  @NotNull
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
  private String rawPassword;
}
