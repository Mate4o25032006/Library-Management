package com.example.LibraryManagement.Services;

import com.example.LibraryManagement.Exceptions.AuthorExistsException;
import com.example.LibraryManagement.Exceptions.BookExistException;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    //Get all Books (Public)
    public List<Category> getAllCategories(){
        try{
            return categoryRepository.findAll();
        } catch(Exception e){
            throw new DatabaseException("Error when querying database.");
        }
    }

    //Register Category (Private)
    @Transactional
    public ResponseEntity<String> registerCategory(@RequestBody Category category){
        try{
            Optional<Category> validCategory = categoryRepository.findById(category.getIdCategory());
            if (validCategory.isEmpty()) {
                categoryRepository.save(category);
            } else {
                throw new AuthorExistsException("Category already exists in the database.");
            }
            return new ResponseEntity<>("correctly registered category", HttpStatus.CREATED);
        }catch (Exception e){
            throw new DatabaseException("An error has occurred in the registration");
        }
    }
}
