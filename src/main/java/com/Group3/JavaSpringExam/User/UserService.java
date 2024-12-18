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

    public UserDTO getByMemberNumber(Long memberNumber){
        List<User> members = userRepository.findAll();
        User foundMember = members.stream().filter(member -> member.getMemberNumber().equals(memberNumber)).findFirst().orElse(null);
        if(foundMember == null){
            throw new NoSuchElementException();
        }else{
            UserDTO userDTO = new UserDTO();
            modelMapper.map(foundMember, userDTO);
            return userDTO;
        }
    }

    public UserDTO addUser(UserAuthDTO memberInfo, String role) {
        // just to make sure no admin rights are granted
        User newUser = new User();

        modelMapper.map(memberInfo, newUser);
        newUser.setPassword(passwordEncoder.encode(memberInfo.getRawPassword()));
        newUser.setRole(roleRepository.findByName(role));
        userRepository.save(newUser);

        UserDTO newUserInfo = new UserDTO();
        modelMapper.map(newUser, newUserInfo);

        return newUserInfo;
    }

    public UserDTO updateMember(UserAuthDTO updatedMemberInfo, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);
        User currentMemberInfo = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Member not found"));
        modelMapper.map(updatedMemberInfo, currentMemberInfo);
        userRepository.save(currentMemberInfo);

        UserDTO userDTO = new UserDTO();
        modelMapper.map(currentMemberInfo, userDTO);

        return userDTO;
    }

    public UserDTO updateLibrarian(Long id, @Valid UserAuthDTO updatedLibrarianInfo, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);
        UserDTO userDTO = new UserDTO();

        User authenticatedUser = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        User currentLibrarianInfo;

        if (authenticatedUser.getRole().getName().equals("ROLE_LIBRARIAN")) {
            currentLibrarianInfo = authenticatedUser;
        } else if (authenticatedUser.getRole().getName().equals("ROLE_ADMIN")) {
            currentLibrarianInfo = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Librarian not found"));
        } else {
            throw new Exception("How did you get here?");
        }

        modelMapper.map(updatedLibrarianInfo, currentLibrarianInfo);
        userRepository.save(currentLibrarianInfo);

        modelMapper.map(currentLibrarianInfo, userDTO);

        return userDTO;
    }
}
