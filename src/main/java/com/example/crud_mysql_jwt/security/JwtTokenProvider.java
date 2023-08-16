package com.example.crud_mysql_jwt.security;

import com.example.crud_mysql_jwt.exceptions.YoViAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirateDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirateDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();

        return token;
    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public boolean tokenValidate(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new YoViAppException(HttpStatus.BAD_REQUEST, "Firma JWT no válida.");
        }
        catch (MalformedJwtException ex){
            throw new YoViAppException(HttpStatus.BAD_REQUEST, "Token JWT no válido.");
        }
        catch (ExpiredJwtException ex){
            throw new YoViAppException(HttpStatus.BAD_REQUEST, "Token JWT caducado.");
        }
        catch (UnsupportedJwtException ex){
            throw new YoViAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible.");
        }
        catch (IllegalArgumentException ex){
            throw new YoViAppException(HttpStatus.BAD_REQUEST, "Cadena claims JWT vacía.");
        }
    }


}
