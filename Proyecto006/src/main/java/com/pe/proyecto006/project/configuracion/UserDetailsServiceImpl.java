package com.pe.proyecto006.project.configuracion;

import com.pe.proyecto006.project.entidades.Usuario;
import com.pe.proyecto006.project.repositorio.PublicacionRepositorio;
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
        log.info("3" +username);
        Usuario usuario = usuarioRepositorio.findOneByUsuario(username)    
                .orElseThrow(()-> new UsernameNotFoundException("La publicacion con titulo " + username + " No existe "));//SI NO ENCUENTRA LANZAMOS UNA EXCEPCION
        log.info("4" +usuario);
        return new UserDetailsImpl(usuario);
    }
}
