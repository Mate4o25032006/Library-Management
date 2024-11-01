package com.example.LibraryManagement.Exceptions;

import org.springframework.http.HttpStatus;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message, HttpStatus httpStatus) {
        super(message);
    }
}
