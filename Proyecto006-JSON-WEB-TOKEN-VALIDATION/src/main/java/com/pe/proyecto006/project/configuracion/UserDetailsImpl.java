package com.pe.proyecto006.project.configuracion;

import com.pe.proyecto006.project.entidades.Usuario;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@AllArgsConstructor//PARA QUE SE INICIALIZE EL MISMO CONSTRUCTOR
public class UserDetailsImpl implements UserDetails{
    
    private final Usuario usuario;

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//ESTO SIRVE SI EL USUARIO TIENE PERMISOS O ROLES
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
    //OBTIENE LA CONTRASEÑA PARA VALIDAR
    /* 
     * LA CONTRASEÑA QUE OBTENDRA ES PARA VALIDAR LA CONTRASEÑA QUE QUEREMOS QUE VALIDE
     * CON EL DATO QUE ESTAMOS VALIDANDO
    */
    log.info("7");
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
    //OBTIENE EL USUARIO PARA VALIDAR
        log.info("8");
        return usuario.getUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getNombre(){
        log.info("10");
        return usuario.getUsuario();
    }
    
}
