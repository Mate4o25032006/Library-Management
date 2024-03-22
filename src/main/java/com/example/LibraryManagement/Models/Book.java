package com.example.LibraryManagement.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book", nullable = false)
    private long Id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 80)
    private String description;

    @Column(nullable = false, length = 20)
    private String author;

    @Column(nullable = false, length = 10)
    private long numPages;

    @Column(nullable = false)
    private boolean availability;

    public Book() {
    }

    public Book(long id, String name, String description, String author, long numPages, boolean availability) {
        Id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.numPages = numPages;
        this.availability = availability;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getNumPages() {
        return numPages;
    }

    public void setNumPages(long numPages) {
        this.numPages = numPages;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
