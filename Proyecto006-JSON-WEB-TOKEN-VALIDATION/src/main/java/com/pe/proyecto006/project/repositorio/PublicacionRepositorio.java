package com.pe.proyecto006.project.repositorio;

import com.pe.proyecto006.project.entidades.Publicacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kevin
 */
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
    
       Optional<Publicacion> findOneByTitulo(String titulo);
    
}
