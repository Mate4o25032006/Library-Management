package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.DTOS.DtoBook;
import com.example.LibraryManagement.Exceptions.BookNoFoundException;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Repositories.AuthorRepository;
import com.example.LibraryManagement.Repositories.BookRepository;
import com.example.LibraryManagement.Repositories.CategoryRepository;
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
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
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


    //Register Book (Private)
    @Transactional
    public ResponseEntity<String> registerBook(@RequestBody DtoBook dtoBook) {

        // Validar que authorId y categoryId no sean nulos antes de continuar
        /*if (dtoBook.getAuthorId() == null || dtoBook.getCategoryId() == null) {
            return new ResponseEntity<>("Author ID and Category ID must not be null", HttpStatus.BAD_REQUEST);
        }*/

        try {
            Author author = authorRepository.findById(dtoBook.getAuthorId())
                    .orElseThrow(() -> new DatabaseException("Author not found"));

            Category category = categoryRepository.findById(dtoBook.getCategoryId())
                    .orElseThrow(() -> new DatabaseException("Category not found"));

            Book book = new Book();
            book.setName(dtoBook.getName());
            book.setDescription(dtoBook.getDescription());
            book.setNumPages(dtoBook.getNumPages());
            book.setAuthor(author);
            book.setCategory(category);

            bookRepository.save(book);
            return new ResponseEntity<>("Book registered correctly", HttpStatus.CREATED);

        } catch (DatabaseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //Delete Book
    public Book deleteBook(Long idBook) {
        try {
            Optional<Book> deletedBook = bookRepository.findById(idBook);
            if (deletedBook.isEmpty()) {
                throw new BookNoFoundException("Book with id " + idBook + " no found");
            }
            bookRepository.delete(deletedBook.get());
        } catch (Exception e) {
            throw new DatabaseException("Error deleting the book with id " + idBook);
        }
        return null;
    }

}
