package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.DTOS.DtoAuthor;
import com.example.LibraryManagement.DTOS.DtoBook;
import com.example.LibraryManagement.DTOS.DtoCategory;
import com.example.LibraryManagement.Exceptions.BookNoFoundException;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Models.User;
import com.example.LibraryManagement.Repositories.AuthorRepository;
import com.example.LibraryManagement.Repositories.BookRepository;
import com.example.LibraryManagement.Repositories.CategoryRepository;
import com.example.LibraryManagement.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityService securityService;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, SecurityService securityService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.securityService = securityService;
    }

    //Obtenemos los libros solo del usuario autenticado y devolverlos en formato DTO
    public List<DtoBook> getUserBooks() {
        //Obtenemos el usuario autenticado a través del servicio de seguridad
        Optional<User> currentUserOpt = securityService.getAuthenticatedUser();

        User currentUser = currentUserOpt.get();

        //Obtenemos los libros solo del usuario autenticado
        List<Book> books = bookRepository.findByUser(currentUser);

        //Convertimos cada Book a DtoBook y mapear los datos necesarios
        return books.stream().map(book -> {
            //Creamos un DtoAuthor a partir de la entidad Author asociada
            DtoAuthor authorDTO = new DtoAuthor(
                    book.getAuthor().getName()
            );

            //Creamos un DtoCategory a partir de la entidad Category asociada
            DtoCategory categoryDTO = new DtoCategory(
                    book.getCategory().getCategoryName()
            );

            //Retornamos un nuevo DtoBook con los datos transformados
            return new DtoBook(
                    book.getName(),
                    book.getDescription(),
                    book.getNumPages(),
                    book.getAuthor().getIdAuthor(),
                    authorDTO,
                    book.getCategory().getIdCategory(),
                    categoryDTO
            );
        }).collect(Collectors.toList());
    }


    @Transactional
    public ResponseEntity<String> registerBook(DtoBook dtoBook) {
        //Obtenemos el usuario autenticado a través del servicio de seguridad
        Optional<User> currentUserOpt = securityService.getAuthenticatedUser();

        if (currentUserOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User currentUser = currentUserOpt.get();

        //Validamos que authorId y categoryId no sean nulos antes de continuar
        if (dtoBook.getAuthorId() == null || dtoBook.getCategoryId() == null) {
            return new ResponseEntity<>("Author ID and Category ID must not be null", HttpStatus.BAD_REQUEST);
        }

        try {
            //Buscamos el autor y la categoría relacionados
            Author author = authorRepository.findById(dtoBook.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));

            Category category = categoryRepository.findById(dtoBook.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            //Creamos y poblamos la entidad Book
            Book book = new Book();
            book.setName(dtoBook.getName());
            book.setDescription(dtoBook.getDescription());
            book.setNumPages(dtoBook.getNumPages());
            book.setAuthor(author);
            book.setCategory(category);
            book.setUser(currentUser);

            //Guardamos el libro en la base de datos
            bookRepository.save(book);

            return new ResponseEntity<>("Book registered correctly", HttpStatus.CREATED);

        } catch (DatabaseException e) {
            return new ResponseEntity<>("Error in related entities: " + e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /*Other Methods*/


    //Get Book By ID (Public)
    public Optional<Book> getBookById(Long idBook){
        try{
            Optional<Book> book = bookRepository.findById(idBook);
            if(book.isEmpty()){
                throw new BookNoFoundException("Book with ID " + idBook + " not found.");
            }
            return book;
        }catch (Exception e){
            throw new DatabaseException("Error when querying database.", HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new DatabaseException("Error when querying database.", HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new DatabaseException("Error deleting the book with id " + idBook, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

}
