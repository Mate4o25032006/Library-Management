package com.example.LibraryManagement.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {
    //Creating Token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date actualTime = new Date();
        Date tokenExpiration = new Date(actualTime.getTime() + SecurityConstans.JWT_EXPIRATION_TOKEN);

        //Token generator
        String token = Jwts.builder() //Building token called "token"
                .setSubject(username)//User logging in
                //Token Issuance and expiry
                .setIssuedAt(new Date())
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, SecurityConstans.JWT_SIGNATURE)//Token signature to avoid manipulation
                .compact();//Finishing the Token construction
        return token;
    }

    //Extract UserName from a Token
    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser() //Analize Token
                .setSigningKey(SecurityConstans.JWT_SIGNATURE) //Key to verify Token signature
                .parseClaimsJws(token) //Verify the token signature from String "Token"
                .getBody(); //Get all token information
        //UserName in the token
        return claims.getSubject(); //Return Username
    }

    //Validate Token Method
    public boolean validateToken(String token){
        try{
            //Validation by String Token "token" Signature
            Jwts.parser().setSigningKey(SecurityConstans.JWT_SIGNATURE).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT token has expired Or it's incorrect.");
        }
    }
}
