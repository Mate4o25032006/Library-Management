package com.example.LibraryManagement.Controllers;

import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class BookController {
    @Autowired
    BookService bookService;
    //Petición GET de prueba
    @GetMapping()
    public String index(){
        return "Conectado Exitosamente";
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{idBook}")
    public Optional<Book> getBookById(@PathVariable("idBook") long idBook){
        return bookService.getBookById(idBook);
    }

    @GetMapping("/books/available/{availability}")
    public List<Book> getBooksByAvailability(@PathVariable("availability") String availability){
        return bookService.getBooksByAvailability(availability);
    }

    @GetMapping("/books/search/{name}")
    public List<Book> getBookByName(@PathVariable("name") String name){
        return bookService.getBookByName(name);
    }

    @PostMapping("/books")
    public ResponseEntity<String> registerBook(@RequestBody Book book){
        return bookService.registerBook(book);
    }

    @PutMapping("/books/{idBook}")
    public Book updateBook(@PathVariable Long idBook){
        return bookService.updateBook(idBook);
    }
}
