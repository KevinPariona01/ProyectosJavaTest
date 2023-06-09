package com.pe.proyecto006.project.dto;

import com.pe.proyecto006.project.entidades.Comentario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class PublicacionDTO {

    //VALIDACIONES A LOS CAMPOS
    //PARA ESTO SE USA LA DEPENDENCIA DE VALIDACIONES spring-boot-starter-validation
    private Long id;
    
    @NotEmpty
    @Size(min = 2, message= "El titulo debe tener al menos dos caracteres")
    private String titulo;
    @NotEmpty
    @Size(min = 10, message= "La descripcion deberia tener al menos 10 caracteres")
    private String descripcion;
    @NotEmpty
    private String contenido;
    
    private Set<Comentario> comentarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
    public PublicacionDTO(){
        
    }

    public PublicacionDTO(Long id, String titulo, String descripcion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "PublicacionDTO{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", contenido=" + contenido + '}';
    }
    
    
    
}
