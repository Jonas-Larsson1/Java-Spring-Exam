package com.Group3.JavaSpringExam.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("member/{memberNumber}")
    public User getMemberByNumber(@PathVariable Long memberNumber){
        return userService.getByMemberNumber(memberNumber);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @PostMapping("/member")
    public ResponseEntity<?> addMember(@RequestBody @Valid User member) {
        return ResponseEntity.ok(userService.addMember(member));
    }

    @PutMapping("/member")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateMember(@RequestBody @Valid User member, HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateMember(member, request));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addLibrarian(@RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.addLibrarian(librarian));
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id, @RequestBody @Valid User librarian) {
        return ResponseEntity.ok(userService.updateLibrarian(id, librarian));
    }
}
