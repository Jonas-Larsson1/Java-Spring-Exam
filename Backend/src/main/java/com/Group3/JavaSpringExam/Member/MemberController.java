package com.Group3.JavaSpringExam.Member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberNumber}")
    public ResponseEntity<Member> getMember(@PathVariable("memberNumber") Long memberNumber) {
        return new ResponseEntity<>(memberService.getMemberByMemberNumber(memberNumber), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<?> addMember(@RequestBody @Valid Member member) {
        return ResponseEntity.ok(memberService.addMember(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody @Valid Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }
}
