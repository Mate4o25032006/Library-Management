package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.Exceptions.AuthorExistsException;
import com.example.LibraryManagement.Exceptions.BookExistException;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    //Get all authors
    public List<Author> getAllAuthors(){
        try{
            return authorRepository.findAll();
        }catch (Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }

    //Register Book (Private)
    @Transactional
    public ResponseEntity<String> registerAuthor(@RequestBody Author author){
        try{
            Optional<Author> validAuthor = authorRepository.findByNameContaining(author.getName());
            if (validAuthor.isEmpty()) {
                authorRepository.save(author);
            } else {
                throw new AuthorExistsException("Author already exists in the database.");
            }
            return new ResponseEntity<>("correctly registered Author", HttpStatus.CREATED);
        }catch (Exception e){
            throw new DatabaseException("An error has occurred in the registration");
        }
    }
}
