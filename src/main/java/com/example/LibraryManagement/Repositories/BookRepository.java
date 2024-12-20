package com.example.LibraryManagement.Repositories;

import com.example.LibraryManagement.Models.Book;
import com.example.LibraryManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByNameContaining(String name);
    public List<Book> findByUser(User user);
}
