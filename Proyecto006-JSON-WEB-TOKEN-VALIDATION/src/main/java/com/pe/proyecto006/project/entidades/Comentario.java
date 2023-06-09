package com.pe.proyecto006.project.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comentarios")
public class Comentario {
    /*
    ACA EN COMENRTARIO NO AÑADO ESTO orphanRemoval NI CASCADE PORQUE CUANDO YO BORRÉ UN COMENTARIO 
    NO SE VA A BORRAR UNA PUBLICACION, PERO SI CUANDO BORRE UNA PUBLICACION SI SE VAN A BORRAR LOS COMENTARIOS
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String nombre;
    private String email;
    private String cuerpo;
    
    
    //MANYTOONE  MUCHOS COMENTARIOS PUEDE ESTAR SOBRE UNA PUBLICACION
    @ManyToOne(fetch = FetchType.LAZY)//CARGA PEREZOZA,SOLO CUANDO LE DECIMOS SE HARÁ LA CARGA
    @JoinColumn(name="publicacion_id", nullable=false)//PARA QUE HAGA REFERENCIA A ESTA TABLE Y ESTE CAMPO publicacion_id TAMBIEN ESTARÁ EN ESTA TABLA
    private Publicacion publicacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Comentario() {
    }

    public Comentario(long id, String nombre, String email, String cuerpo, Publicacion publicacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.cuerpo = cuerpo;
        this.publicacion = publicacion;
    }
    
    
    
}
