package com.pe.proyecto006.project.repositorio;

import com.pe.proyecto006.project.entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    
       //CREO ESTE METODO EN MI MAPPER
    /*
    ACA LE DECIMOS QUE HAGA UNA BUSQUEDA POR ID PUBLICACION
    PARA REALIZAR ESO DEBEMOS HACER QUE SPRING DATA JPA INFIERA QUE QUEREMOS BUSCAR
    PARA ESO USAMOS LA NOMENCLATURA findByNombreAtributo como
    public List<Comentario> findByEmail(String email);
    PARA DECIRLE QUE BUSQUE POR EL ATRIBUTO EMAIL DEL NOMBRE DEL COMENTARIO
    AUNQUE EN ESTE CASO DE MANERA IMPLICITA NO MANEJA LA ENTIDAD COMENTARIO NO TIENE EL ID PUBLICACION
    SI NO TIENE LA PUBLICACION PERO SPRING DATA JPA INFIERA QUE LA CONEXION ENTRE COMENTARIO Y PUBLICACION 
    ES EL publicacionId
    */
       public List<Comentario> findByPublicacionId(long publicacionId);
    
}
