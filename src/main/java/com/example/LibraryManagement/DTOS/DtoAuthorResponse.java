package com.example.LibraryManagement.DTOS;

public class DtoAuthorResponse {
    private Long idAuthor;
    private String name;

    public DtoAuthorResponse() {
    }

    public DtoAuthorResponse(Long idAuthor, String name) {
        this.idAuthor = idAuthor;
        this.name = name;
    }

    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
