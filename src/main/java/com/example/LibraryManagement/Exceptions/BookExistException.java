package com.example.LibraryManagement.Exceptions;

public class BookExistException extends RuntimeException{
    public BookExistException(String message) {
        super(message);
    }
}
