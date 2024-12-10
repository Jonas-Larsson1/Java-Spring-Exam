package com.Group3.JavaSpringExam.User;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{memberNumber}")
    public User getMemberByNumber(@PathVariable Long memberNumber){
        return userService.getByMemberNumber(memberNumber);
    }

    @PostMapping
    public ResponseEntity<?> addMember(@RequestBody @Valid User member) {
        return ResponseEntity.ok(userService.addMember(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody @Valid User member) {
        return ResponseEntity.ok(userService.updateMember(id, member));
    }
}
