package com.example.LibraryManagement.DTOS;

import lombok.Data;


@Data
public class DtoBook {
    private String name;
    private String description;
    private Long numPages;
    private Long authorId;
    private DtoAuthor author;
    private Long categoryId;
    private DtoCategory category;

    public DtoBook(String name, String description, long numPages, Long authorId, DtoAuthor author, Long categoryId, DtoCategory category) {
        this.name = name;
        this.description = description;
        this.numPages = numPages;
        this.authorId = authorId;
        this.author = author;
        this.categoryId = categoryId;
        this.category = category;
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

    public long getNumPages() {
        return numPages;
    }

    public void setNumPages(long numPages) {
        this.numPages = numPages;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public DtoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(DtoAuthor author) {
        this.author = author;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public DtoCategory getCategory() {
        return category;
    }

    public void setCategory(DtoCategory category) {
        this.category = category;
    }
}
