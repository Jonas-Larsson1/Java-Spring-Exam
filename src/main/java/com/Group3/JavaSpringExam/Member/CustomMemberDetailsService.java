package com.Group3.JavaSpringExam.Member;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailsService {

    private final MemberRepository memberRepository;

    public CustomMemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public UserDetailsService memberDetailsService(PasswordEncoder passwordEncoder) {
        return memberNr -> {
            Member member = memberRepository.findByMemberNumber(Long.parseLong(memberNr));
            if (member == null) {
                throw new UsernameNotFoundException("User not found");
            }else{
                return org.springframework.security.core.userdetails.User.builder()
                        .username(member.getMemberNumber().toString())
                        .password(passwordEncoder.encode(member.getPassword()))
                        .roles(member.getRole())
                        .build();
            }
        };
    }
}
