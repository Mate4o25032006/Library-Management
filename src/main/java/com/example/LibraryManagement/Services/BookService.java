package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.Exceptions.BookExistException;
import com.example.LibraryManagement.Exceptions.BookNoFoundException;
import com.example.LibraryManagement.Exceptions.BooksNotAvailability;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class BookService{
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Get all Books (Public)
    public List<Book> getAllBooks(){
        try{
            return bookRepository.findAll();
        } catch(Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }

    //Get Book By ID (Public)
    public Optional<Book> getBookById(Long idBook){
        try{
            Optional<Book> book = bookRepository.findById(idBook);
            if(book.isEmpty()){
                throw new BookNoFoundException("Book with ID " + idBook + " not found.");
            }
            return book;
        }catch (Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }


    //Get Book By name (Public)
    public List<Book> getBookByName(String name){
        try{
            List<Book> books = bookRepository.findByNameContaining(name);
            if(books.isEmpty()){
                throw new BookNoFoundException("No books found with name containing " + name);
            }
            return books;
        }catch (Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }


    //Get Book By availability (Public)
    public List<Book> getBooksByAvailability(String availability){
        try{
            Boolean bool = Boolean.parseBoolean(availability);
            List<Book> books = bookRepository.findByAvailability(bool);
            if(books.isEmpty()){
                throw new BooksNotAvailability("There's not Books availability");
            }else{
                return books;
            }
        }catch(Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }


    //Register Book (Private)
    @Transactional
    public ResponseEntity<String> registerBook(@RequestBody Book book){
        try{
            Optional<Book> validBook = bookRepository.findById(book.getId());
            if(validBook.isPresent()){
                throw new BookExistException("Book already exists in the database.");
            }else{
                bookRepository.save(book);
            }
            return new ResponseEntity<>("correctly registered book", HttpStatus.CREATED);
        }catch (Exception e){
            throw new DatabaseException("An error has occurred in the registration");
        }
    }


    //Update Book state (Private)
    public Book updateBook(Long idBook) {
        Optional<Book> foundBook = bookRepository.findById(idBook);
        if(foundBook.isEmpty()){
            throw new BookNoFoundException("Book with ID " + idBook + " not found.");
        } else {
            Book book = foundBook.get();
            book.setAvailability(false);
            return bookRepository.save(book);
        }
    }

}
