package com.Group3.JavaSpringExam.User;

import com.Group3.JavaSpringExam.Role.RoleRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
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
        // ordinary member usernames == member number
        member.setUsername(member.getMemberNumber().toString());
        // just to make sure no admin rights are granted
        member.setRole(roleRepository.findByName("ROLE_MEMBER"));
        return userRepository.save(member);
    }

    public User updateMember(Long id, User member) {
        User oldMember = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Member not found"));
        modelMapper.map(member, oldMember);
        return userRepository.save(oldMember);
    }

    public String addLibrarian(@Valid User librarian) {
        librarian.setRole(roleRepository.findByName("ROLE_LIBRARIAN"));
        return "New librarian with username " + userRepository.saveAndFlush(librarian).getUsername() + " added.";
    }

    public String updateLibrarian(Long id, @Valid User librarian) {
        User oldLibrarian = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Librarian not found"));
        modelMapper.map(librarian, oldLibrarian);
        librarian.setRole(roleRepository.findByName("ROLE_LIBRARIAN"));
        return "Details updated for librarian " + userRepository.saveAndFlush(librarian).getUsername() + ".";

    }
}
