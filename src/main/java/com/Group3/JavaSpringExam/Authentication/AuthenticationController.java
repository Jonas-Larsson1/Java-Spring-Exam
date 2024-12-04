package com.Group3.JavaSpringExam.Authentication;


import com.Group3.JavaSpringExam.Member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final MemberService memberService;

    public AuthenticationController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String password) {
        return ResponseEntity.ok(memberService.registerMember(password));
    }

}
