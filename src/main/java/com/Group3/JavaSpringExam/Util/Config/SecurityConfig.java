package com.Group3.JavaSpringExam.Util.Config;

import com.Group3.JavaSpringExam.Admin.AdminDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminDetailsService userDetailsService;

    public SecurityConfig(AdminDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/book/**").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.GET, "/loan/{memberNumber}").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.POST, "/loan").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
//                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .userDetailsService(userDetailsService);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder().username("Bob")
//                .password(passwordEncoder.encode("Bob"))
//                .roles("USER").build();
//
//        UserDetails admin = User.builder().username("Fred")
//                .password(passwordEncoder.encode("Fred"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
