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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/book/**").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/loan/{memberNumber}").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/loan").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/auth/register").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
