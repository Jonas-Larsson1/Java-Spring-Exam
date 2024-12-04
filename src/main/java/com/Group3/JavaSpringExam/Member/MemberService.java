package com.Group3.JavaSpringExam.Member;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Member getByMemberNumber(Long memberNumber){
        return memberRepository.findByMemberNumber(memberNumber);
    }

    public Member registerMember(String rawPassword) {
        Member member = new Member();
        member.setRawPassword(rawPassword);
        member.setPassword(passwordEncoder.encode(rawPassword));

        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member member) {
        Member oldMember = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Member not found"));
        modelMapper.map(member, oldMember);
        return memberRepository.save(oldMember);
    }
}
