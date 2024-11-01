package com.example.LibraryManagement.DTOS;

public class DtoAuthor {
    private String name;

    public DtoAuthor() {
    }

    public DtoAuthor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
