package com.pe.proyecto006.project.configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
         * ACA LO QUE SE REALIZA ES LA VERIFICACION DEL HEADER, SI TIENE LA VARIABLE Authorization Y DENTRO EL TOKEN
         * SI TIENE INGRESA AL METODO Y VERIFICA EN TOKEN UTILS CON EL METODO getAuthentication, Y LUEGO ESTABLECE LA INFORMACION DE LA VERIFICACION DEL TOKEN
         * EN setAuthentication
         * PERO SI NO HAY ESA VARIABLE EN LOS HEADERS ENTONCES SIGUE SU FLUJO AL METODO doFilter QUE IGUAL SIEMPRE LLEGA A ESE METODO
         * Esto significa que no se establece ninguna información de autenticación en el contexto de seguridad y el flujo de la solicitud continúa sin autenticación.
        */
        log.info("3: ");
        
        //SI ENTRA AL IF DEFRENTE SE VA AL PROCESO DE USAR EL TOKEN PORQUE ESTA AUTENTIFICADO
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ") ){
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
            log.info("3: " + usernamePAT);
            /*
             * SecurityContextHolder.getContext().setAuthentication(usernamePAT), se establece la autenticación 
             * en el contexto de seguridad y luego se continúa con el procesamiento de la solicitud llamando a filterChain.doFilter(request, response).
             */
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }
        
        filterChain.doFilter(request, response);
    }
    
    
    
}
