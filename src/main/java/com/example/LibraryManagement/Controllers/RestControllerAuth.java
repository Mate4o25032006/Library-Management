package com.example.LibraryManagement.Controllers;

import com.example.LibraryManagement.DTOS.DtoAuthResponse;
import com.example.LibraryManagement.DTOS.DtoLogin;
import com.example.LibraryManagement.DTOS.DtoRegister;
import com.example.LibraryManagement.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody DtoRegister dtoRegister){
        return userService.registerUser(dtoRegister);
    }

    @PostMapping("registerAdm")
    public ResponseEntity<String> registerAdm(@RequestBody DtoRegister dtoRegister){
        return userService.registerAdm(dtoRegister);
    }

    @PostMapping("login")
    public ResponseEntity<DtoAuthResponse> loginUser(@RequestBody DtoLogin dtoLogin){
        return userService.loginUser(dtoLogin);
    }
}
