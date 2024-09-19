package com.example.LibraryManagement.Repositories;

import com.example.LibraryManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
