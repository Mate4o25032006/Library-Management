package com.example.LibraryManagement.Exceptions;

public class BooksNotAvailability extends RuntimeException{
    public BooksNotAvailability(String message){
        super(message);
    }
}
