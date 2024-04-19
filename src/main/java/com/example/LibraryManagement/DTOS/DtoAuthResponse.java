package com.example.LibraryManagement.DTOS;

import lombok.Data;

//Return Message with the Token
@Data
public class DtoAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public DtoAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
