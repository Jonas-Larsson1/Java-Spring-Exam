package com.Group3.JavaSpringExam.Util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
        UserDetails user = User.builder().username("Bob")
                .password(passwordEncoder.encode("Bob"))
                .roles("USER").build();

        UserDetails admin = User.builder().username("Fred")
                .password(passwordEncoder.encode("Fred"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
