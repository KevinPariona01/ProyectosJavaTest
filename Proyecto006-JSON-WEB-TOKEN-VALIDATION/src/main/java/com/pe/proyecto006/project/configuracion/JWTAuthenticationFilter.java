package com.pe.proyecto006.project.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    
    //FILTRO DE AUTENTICACION
    
   

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        /*
         * 1. Se crea una instancia de AuthCredentials, que es una clase que representa las credenciales de autenticación enviadas por el cliente.
         * 2. Se intenta leer y mapear las credenciales de autenticación del cuerpo de la solicitud utilizando un objeto ObjectMapper. 
         * Si hay algún error de entrada o salida (IOException), se captura y no se realiza ninguna acción adicional.
         * 3. Se crea un objeto UsernamePasswordAuthenticationToken utilizando las credenciales de autenticación obtenidas.
         * 4. Se llama al método getAuthenticationManager().authenticate() para realizar la autenticación. 
         * Este método delega la autenticación al administrador de autenticación configurado en la clase SecurityConfig.
         */
        log.info("4");
        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch(IOException e){
            
        }
        
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getUsuario(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );
        
        
        /*
         * El método getAuthenticationManager() devuelve el administrador de autenticación configurado en la clase SecurityConfig..
         * Dentro del administrador de autenticación, se llama al proveedor de autenticación correspondiente, que está configurado en SecurityConfig. 
         * Por ejemplo, si se utiliza el proveedor de autenticación basado en UserDetailsService, se llama a su método loadUserByUsername para cargar los detalles del usuario.
         */
        return getAuthenticationManager().authenticate(usernamePAT);//ACA LLAMA A UserDetailsService
    }

    
    
    /*
     * SI SE COMPRUEBA LA VALIDACION ENTONCES INGRESA ACA YA QUE TOD0 SE REALIZO
     * SATISFACTORIAMENTE
     * CREA EL TOKEN Y DEVUELVE LA RESPUESTA CON EL NUEVO ENCABEZADO
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain chain, 
            Authentication authResult) throws IOException, ServletException {
        
                log.info("9");
       UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
       String token = TokenUtils.createToken(userDetails.getNombre());
       
       response.addHeader("Authorization", "Bearer " + token);
       response.getWriter().flush();
        
        super.successfulAuthentication(request, response, chain, authResult); //TERMINA EL FLUJO ENVIANDO EL TOKEN Y QUE TODO SALIO BIEN
    }
    
    
    
}
