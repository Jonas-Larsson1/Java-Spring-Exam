package com.Group3.JavaSpringExam.Util.Config;

import com.Group3.JavaSpringExam.Member.Member;
import com.Group3.JavaSpringExam.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MemberService memberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/book/**").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/loan/{memberNumber}").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/loan").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return memberNr -> {
            Member member = memberService.getByMemberNumber(Long.parseLong(memberNr));
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
