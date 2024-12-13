package com.Group3.JavaSpringExam.User;

import com.Group3.JavaSpringExam.Role.RoleRepository;
import com.Group3.JavaSpringExam.Security.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User getByMemberNumber(Long memberNumber){
        List<User> members = userRepository.findAll();
        User foundMember = members.stream().filter(member -> member.getMemberNumber().equals(memberNumber)).findFirst().orElse(null);
        if(foundMember == null){
            throw new NoSuchElementException();
        }else{
            return foundMember;
        }
    }

    public User addMember(User member) {
        // just to make sure no admin rights are granted
        member.setRole(roleRepository.findByName("ROLE_MEMBER"));
        member.setPassword(passwordEncoder.encode(member.getRawPassword()));
        return userRepository.save(member);
    }

    public User updateMember(User updatedMemberInfo, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);
        User currentMemberInfo = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Member not found"));
        modelMapper.map(updatedMemberInfo, currentMemberInfo);

        // make sure member no. cannot change
        currentMemberInfo.setMemberNumber(currentMemberInfo.getMemberNumber());
        return userRepository.save(currentMemberInfo);
    }

    public String addLibrarian(@Valid User librarian) {
        librarian.setRole(roleRepository.findByName("ROLE_LIBRARIAN"));
        librarian.setPassword(passwordEncoder.encode(librarian.getRawPassword()));
        return "New librarian with email " + userRepository.saveAndFlush(librarian).getEmail() + " added.";
    }

    public String updateLibrarian(Long id, @Valid User librarian) {
        User oldLibrarian = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Librarian not found"));
        modelMapper.map(librarian, oldLibrarian);
        librarian.setRole(roleRepository.findByName("ROLE_LIBRARIAN"));
        return "Details updated for librarian with email " + userRepository.saveAndFlush(librarian).getEmail() + ".";
    }
}
