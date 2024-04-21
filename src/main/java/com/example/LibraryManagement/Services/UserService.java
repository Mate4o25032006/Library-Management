package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.DTOS.DtoAuthResponse;
import com.example.LibraryManagement.DTOS.DtoLogin;
import com.example.LibraryManagement.DTOS.DtoRegister;
import com.example.LibraryManagement.Models.Role;
import com.example.LibraryManagement.Models.User;
import com.example.LibraryManagement.Repositories.RoleRepository;
import com.example.LibraryManagement.Repositories.UserRepository;
import com.example.LibraryManagement.Security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

@Service
public class UserService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private JwtGenerator jwtGenerator;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    //Register User Method
    public ResponseEntity<String> registerUser(@RequestBody DtoRegister dtoRegister){
        if(userRepository.existsByUserName(dtoRegister.getUserName())){
            return new ResponseEntity<>("The user already exists, try again", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(dtoRegister.getUserName());
        user.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        return new ResponseEntity<>("User Register Correctly", HttpStatus.OK);
    }

    //Register Admin Method
    public ResponseEntity<String> registerAdm(@RequestBody DtoRegister dtoRegister){
        if(userRepository.existsByUserName(dtoRegister.getUserName())){
            return new ResponseEntity<>("The user already exists, try again", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(dtoRegister.getUserName());
        user.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));

        Role role = roleRepository.findByName("ADMIN").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        return new ResponseEntity<>("Admin Register Correctly", HttpStatus.OK);
    }

    //Login User Method
    public ResponseEntity<DtoAuthResponse> loginUser(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUserName(),dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }
}
