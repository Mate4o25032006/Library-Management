package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.DTOS.DtoAuthResponse;
import com.example.LibraryManagement.DTOS.DtoLogin;
import com.example.LibraryManagement.DTOS.DtoRegister;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.User;
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

import java.util.List;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    //Get all Books (Public)
    public List<User> getAllUsers(){
        try{
            return userRepository.findAll();
        } catch(Exception e){
            throw new DatabaseException("Error when querying database.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Register User Method
    public ResponseEntity<String> registerUser(@RequestBody DtoRegister dtoRegister){
        if(userRepository.existsByEmail(dtoRegister.getEmail())){
            return new ResponseEntity<>("The user already exists, try again", HttpStatus.BAD_REQUEST);
        }
        User user = new User();

        user.setUserName(dtoRegister.getUserName());
        user.setEmail(dtoRegister.getEmail());
        user.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User Register Correctly", HttpStatus.OK);
    }

    //Login User Method
    public ResponseEntity<DtoAuthResponse> loginUser(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getEmail(),dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }
}
