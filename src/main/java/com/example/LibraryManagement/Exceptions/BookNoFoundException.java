package com.example.LibraryManagement.Exceptions;

public class BookNoFoundException extends RuntimeException{
    public BookNoFoundException(String message) {
        super(message);
    }
}
