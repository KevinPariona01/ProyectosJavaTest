package com.pe.proyecto006.project.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Entity
//NOMBRE DE LA TABLA
//DECIMOS QUE TIENE UN CONSTRAINT Y QUE EL TITULO NO SE PUEDE REPETIR
@Table(name="publicaciones", uniqueConstraints={@UniqueConstraint(columnNames="titulo")})
public class Publicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="titulo", nullable=false)
    private String titulo;
    
    @Column(name="descripcion", nullable=false)
    private String descripcion;
    
    @Column(name="contenidp", nullable=false)
    private String contenido;
    
    //EN UNA PUBLICACION PUEDEN HABER MUCHOS COMENTARIOS, UNA LISTA DE COMENTARIOS
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)//CON orphanRemoval CADA VEZ QUE ELIMINEMOS UN VALOR, ESTE ELIMINARA TODOS LOS VALORES ASOCIADOS A EL, ENTONCES SI ELIMINAMOS UNA PUBLICACION SE ELIMINA ESA LISTA DE COMENTARIOS QUE ESTA CON ETE ID
    private Set<Comentario> comentarios = new HashSet<>();

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
    
    public Publicacion(){
        
    }

    public Publicacion(Long id, String titulo, String descripcion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Publicacion{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", contenido=" + contenido + '}';
    }
    
    
    
}
