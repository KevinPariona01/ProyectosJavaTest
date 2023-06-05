package com.pe.proyecto006.project.configuracion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TokenUtils {
    
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;//TIEMPO DE VIDA DEL TOKEN EN SEGUNDOS
    
    public static String createToken(String username){
        long expirationTime  = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime );
        Map<String, Object> extra = new HashMap<>();
        extra.put("username", username);
        
        return Jwts.builder()//CONSTRUIMOS EL TOKEN
                .setSubject(username)//A QUIEN ESTA DIRIGIDO
                .setExpiration(expirationDate)//TIEMPO DE EXPIRACION
                .addClaims(extra)//AÑADIMOS DATA ADICIONAL, PUEDEN SER MAS DATOS
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))//
                .compact();//LUEGO COMPACTAMOS
        
    }
    
    
    //ACA VA A RECIBIR EL TOKEN QUE EL CLIENTE VA A ENVIAR Y VA A REVISAR SI ES VALIDO
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
            String email = claims.getSubject();

            //ENVIAMOS COMO USER NAME EL CORREO QUE LE SERVIRA AL USUARIO PARA AUTENTICARSE
            //NULO
            //UNA LISTA VACIA
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        }catch(JwtException e){
            return null;
        }
        
    }
    
}
