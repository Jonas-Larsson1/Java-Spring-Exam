package com.Group3.JavaSpringExam.Util;

import com.Group3.JavaSpringExam.Role.Role;
import com.Group3.JavaSpringExam.Role.RoleRepository;
import com.Group3.JavaSpringExam.User.User;
import com.Group3.JavaSpringExam.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {


        if(roleRepository.findByName("ROLE_USER") == null){
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("added ROLE_USER to database");
        }

        if(roleRepository.findByName("ROLE_ADMIN") == null){
            Role userRole = new Role();
            userRole.setName("ROLE_ADMIN");
            roleRepository.save(userRole);
            System.out.println("added ROLE_ADMIN to database");
        }


        if (userRepository.findByUsername("admin").isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setRole((roleRepository.findByName("ROLE_ADMIN")));
            admin.setPassword(passwordEncoder.encode("Pass1234"));
            userRepository.save(admin);
        }
    }
}