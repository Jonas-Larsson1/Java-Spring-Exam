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
    public UserDTO getMemberByNumber(@PathVariable Long memberNumber){
        return userService.getByMemberNumber(memberNumber);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @PostMapping("/member")
    public ResponseEntity<?> addMember(@RequestBody @Valid UserAuthDTO memberInfo) {
        return ResponseEntity.ok(userService.addUser(memberInfo, "ROLE_MEMBER"));
    }

    @PostMapping("/librarian")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addLibrarian(@RequestBody @Valid UserAuthDTO librarianInfo) {
        return ResponseEntity.ok(userService.addUser(librarianInfo, "ROLE_LIBRARIAN"));
    }

    @PutMapping("/member")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateMember(@RequestBody @Valid UserAuthDTO updatedMemberInfo, HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateMember(updatedMemberInfo, request));
    }


    @PutMapping("/librarian")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<?> updateLibrarian(@RequestBody @Valid UserAuthDTO updatedLibrarianInfo,
                                             HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.updateLibrarian(null, updatedLibrarianInfo, request));
    }

    @PutMapping("/librarian/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLibrarian(@PathVariable Long id,
                                             @RequestBody @Valid UserAuthDTO updatedLibrarianInfo,
                                             HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.updateLibrarian(id, updatedLibrarianInfo, request));
    }
}
