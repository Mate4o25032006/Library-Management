package com.example.LibraryManagement.Services;


import com.example.LibraryManagement.DTOS.DtoCategory;
import com.example.LibraryManagement.DTOS.DtoCategoryResponse;
import com.example.LibraryManagement.Exceptions.DatabaseException;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Models.User;
import com.example.LibraryManagement.Repositories.CategoryRepository;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, SecurityService securityService){
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    //Get all Books (Public)
    public List<DtoCategoryResponse> getUserCategories(){
        Optional<User> currentUserOpt = securityService.getAuthenticatedUser();

        User currentUser = currentUserOpt.get();

        List<Category> categories = categoryRepository.findByUser(currentUser);

        return categories.stream()
                .map(category -> new DtoCategoryResponse(category.getIdCategory(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

    //Register Category (Private)
    @Transactional
    public ResponseEntity<String> registerCategory(@RequestBody DtoCategory dtoCategory){
        //Obtenemos la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        //Buscamos al usuario autenticado en la base de datos
        Optional<User> currentUserOpt = userRepository.findByEmail(currentUserEmail);

        if (currentUserOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User currentUser = currentUserOpt.get();

        try{
            Category category = new Category();
            category.setCategoryName(dtoCategory.getCategoryName());
            category.setUser(currentUser);

            categoryRepository.save(category);
            return new ResponseEntity<>("Category registered correctly", HttpStatus.CREATED);
        }catch (Exception e){
            throw new DatabaseException("An error has occurred in the registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
