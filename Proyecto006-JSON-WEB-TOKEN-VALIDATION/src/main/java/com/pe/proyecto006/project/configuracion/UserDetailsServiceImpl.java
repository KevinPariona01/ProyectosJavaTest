package com.pe.proyecto006.project.configuracion;

import com.pe.proyecto006.project.entidades.Usuario;
import com.pe.proyecto006.project.repositorio.UsuarioRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        /*
         * Cuando se llama al método loadUserByUsername, se pasa como argumento el nombre de usuario proporcionado durante la autenticación.
         * El método loadUserByUsername busca en el repositorio de usuarios (UsuarioRepositorio) un usuario que coincida con el nombre de usuario 
         * Si se encuentra un usuario, se crea un objeto UserDetailsImpl utilizando el usuario encontrado. 
         * El objeto UserDetailsImpl implementa la interfaz UserDetails y contiene los detalles del usuario necesarios para la autenticación.
         * Si no se encuentra ningún usuario con el nombre de usuario proporcionado, se lanza una excepción UsernameNotFoundException, indicando que el usuario no existe.
        */
        log.info("5 " +username);
        Usuario usuario = usuarioRepositorio.findOneByUsuario(username)    
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con nombre " + username + " No existe "));//SI NO ENCUENTRA LANZAMOS UNA EXCEPCION
        log.info("6 " +usuario);
        /*
         * se crea un objeto UserDetailsImpl utilizando ese usuario.
         * Y ESTE SE ENVIA PARA VALIDAR A UserDetailsImpl
         */
        return new UserDetailsImpl(usuario);
    }
}
