package com.Group3.JavaSpringExam.Member;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberNumber}")
    public Member getMemberByNumber(@PathVariable Long memberNumber){
        return memberService.getByMemberNumber(memberNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody @Valid Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }
}
