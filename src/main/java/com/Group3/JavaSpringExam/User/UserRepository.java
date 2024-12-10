package com.Group3.JavaSpringExam.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByMemberNumber(Long number);

    Optional<User> findByUsername(String username);
}
