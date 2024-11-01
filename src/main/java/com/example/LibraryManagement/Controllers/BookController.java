package com.example.LibraryManagement.Controllers;

import com.example.LibraryManagement.DTOS.DtoBook;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    BookService bookService;
    //Petición GET de prueba
    @GetMapping()
    public String index(){
        return "Conectado Exitosamente";
    }

    @GetMapping("/books")
    public ResponseEntity<List<DtoBook>> getUserBooks() {
        List<DtoBook> userBooks = bookService.getUserBooks();
        return new ResponseEntity<>(userBooks, HttpStatus.OK);
    }


    @GetMapping("/books/{idBook}")
    public Optional<Book> getBookById(@PathVariable("idBook") long idBook){
        return bookService.getBookById(idBook);
    }


    @GetMapping("/books/search/{name}")
    public List<Book> getBookByName(@PathVariable("name") String name){
        return bookService.getBookByName(name);
    }


    @PostMapping("/books")
    public ResponseEntity<String> registerBook(@RequestBody DtoBook dtoBook){
        return bookService.registerBook(dtoBook);
    }


    @DeleteMapping("/books/{idBook}")
    public Book deleteBook(@PathVariable Long idBook){
        return bookService.deleteBook(idBook);
    }
}
