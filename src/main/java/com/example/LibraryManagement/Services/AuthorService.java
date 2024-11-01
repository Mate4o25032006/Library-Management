package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.DTOS.DtoAuthor;
import com.example.LibraryManagement.DTOS.DtoAuthorResponse;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Models.User;
import com.example.LibraryManagement.Repositories.AuthorRepository;
import com.example.LibraryManagement.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public AuthorService(AuthorRepository authorRepository, UserRepository userRepository, SecurityService securityService){
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    //Get all authors
    public List<DtoAuthorResponse> getUserAuthors() {
        Optional<User> currentUserOpt = securityService.getAuthenticatedUser();

        User currentUser = currentUserOpt.get();

        // Obtener los libros solo del usuario autenticado
        List<Author> authors = authorRepository.findByUser(currentUser);

        // Convert each Author to a DtoAuthorResponse
        return authors.stream()
                .map(author -> new DtoAuthorResponse(author.getIdAuthor(), author.getName()))
                .collect(Collectors.toList());
    }

    //Register Book (Private)
    @Transactional
    public ResponseEntity<String> registerAuthor(@RequestBody DtoAuthor dtoAuthor) {
        Optional<User> currentUserOpt = securityService.getAuthenticatedUser();

        if (currentUserOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User currentUser = currentUserOpt.get();

        try {
            Author author = new Author();
            author.setName(dtoAuthor.getName());
            author.setUser(currentUser);

            authorRepository.save(author);

            return new ResponseEntity<>("Author registered successfully", HttpStatus.CREATED);

        } catch (DatabaseException e) {
            return new ResponseEntity<>("Database error occurred during registration", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
