package com.Group3.JavaSpringExam.User;

import com.Group3.JavaSpringExam.Role.Role;
import lombok.Data;

@Data
public class UserDTO {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private Long memberNumber;

  private Role role;
}
