package com.Group3.JavaSpringExam.User;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addLibrarian(@RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.addLibrarian(librarian));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String dummyRequest() {
        return "Here is a lovely dummy response";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id, @RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.updateLibrarian(id, librarian));
    }
}
