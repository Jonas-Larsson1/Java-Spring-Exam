package com.Group3.JavaSpringExam.Member;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public Member getByMemberNumber(Long memberNumber){
        List<Member> members = memberRepository.findAll();
        Member foundMember = members.stream().filter(member -> member.getMemberNumber().equals(memberNumber)).findFirst().orElse(null);
        if(foundMember == null){
            throw new NoSuchElementException();
        }else{
            return foundMember;
        }
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member member) {
        Member oldMember = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Member not found"));
        modelMapper.map(member, oldMember);
        return memberRepository.save(oldMember);
    }

    public Member getMemberByMemberNumber(Long memberNumber) {
        Member foundMember = memberRepository.findByMemberNumber(memberNumber);
        if(foundMember != null){
            return foundMember;
        }else{
            throw new NoSuchElementException("Member not found");
        }
    }
}
