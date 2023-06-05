package com.pe.proyecto006.project.repositorio;

import com.pe.proyecto006.project.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findOneByUsuario(String usuario);
    
}
