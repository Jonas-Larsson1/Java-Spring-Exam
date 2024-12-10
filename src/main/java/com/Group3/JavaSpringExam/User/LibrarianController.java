package com.Group3.JavaSpringExam.User;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class LibrarianController {
    private final UserService userService;

    public LibrarianController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addLibrarian(@RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.addLibrarian(librarian));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id, @RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.updateLibrarian(id, librarian));
    }
}
