package com.example.LibraryManagement.Exceptions;

import org.springframework.http.HttpStatus;

public class AuthorExistsException extends RuntimeException{
    public AuthorExistsException(String message) {
        super(message);
    }
}
