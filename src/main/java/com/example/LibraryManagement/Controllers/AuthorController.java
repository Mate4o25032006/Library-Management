package com.example.LibraryManagement.Controllers;

import com.example.LibraryManagement.DTOS.DtoAuthor;
import com.example.LibraryManagement.DTOS.DtoAuthorResponse;
import com.example.LibraryManagement.DTOS.DtoCategoryResponse;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<DtoAuthorResponse>> getUserAuthors() {
        List<DtoAuthorResponse> userAuthors = authorService.getUserAuthors();
        return new ResponseEntity<>(userAuthors, HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<String> registerAuthor(@RequestBody DtoAuthor dtoAuthor){
        return authorService.registerAuthor(dtoAuthor);
    }
}
