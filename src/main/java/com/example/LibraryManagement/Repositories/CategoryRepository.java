package com.example.LibraryManagement.Repositories;

import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Category;
import com.example.LibraryManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
   //public Optional<Category> findByNameContaining(String categoryName);
   public List<Category> findByUser(User user);
}
