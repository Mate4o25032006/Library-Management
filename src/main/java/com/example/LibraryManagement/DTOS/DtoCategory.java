package com.example.LibraryManagement.DTOS;

public class DtoCategory {
    private String categoryName;

    public DtoCategory() {
    }

    public DtoCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
