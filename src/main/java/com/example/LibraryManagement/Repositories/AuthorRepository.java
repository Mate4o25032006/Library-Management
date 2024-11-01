package com.example.LibraryManagement.Repositories;

import com.example.LibraryManagement.Models.Author;
import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findByNameContaining(String name);
    public List<Author> findByUser(User user);
}
