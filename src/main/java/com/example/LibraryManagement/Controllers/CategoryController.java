package com.example.LibraryManagement.Controllers;


import com.example.LibraryManagement.DTOS.DtoBook;
import com.example.LibraryManagement.DTOS.DtoCategory;
import com.example.LibraryManagement.DTOS.DtoCategoryResponse;
import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<DtoCategoryResponse>> getUserCategories() {
        List<DtoCategoryResponse> userCategories = categoryService.getUserCategories();
        return new ResponseEntity<>(userCategories, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<String> registerCategories(@RequestBody DtoCategory dtoCategory){
        return categoryService.registerCategory(dtoCategory);
    }
}
