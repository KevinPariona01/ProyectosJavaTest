package com.pe.proyecto006.project.configuracion;

import com.pe.proyecto006.project.entidades.Usuario;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor//PARA QUE SE INICIALIZE EL MISMO CONSTRUCTOR
public class UserDetailsImpl implements UserDetails{
    
    private final Usuario usuario;

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//ESTO SIRVE SI EL USUARIO TIENE PERMISOS O ROLES
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
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
        return usuario.getUsuario();
    }
    
}
